package ec.edu.monster.modelo.banquito

import java.math.BigDecimal
import java.time.LocalDate

data class CreditoDTO(
    val idCredito: Int,
    val cedula: String,
    val nombreCliente: String,
    val monto: BigDecimal,
    val plazoMeses: Int,
    val valorCuotaFija: BigDecimal,
    val tasaAnual: BigDecimal,
    val fechaOtorgamiento: LocalDate,
    val estado: String,
    val amortizaciones: List<AmortizacionDTO>
)
