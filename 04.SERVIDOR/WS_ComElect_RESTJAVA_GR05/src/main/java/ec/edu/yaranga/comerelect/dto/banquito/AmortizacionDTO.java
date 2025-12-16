package ec.edu.yaranga.comerelect.dto.banquito;

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