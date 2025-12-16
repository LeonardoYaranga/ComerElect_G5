package ec.edu.yaranga.comerelect.service;

import ec.edu.yaranga.comerelect.dto.FacturaRequest;
import ec.edu.yaranga.comerelect.dto.FacturaResponse;
import ec.edu.yaranga.comerelect.dto.banquito.AmortizacionDTO;
import ec.edu.yaranga.comerelect.dto.banquito.CreditoDTO;
import ec.edu.yaranga.comerelect.dto.banquito.CreditoRequest;
import ec.edu.yaranga.comerelect.exception.ResourceNotFoundException;
import ec.edu.yaranga.comerelect.model.DetalleFactura;
import ec.edu.yaranga.comerelect.model.DetalleFacturaId;
import ec.edu.yaranga.comerelect.model.Electrodomestico;
import ec.edu.yaranga.comerelect.model.Factura;
import ec.edu.yaranga.comerelect.repository.DetalleFacturaRepository;
import ec.edu.yaranga.comerelect.repository.ElectrodomesticoRepository;
import ec.edu.yaranga.comerelect.repository.FacturaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FacturaService {

    private final FacturaRepository facturaRepo;
    private final DetalleFacturaRepository detalleRepo;
    private final ElectrodomesticoRepository electroRepo;
    private final BanQuitoClient banQuitoClient;

    @Transactional
    public FacturaResponse generarFactura(FacturaRequest request) {
        if (request.getDetalles() == null || request.getDetalles().isEmpty()) {
            throw new IllegalArgumentException("La factura debe tener al menos un detalle");
        }

        BigDecimal totalBruto = BigDecimal.ZERO;
        List<DetalleFactura> detalles = new ArrayList<>();

        for (FacturaRequest.DetalleRequest d : request.getDetalles()) {
            Electrodomestico e = electroRepo.findById(d.getCodigo())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado: " + d.getCodigo()));

            BigDecimal subtotal = e.getPrecio().multiply(BigDecimal.valueOf(d.getCantidad()));
            totalBruto = totalBruto.add(subtotal);

            DetalleFactura det = new DetalleFactura();
            DetalleFacturaId id = new DetalleFacturaId();
            id.setCodigo(d.getCodigo());
            // numFactura se asigna después de crear la factura
            det.setId(id);
            det.setCantidad(d.getCantidad());
            det.setSubtotal(subtotal);
            det.setElectrodomestico(e);
            detalles.add(det);
        }

        Factura factura = new Factura();
        factura.setNumFactura(generarNumeroFactura());
        factura.setCedula(request.getCedula());
        factura.setTipoPago(request.getTipoPago());
        factura.setDetalles(detalles);

        // APLICAR DESCUENTO O CRÉDITO
        if ("E".equals(request.getTipoPago())) {
            BigDecimal descuento = totalBruto.multiply(BigDecimal.valueOf(0.33)).setScale(2, RoundingMode.HALF_UP);
            factura.setDescuento(descuento);
            factura.setTotal(totalBruto.subtract(descuento));
        } else if ("C".equals(request.getTipoPago())) {
            boolean esSujeto = banQuitoClient.esSujetoDeCredito(request.getCedula());
            if (!esSujeto) throw new RuntimeException("Cliente no es sujeto de crédito");
            BigDecimal montoMax = banQuitoClient.obtenerMontoMaximo(request.getCedula());
            if (totalBruto.compareTo(montoMax) > 0) throw new RuntimeException("Monto excede límite: " + montoMax);

            CreditoRequest creditoReq = new CreditoRequest(request.getCedula(), totalBruto, request.getNumeroCuotas());
            CreditoDTO credito = banQuitoClient.otorgarCredito(creditoReq);
            factura.setCodCreditoRef(String.valueOf(credito.getIdCredito()));
            factura.setTotal(totalBruto);
            factura.setDescuento(BigDecimal.ZERO);
        } else {
            throw new IllegalArgumentException("Tipo de pago inválido");
        }

        // ASIGNAR FACTURA Y COMPLETAR ID
        for (DetalleFactura det : detalles) {
            det.setFactura(factura);
            det.getId().setNumFactura(factura.getNumFactura());
        }

        factura = facturaRepo.save(factura);
        return toResponse(factura);
    }

    private String generarNumeroFactura() {
        int anioActual = LocalDate.now().getYear();
        String patron = "FAC-" + anioActual + "-%";
        
        Optional<Factura> ultimaFactura = facturaRepo.findUltimaFacturaDelAnio(patron);
        
        int siguienteNumero = 1;
        if (ultimaFactura.isPresent()) {
            String numeroAnterior = ultimaFactura.get().getNumFactura();
            // Extraer el número al final: FAC-2025-0006 -> 0006 -> 6
            String[] partes = numeroAnterior.split("-");
            int ultimoNumero = Integer.parseInt(partes[2]);
            siguienteNumero = ultimoNumero + 1;
        }
        
        return "FAC-" + anioActual + "-" + String.format("%04d", siguienteNumero);
    }

    private FacturaResponse toResponse(Factura f) {
        FacturaResponse resp = new FacturaResponse();
        resp.setNumFactura(f.getNumFactura());
        resp.setCedula(f.getCedula());
        resp.setTotal(f.getTotal());
        resp.setDescuento(f.getDescuento());
        resp.setTipoPago(f.getTipoPago());
        resp.setFecha(f.getFecha());

        // Obtener nombre del cliente desde BanQuito
        try {
            var cliente = banQuitoClient.obtenerCliente(f.getCedula());
            String apellido = cliente.getApellido() != null ? " " + cliente.getApellido() : "";
            resp.setNombreCliente(cliente.getNombre() + apellido);
        } catch (Exception e) {
            resp.setNombreCliente("Cliente no encontrado");
        }

        List<FacturaResponse.DetalleDTO> detalles = f.getDetalles().stream()
                .map(d -> {
                    FacturaResponse.DetalleDTO det = new FacturaResponse.DetalleDTO();
                    det.setCodigo(d.getElectrodomestico().getCodigo());
                    det.setNombre(d.getElectrodomestico().getNombre());
                    det.setPrecio(d.getElectrodomestico().getPrecio());
                    det.setCantidad(d.getCantidad());
                    det.setSubtotal(d.getSubtotal());
                    return det;
                })
                .collect(Collectors.toList());

        resp.setDetalles(detalles);
        return resp;
    }


    // === NUEVO: Obtener una factura por número ===
    public FacturaResponse obtenerFactura(String numFactura) {
        Factura factura = facturaRepo.findById(numFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada: " + numFactura));
        return toResponse(factura);
    }

    // === NUEVO: Listar todas las facturas ===
    public List<FacturaResponse> obtenerTodas() {
        return facturaRepo.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    // ÍTEM 11: Consultar amortización desde factura
    public List<AmortizacionDTO> obtenerAmortizacion(String numFactura) {
        Factura factura = facturaRepo.findById(numFactura)
                .orElseThrow(() -> new ResourceNotFoundException("Factura no encontrada"));

        if (!"C".equals(factura.getTipoPago())) {
            throw new IllegalStateException("Solo facturas de crédito tienen amortización");
        }

        String codCreditoRef = factura.getCodCreditoRef();
        if (codCreditoRef == null) {
            throw new RuntimeException("No hay crédito asociado");
        }

        Integer idCredito = Integer.valueOf(codCreditoRef);
        return banQuitoClient.obtenerTablaAmortizacion(idCredito);
    }
}