package ec.edu.monster.controlador.facturas

import android.util.Log
import ec.edu.monster.modelo.FacturaRequest
import ec.edu.monster.modelo.FacturaResponse
import ec.edu.monster.modelo.banquito.AmortizacionDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Service de alto nivel para facturas que usan las vistas/ViewModels.
 * Delega las llamadas de red al `FacturaController` (wrapper Retrofit).
 */
class FacturaService(
    private val controller: FacturaController = FacturaController()
) {

    /**
     * Genera una nueva factura.
     * Validaciones: Si tipoPago = "C", numeroCuotas debe estar entre 3 y 24.
     */
    suspend fun generarFactura(request: FacturaRequest): FacturaResponse = withContext(Dispatchers.IO) {
        Log.d("CFACTURA", "[Service] Iniciando generarFactura")
        Log.d("CFACTURA", "[Service] Validando request...")
        
        // Validación local antes de enviar al backend
        if (request.tipoPago == "C") {
            Log.d("CFACTURA", "[Service] Tipo de pago: Crédito")
            if (request.numeroCuotas == null) {
                Log.e("CFACTURA", "[Service] ✗ Validación fallida: numeroCuotas es null")
                throw IllegalArgumentException("El número de cuotas es obligatorio para pago a crédito")
            }
            if (request.numeroCuotas < 3 || request.numeroCuotas > 24) {
                Log.e("CFACTURA", "[Service] ✗ Validación fallida: numeroCuotas=${request.numeroCuotas} (debe estar entre 3 y 24)")
                throw IllegalArgumentException("El número de cuotas debe estar entre 3 y 24")
            }
            Log.d("CFACTURA", "[Service] ✓ Validación OK: numeroCuotas=${request.numeroCuotas}")
        } else if (request.tipoPago == "E") {
            Log.d("CFACTURA", "[Service] Tipo de pago: Efectivo")
            if (request.numeroCuotas != null) {
                Log.e("CFACTURA", "[Service] ✗ Validación fallida: numeroCuotas debe ser null para efectivo")
                throw IllegalArgumentException("El número de cuotas no debe enviarse para pago en efectivo")
            }
            Log.d("CFACTURA", "[Service] ✓ Validación OK")
        }
        
        Log.d("CFACTURA", "[Service] Llamando a controller.generarFactura()...")
        try {
            val response = controller.generarFactura(request)
            Log.d("CFACTURA", "[Service] ✓ Controller retornó respuesta exitosa")
            Log.d("CFACTURA", "[Service] NumFactura: ${response.numFactura}, Total: ${response.total}")
            response
        } catch (e: Exception) {
            Log.e("CFACTURA", "[Service] ✗ Controller lanzó excepción: ${e.javaClass.simpleName}", e)
            Log.e("CFACTURA", "[Service] Mensaje: ${e.message}")
            throw e
        }
    }

    suspend fun obtenerFactura(numFactura: String): FacturaResponse = withContext(Dispatchers.IO) {
        controller.obtenerFactura(numFactura)
    }

    suspend fun obtenerTodas(): List<FacturaResponse> = withContext(Dispatchers.IO) {
        controller.obtenerTodas()
    }

    suspend fun obtenerAmortizacion(numFactura: String): List<AmortizacionDTO> = withContext(Dispatchers.IO) {
        controller.obtenerAmortizacion(numFactura)
    }
}
