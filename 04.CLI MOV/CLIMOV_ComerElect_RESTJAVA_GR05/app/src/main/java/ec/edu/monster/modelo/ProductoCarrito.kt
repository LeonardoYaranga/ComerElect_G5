package ec.edu.monster.modelo

import java.math.BigDecimal

data class ProductoCarrito(
    val codigo: String,
    val nombre: String,
    val cantidad: Int,
    val precioUnitario: BigDecimal,
    val subtotal: BigDecimal
)