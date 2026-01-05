package ec.edu.monster.controllers;

import ec.edu.monster.models.*;
import ec.edu.monster.services.FacturaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.Map;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService facturaService;

    public FacturaController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping("/crear")
    public String crear(Model model) {
        model.addAttribute("crearFacturaViewModel", new CrearFacturaViewModel());
        return "facturas/crear";
    }

    @PostMapping("/crear")
    public String crear(@Valid @ModelAttribute("crearFacturaViewModel") CrearFacturaViewModel model, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            String errors = bindingResult.getAllErrors().stream()
                .map(ObjectError::getDefaultMessage)
                .collect(Collectors.joining(", "));
            redirectAttributes.addFlashAttribute("error", "Errores de validación: " + errors);
            return "redirect:/facturas/crear";
        }

        try {
            // Validar que haya productos
            if (model.getProductosCarrito() == null || model.getProductosCarrito().isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Debe agregar al menos un producto");
                return "redirect:/facturas/crear";
            }

            System.out.println("[DEBUG] Creando factura - Cédula: " + model.getCedula());
            System.out.println("[DEBUG] Tipo de pago: " + model.getTipoPago());
            System.out.println("[DEBUG] Número de cuotas: " + model.getNumeroCuotas());
            System.out.println("[DEBUG] Productos en carrito: " + model.getProductosCarrito().size());

            // Adaptar TipoPago
            String tipoPagoSOAP = "Efectivo".equals(model.getTipoPago()) ? "E" : "C";

            // Crear solicitud
            SolicitudFactura solicitud = new SolicitudFactura();
            solicitud.setCedula(model.getCedula());
            solicitud.setTipoPago(tipoPagoSOAP);
            solicitud.setNumeroCuotas("C".equals(tipoPagoSOAP) ? model.getNumeroCuotas() : null);
            solicitud.setDetalles(model.getProductosCarrito().stream().map(p -> {
                DetalleFacturaRequest d = new DetalleFacturaRequest();
                d.setCodigo(p.getCodigo());
                d.setCantidad(p.getCantidad());
                return d;
            }).collect(Collectors.toList()));

            System.out.println("[DEBUG] Solicitud preparada - TipoPago SOAP: " + tipoPagoSOAP);
            System.out.println("[DEBUG] Detalles: " + solicitud.getDetalles().size() + " productos");

            // Llamar al servicio
            Map<String, Object> respuesta = facturaService.crearFactura(solicitud);

            System.out.println("[DEBUG] Respuesta del servicio: " + respuesta);

            if (respuesta != null && respuesta.containsKey("numFactura")) {
                redirectAttributes.addFlashAttribute("mensaje", "Factura creada exitosamente");
                String numFactura = (String) respuesta.get("numFactura");
                System.out.println("[DEBUG] Factura creada: " + numFactura);
                return "redirect:/facturas/detalle?numFactura=" + numFactura;
            } else {
                String errorMsg = "Error al crear factura - Respuesta: " + respuesta;
                System.err.println("[ERROR] " + errorMsg);
                redirectAttributes.addFlashAttribute("error", errorMsg);
                return "redirect:/facturas/crear";
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            String errorMsg = ex.getMessage();
            
            // Extraer el mensaje JSON si existe
            if (errorMsg != null && errorMsg.contains("{\"error\":")) {
                try {
                    int startIndex = errorMsg.indexOf("{");
                    int endIndex = errorMsg.lastIndexOf("}") + 1;
                    String jsonPart = errorMsg.substring(startIndex, endIndex);
                    
                    // Extraer solo el campo "message"
                    if (jsonPart.contains("\"message\":")) {
                        int msgStart = jsonPart.indexOf("\"message\":\"") + 11;
                        int msgEnd = jsonPart.indexOf("\"", msgStart);
                        errorMsg = jsonPart.substring(msgStart, msgEnd);
                    }
                } catch (Exception e) {
                    // Si falla la extracción, usar el mensaje original
                    errorMsg = ex.getMessage();
                }
            }
            
            System.err.println("[ERROR] " + errorMsg);
            redirectAttributes.addFlashAttribute("error", errorMsg);
            return "redirect:/facturas/crear";
        }
    }

    @PostMapping("/validar-cliente")
    @ResponseBody
    public Map<String, Object> validarCliente(@RequestBody String cedula) {
        try {
            if (cedula == null || cedula.length() != 10 || !cedula.matches("\\d{10}")) {
                Map<String, Object> response = new HashMap<>();
                response.put("success", false);
                response.put("message", "Cédula inválida. Debe tener 10 dígitos.");
                return response;
            }

            Map<String, Object> result = facturaService.validarCliente(cedula);
            return result;
        } catch (Exception ex) {
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "Error al validar cliente: " + ex.getMessage());
            return response;
        }
    }

    @GetMapping("/consultar")
    public String consultar(ConsultarFacturasViewModel filtros, Model model, HttpSession session) {
        String rol = (String) session.getAttribute("Rol");
        model.addAttribute("rol", rol);

        // Para clientes, filtrar por su cédula si no se especifica
        ConsultarFacturasViewModel filtrosToUse = filtros != null ? filtros : new ConsultarFacturasViewModel();
        if ("CLIENTE".equals(rol) && (filtrosToUse.getCedula() == null || filtrosToUse.getCedula().isEmpty())) {
            filtrosToUse.setCedula((String) session.getAttribute("Cedula"));
        }

        var viewModel = filtrosToUse;

        try {
            List<FacturaResumen> facturas;
            if (filtrosToUse.getNumeroFactura() != null && !filtrosToUse.getNumeroFactura().isEmpty()) {
                DetalleFacturaViewModel factura = facturaService.obtenerFacturaPorNumFactura(filtrosToUse.getNumeroFactura());
                facturas = factura != null ? List.of(convertToResumen(factura)) : List.of();
            } else if (filtrosToUse.getCedula() != null && !filtrosToUse.getCedula().isEmpty()) {
                // Filtrar por cédula desde la lista completa
                List<DetalleFacturaViewModel> fullFacturas = facturaService.listarFacturas();
                facturas = fullFacturas != null ? fullFacturas.stream()
                    .filter(f -> filtrosToUse.getCedula().equals(f.getClienteCedula()))
                    .map(this::convertToResumen)
                    .collect(Collectors.toList()) : List.of();
            } else {
                List<DetalleFacturaViewModel> fullFacturas = facturaService.listarFacturas();
                facturas = fullFacturas != null ? fullFacturas.stream().map(this::convertToResumen).collect(Collectors.toList()) : List.of();
            }

            // Apply filters
            if (filtrosToUse.getFechaInicio() != null) {
                facturas = facturas.stream().filter(f -> !f.getFecha().toLocalDate().isBefore(filtrosToUse.getFechaInicio().toLocalDate())).collect(Collectors.toList());
            }
            if (filtrosToUse.getFechaFin() != null) {
                facturas = facturas.stream().filter(f -> !f.getFecha().toLocalDate().isAfter(filtrosToUse.getFechaFin().toLocalDate())).collect(Collectors.toList());
            }
            if (filtrosToUse.getTipoPago() != null && !filtrosToUse.getTipoPago().isEmpty()) {
                String tipoPagoSOAP = "Efectivo".equals(filtrosToUse.getTipoPago()) ? "E" : "C";
                facturas = facturas.stream().filter(f -> tipoPagoSOAP.equals(f.getTipoPago())).collect(Collectors.toList());
            }

            viewModel.setFacturas(facturas);
        } catch (Exception ex) {
            model.addAttribute("error", "Error al consultar facturas: " + ex.getMessage());
        }

        model.addAttribute("consultarFacturasViewModel", viewModel);
        return "facturas/consultar";
    }

    @GetMapping("/detalle")
    public String detalle(@RequestParam String numFactura, Model model) {
        try {
            DetalleFacturaViewModel factura = facturaService.obtenerFacturaPorNumFactura(numFactura);

            if (factura == null) {
                return "redirect:/facturas/consultar";
            }

            // Convertir tipoPago
            if ("E".equals(factura.getTipoPago())) {
                factura.setTipoPago("Efectivo");
            } else if ("C".equals(factura.getTipoPago())) {
                factura.setTipoPago("Credito");
            }

            // Limpiar nombre del cliente
            if (factura.getClienteNombre() != null) {
                factura.setClienteNombre(factura.getClienteNombre()
                    .replace("Ã¡", "á")
                    .replace("Ã©", "é")
                    .replace("Ã­", "í")
                    .replace("Ã³", "ó")
                    .replace("Ãº", "ú")
                    .replace("Ã±", "ñ")
                    .replace(" null", "")
                    .trim());
            }

            if (factura.getProductos() != null) {
                BigDecimal subtotal = factura.getProductos().stream()
                        .map(DetalleProducto::getSubtotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                factura.setSubtotal(subtotal);
                factura.setIva(subtotal.multiply(BigDecimal.valueOf(0.15)));
            }

            model.addAttribute("detalleFacturaViewModel", factura);
            return "facturas/detalle";
        } catch (Exception ex) {
            model.addAttribute("error", "Error: " + ex.getMessage());
            return "redirect:/facturas/consultar";
        }
    }

    @GetMapping("/amortizacion")
    public String amortizacion(@RequestParam String numFactura, Model model) {
        try {
            List<CuotaAmortizacion> cuotas = facturaService.obtenerAmortizacion(numFactura);
            DetalleFacturaViewModel factura = facturaService.obtenerFacturaPorNumFactura(numFactura);

            if (cuotas == null || factura == null) {
                return "redirect:/facturas/consultar";
            }

            // Calcular saldo pendiente y números de cuota
            BigDecimal saldoPendiente = factura.getTotal();
            for (int i = 0; i < cuotas.size(); i++) {
                CuotaAmortizacion cuota = cuotas.get(i);
                cuota.setNumeroCuota(i + 1);
                cuota.setSaldoPendiente(saldoPendiente.subtract(cuota.getCapital()));
                saldoPendiente = cuota.getSaldoPendiente();
            }

            AmortizacionViewModel viewModel = new AmortizacionViewModel();
            viewModel.setNumFactura(numFactura);
            viewModel.setMontoTotal(factura.getTotal());
            viewModel.setPlazo(cuotas.size());
            viewModel.setTasaInteresAnual(BigDecimal.valueOf(15));
            viewModel.setCuotaFijaMensual(cuotas.isEmpty() ? BigDecimal.ZERO : cuotas.get(0).getValorCuota());
            viewModel.setCuotas(cuotas);

            model.addAttribute("amortizacionViewModel", viewModel);
            return "facturas/amortizacion";
        } catch (Exception ex) {
            model.addAttribute("error", "Error: " + ex.getMessage());
            return "redirect:/facturas/consultar";
        }
    }

    private FacturaResumen convertToResumen(DetalleFacturaViewModel factura) {
        FacturaResumen resumen = new FacturaResumen();
        resumen.setNumFactura(factura.getNumFactura());
        resumen.setFecha(factura.getFecha().atStartOfDay());
        resumen.setCedula(factura.getClienteCedula());
        resumen.setTotal(factura.getTotal());
        resumen.setTipoPago("E".equals(factura.getTipoPago()) ? "Efectivo" : "Credito");
        resumen.setCodCreditoRef(factura.getCodigoCredito());
        return resumen;
    }
}