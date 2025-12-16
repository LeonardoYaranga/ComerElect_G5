package ec.edu.yaranga.BanQuito.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class CuentaDTO {
    private String numCuenta;
    private String cedula;
    private BigDecimal saldo;
    // No incluimos movimientos ni cliente para evitar recursividad
}
