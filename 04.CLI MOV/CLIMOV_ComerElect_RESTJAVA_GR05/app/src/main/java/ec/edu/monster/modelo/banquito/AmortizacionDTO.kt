package ec.edu.monster.modelo.banquito

import java.math.BigDecimal

data class AmortizacionDTO(
    val numCuota: Int,
    val valorCuota: BigDecimal,
    val interes: BigDecimal,
    val capital: BigDecimal,
    val saldo: BigDecimal
)
