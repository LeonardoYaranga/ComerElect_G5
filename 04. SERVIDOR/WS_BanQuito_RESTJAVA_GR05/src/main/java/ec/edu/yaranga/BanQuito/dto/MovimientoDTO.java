package ec.edu.yaranga.BanQuito.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class MovimientoDTO {
    private Integer codMovimiento;
    private String numCuenta;
    private String tipo; // DEP, RET
    private BigDecimal valor;
    private LocalDate fecha;
}
