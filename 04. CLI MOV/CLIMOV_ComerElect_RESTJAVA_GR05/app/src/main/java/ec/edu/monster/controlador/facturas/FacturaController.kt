package ec.edu.monster.controlador.facturas

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonParser
import ec.edu.monster.modelo.ApiException
import ec.edu.monster.modelo.FacturaRequest
import ec.edu.monster.modelo.FacturaResponse
import ec.edu.monster.modelo.banquito.AmortizacionDTO
import ec.edu.monster.controlador.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Controller que hace las peticiones HTTP al backend de facturas.
 * Wrapper de la API de Retrofit con funciones suspend.
 */
class FacturaController(
    private val api: FacturaApi = ApiClient.create(FacturaApi::class.java)
) {
    private val gson = Gson()

    suspend fun generarFactura(request: FacturaRequest): FacturaResponse = withContext(Dispatchers.IO) {
        Log.d("CFACTURA", "[Controller] Iniciando petición POST a /facturas")
        Log.d("CFACTURA", "[Controller] Request JSON: ${gson.toJson(request)}")
        
        try {
            Log.d("CFACTURA", "[Controller] Enviando petición HTTP...")
            val response = api.generarFactura(request)
            Log.d("CFACTURA", "[Controller] ✓ Respuesta recibida exitosamente")
            Log.d("CFACTURA", "[Controller] Response JSON: ${gson.toJson(response)}")
            response
        } catch (e: retrofit2.HttpException) {
            Log.e("CFACTURA", "[Controller] ✗ HttpException - Código: ${e.code()}")
            
            // Intentar extraer el mensaje de error del backend
            var errorMessage = "Error del servidor (código ${e.code()})"
            var errorType: String? = null
            
            try {
                val errorBody = e.response()?.errorBody()?.string()
                Log.e("CFACTURA", "[Controller] Error Body: $errorBody")
                
                if (!errorBody.isNullOrEmpty()) {
                    // Parsear el JSON de error
                    val jsonError = JsonParser.parseString(errorBody).asJsonObject
                    val message = jsonError.get("message")?.asString
                    val error = jsonError.get("error")?.asString
                    
                    errorMessage = message ?: error ?: errorBody
                    errorType = error
                    
                    Log.e("CFACTURA", "[Controller] Mensaje extraído: $errorMessage")
                    Log.e("CFACTURA", "[Controller] Tipo de error: $errorType")
                }
            } catch (ex: Exception) {
                Log.e("CFACTURA", "[Controller] No se pudo parsear error body", ex)
            }
            
            // Lanzar nuestra excepción personalizada con el mensaje parseado
            throw ApiException(errorMessage, errorType, e.code())
        } catch (e: Exception) {
            Log.e("CFACTURA", "[Controller] ✗ Excepción: ${e.javaClass.simpleName}", e)
            Log.e("CFACTURA", "[Controller] Mensaje: ${e.message}")
            throw e
        }
    }

    suspend fun obtenerFactura(numFactura: String): FacturaResponse = withContext(Dispatchers.IO) {
        api.obtenerFactura(numFactura)
    }

    suspend fun obtenerTodas(): List<FacturaResponse> = withContext(Dispatchers.IO) {
        api.obtenerTodas()
    }

    suspend fun obtenerAmortizacion(numFactura: String): List<AmortizacionDTO> = withContext(Dispatchers.IO) {
        api.obtenerAmortizacion(numFactura)
    }
}
