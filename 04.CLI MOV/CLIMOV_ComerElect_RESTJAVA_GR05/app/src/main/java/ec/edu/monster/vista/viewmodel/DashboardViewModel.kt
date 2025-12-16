package ec.edu.monster.vista.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.monster.controlador.electrodomesticos.ElectrodomesticoService
import ec.edu.monster.controlador.facturas.FacturaService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.math.BigDecimal
import java.time.LocalDate

data class DashboardStats(
    val totalFacturas: Int = 0,
    val totalProductos: Int = 0,
    val totalEfectivo: BigDecimal = BigDecimal.ZERO,
    val totalCredito: BigDecimal = BigDecimal.ZERO,
    val totalGeneral: BigDecimal = BigDecimal.ZERO
)

class DashboardViewModel(
    private val facturaService: FacturaService = FacturaService(),
    private val electrodomesticoService: ElectrodomesticoService = ElectrodomesticoService()
) : ViewModel() {

    private val _stats = MutableStateFlow(DashboardStats())
    val stats: StateFlow<DashboardStats> = _stats.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * Carga todas las estadísticas del dashboard
     */
    fun cargarEstadisticas() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                // Cargar facturas y productos en paralelo
                val facturas = facturaService.obtenerTodas()
                val productos = electrodomesticoService.listar()
                
                // Filtrar facturas de hoy
                val hoy = LocalDate.now()
                val facturasHoy = facturas.filter { it.fecha == hoy }
                
                // Calcular totales
                val totalEfectivo = facturasHoy
                    .filter { it.tipoPago == "E" }
                    .sumOf { it.total }
                
                val totalCredito = facturasHoy
                    .filter { it.tipoPago == "C" }
                    .sumOf { it.total }
                
                _stats.value = DashboardStats(
                    totalFacturas = facturasHoy.size,
                    totalProductos = productos.size,
                    totalEfectivo = totalEfectivo,
                    totalCredito = totalCredito,
                    totalGeneral = totalEfectivo + totalCredito
                )
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Error al cargar estadísticas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
