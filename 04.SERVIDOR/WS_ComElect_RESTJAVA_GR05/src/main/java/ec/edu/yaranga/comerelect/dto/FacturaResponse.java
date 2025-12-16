package ec.edu.yaranga.comerelect.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class FacturaResponse {
    private String numFactura;
    private LocalDate fecha;
    private String cedula;
    private String nombreCliente;
    private BigDecimal total;
    private String tipoPago;
    private BigDecimal descuento;
    private List<DetalleDTO> detalles;

    @Data
    public static class DetalleDTO {
        private String codigo;
        private String nombre;
        private Integer cantidad;
        private BigDecimal precio;
        private BigDecimal subtotal;
    }
}
