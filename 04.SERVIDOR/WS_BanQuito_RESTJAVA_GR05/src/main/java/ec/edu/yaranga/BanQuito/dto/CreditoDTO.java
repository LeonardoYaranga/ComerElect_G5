package ec.edu.yaranga.BanQuito.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Data
public class CreditoDTO {
    private Integer idCredito;
    private String cedula;
    private String nombreCliente;
    private BigDecimal monto;
    private Integer plazoMeses;
    private BigDecimal valorCuotaFija;
    private BigDecimal tasaAnual;
    private LocalDate fechaOtorgamiento;
    private String estado;
    private List<AmortizacionDTO> amortizaciones;
}