package ec.edu.monster.modelo

import java.math.BigDecimal
import java.time.LocalDate

data class FacturaResponse(
    val numFactura: String,
    val fecha: LocalDate,
    val cedula: String,
    val total: BigDecimal,
    val tipoPago: String,
    val descuento: BigDecimal,
    val detalles: List<DetalleDTO>
) {
    data class DetalleDTO(
        val codigo: String,
        val nombre: String,
        val cantidad: Int,
        val precio: BigDecimal,
        val subtotal: BigDecimal
    )
}
