package ec.edu.monster.vista.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.monster.controlador.facturas.FacturaService
import ec.edu.monster.modelo.ApiException
import ec.edu.monster.modelo.FacturaRequest
import ec.edu.monster.modelo.FacturaResponse
import ec.edu.monster.modelo.banquito.AmortizacionDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class FacturasViewModel(
    private val service: FacturaService = FacturaService()
) : ViewModel() {

    private val _facturas = MutableStateFlow<List<FacturaResponse>>(emptyList())
    val facturas: StateFlow<List<FacturaResponse>> = _facturas.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    /**
     * Carga todas las facturas desde el backend
     */
    fun cargarFacturas() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val result = service.obtenerTodas()
                _facturas.value = result
            } catch (e: Exception) {
                e.printStackTrace()
                _error.value = "Error al cargar facturas: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Genera una nueva factura
     */
    fun generarFactura(request: FacturaRequest, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            Log.d("CFACTURA", "========== INICIO GENERAR FACTURA ==========")
            Log.d("CFACTURA", "Request - Cedula: ${request.cedula}")
            Log.d("CFACTURA", "Request - TipoPago: ${request.tipoPago}")
            Log.d("CFACTURA", "Request - NumeroCuotas: ${request.numeroCuotas}")
            Log.d("CFACTURA", "Request - Detalles: ${request.detalles.size} productos")
            request.detalles.forEachIndexed { index, detalle ->
                Log.d("CFACTURA", "  Detalle[$index] - Codigo: ${detalle.codigo}, Cantidad: ${detalle.cantidad}")
            }
            
            _isLoading.value = true
            _error.value = null
            try {
                Log.d("CFACTURA", "Llamando a service.generarFactura()...")
                val response = service.generarFactura(request)
                Log.d("CFACTURA", "✓ Factura creada exitosamente!")
                Log.d("CFACTURA", "Response - NumFactura: ${response.numFactura}")
                Log.d("CFACTURA", "Response - Total: ${response.total}")
                
                cargarFacturas() // Recargar la lista
                onSuccess()
                Log.d("CFACTURA", "========== FIN GENERAR FACTURA (EXITO) ==========")
            } catch (e: ApiException) {
                Log.e("CFACTURA", "✗ ApiException al generar factura", e)
                Log.e("CFACTURA", "Código HTTP: ${e.httpCode}")
                Log.e("CFACTURA", "Tipo de error: ${e.errorType}")
                Log.e("CFACTURA", "Mensaje: ${e.message}")
                
                val errorMsg = e.message ?: "Error desconocido"
                e.printStackTrace()
                _error.value = errorMsg
                onError(errorMsg)
                Log.d("CFACTURA", "========== FIN GENERAR FACTURA (ERROR API) ==========")
            } catch (e: Exception) {
                Log.e("CFACTURA", "✗ ERROR al generar factura", e)
                Log.e("CFACTURA", "Tipo de excepción: ${e.javaClass.simpleName}")
                Log.e("CFACTURA", "Mensaje: ${e.message}")
                Log.e("CFACTURA", "Causa: ${e.cause?.message}")
                e.printStackTrace()
                val errorMsg = e.message ?: "Error desconocido"
                _error.value = errorMsg
                onError(errorMsg)
                Log.d("CFACTURA", "========== FIN GENERAR FACTURA (ERROR) ==========")
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Obtiene los detalles de una factura específica
     */
    suspend fun obtenerFactura(numFactura: String): FacturaResponse? {
        return try {
            service.obtenerFactura(numFactura)
        } catch (e: Exception) {
            e.printStackTrace()
            _error.value = "Error al obtener factura: ${e.message}"
            null
        }
    }

    /**
     * Obtiene la tabla de amortización de una factura a crédito
     */
    suspend fun obtenerAmortizacion(numFactura: String): List<AmortizacionDTO> {
        return try {
            service.obtenerAmortizacion(numFactura)
        } catch (e: Exception) {
            e.printStackTrace()
            _error.value = "Error al obtener amortización: ${e.message}"
            emptyList()
        }
    }

    fun clearError() {
        _error.value = null
    }
}
