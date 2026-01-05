package ec.edu.yaranga.BanQuito.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class AmortizacionDTO {
    private Integer numCuota;
    private BigDecimal valorCuota;
    private BigDecimal interes;
    private BigDecimal capital;
    private BigDecimal saldo;
}