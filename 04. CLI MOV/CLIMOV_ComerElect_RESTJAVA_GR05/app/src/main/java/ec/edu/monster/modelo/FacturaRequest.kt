package ec.edu.monster.modelo

data class FacturaRequest(
    val cedula: String,
    val tipoPago: String, // "E" (Efectivo) o "C" (Crédito)
    val numeroCuotas: Int? = null, // Solo obligatorio si tipoPago = "C"
    val detalles: List<DetalleRequest>
) {
    data class DetalleRequest(
        val codigo: String,
        val cantidad: Int
    )
}
