package ec.edu.monster.controllers;

import ec.edu.monster.models.DashboardViewModel;
import ec.edu.monster.models.DetalleFacturaViewModel;
import ec.edu.monster.models.ProductoMasVendido;
import ec.edu.monster.services.FacturaService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private final FacturaService facturaService;

    public DashboardController(FacturaService facturaService) {
        this.facturaService = facturaService;
    }

    @GetMapping
    public String index(HttpSession session, Model model) {
        String usuario = (String) session.getAttribute("Usuario");
        String rol = (String) session.getAttribute("Rol");
        String cedula = (String) session.getAttribute("Cedula");
        
        // Guardar la cédula en el modelo para usarla en el frontend
        model.addAttribute("cedula", cedula);
        model.addAttribute("rol", rol);

        var viewModel = new DashboardViewModel();
        viewModel.setNombreUsuario(usuario != null ? usuario : "Usuario");

        // Solo mostrar estadísticas para ADMIN
        if ("ADMIN".equals(rol)) {
            try {
                List<DetalleFacturaViewModel> facturas = facturaService.listarFacturas();

            if (facturas != null) {
                LocalDate hoy = LocalDate.now();
                List<DetalleFacturaViewModel> facturasHoy = facturas.stream()
                        .filter(f -> f.getFecha().equals(hoy))
                        .collect(Collectors.toList());

                viewModel.setTotalFacturasHoy(facturasHoy.size());
                viewModel.setTotalVendidoEfectivo(facturasHoy.stream()
                        .filter(f -> "E".equals(f.getTipoPago()))
                        .map(DetalleFacturaViewModel::getTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));
                viewModel.setTotalVendidoCredito(facturasHoy.stream()
                        .filter(f -> "C".equals(f.getTipoPago()))
                        .map(DetalleFacturaViewModel::getTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add));

                // Productos más vendidos
                Map<String, Integer> productosVendidosMap = facturas.stream()
                        .flatMap(f -> f.getProductos().stream())
                        .collect(Collectors.groupingBy(
                                p -> p.getNombre(),
                                Collectors.summingInt(p -> p.getCantidad())
                        ));

                List<ProductoMasVendido> productosMasVendidos = productosVendidosMap.entrySet().stream()
                        .map(e -> {
                            ProductoMasVendido p = new ProductoMasVendido();
                            p.setNombre(e.getKey());
                            p.setCantidad(e.getValue());
                            return p;
                        })
                        .sorted((a, b) -> Integer.compare(b.getCantidad(), a.getCantidad()))
                        .limit(5)
                        .collect(Collectors.toList());

                viewModel.setProductosMasVendidos(productosMasVendidos);
                }
            } catch (Exception ex) {
                model.addAttribute("error", "Error al cargar datos: " + ex.getMessage());
            }
        }

        model.addAttribute("dashboardViewModel", viewModel);
        return "dashboard/index";
    }
}