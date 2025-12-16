package ec.edu.monster.modelo

import java.math.BigDecimal

data class ElectrodomesticoDTO(
    val codigo: String,
    val nombre: String,
    val precio: BigDecimal,
    val descripcion: String,
    val imageUrl: String? = null
)
