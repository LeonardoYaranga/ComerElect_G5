package ec.edu.monster.modelo.banquito

import androidx.annotation.IntRange
import java.math.BigDecimal

data class CreditoRequest(
    val cedula: String,
    val precioCredito: BigDecimal,
    @IntRange(from = 3, to = 24)
    val numeroCuotas: Int
)
