package ec.edu.monster.controlador.carrito

import android.util.Log
import ec.edu.monster.controlador.ApiClient
import ec.edu.monster.modelo.ConfirmacionCarrito
import ec.edu.monster.modelo.FacturaResponse
import ec.edu.monster.modelo.ItemCarrito
import ec.edu.monster.modelo.ProductoCarrito
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CarritoService(
    private val api: CarritoApi = ApiClient.create(CarritoApi::class.java)
) {

    suspend fun agregar(item: ItemCarrito): String = withContext(Dispatchers.IO) {
        Log.d("CARRITO", "[Service] Agregando item al carrito")
        Log.d("CARRITO", "[Service] Cedula: ${item.cedula}")
        Log.d("CARRITO", "[Service] Codigo: ${item.codigo}")
        Log.d("CARRITO", "[Service] Cantidad: ${item.cantidad}")
        try {
            val response = api.agregar(item)
            Log.d("CARRITO", "[Service] ✓ Item agregado exitosamente: $response")
            response
        } catch (e: retrofit2.HttpException) {
            Log.e("CARRITO", "[Service] ✗ HTTP ${e.code()}: ${e.message()}")
            Log.e("CARRITO", "[Service] Error body: ${e.response()?.errorBody()?.string()}")
            throw e
        } catch (e: Exception) {
            Log.e("CARRITO", "[Service] ✗ Error: ${e.javaClass.simpleName} - ${e.message}", e)
            throw e
        }
    }

    suspend fun obtener(cedula: String): List<ProductoCarrito> = withContext(Dispatchers.IO) {
        Log.d("CARRITO", "[Service] Obteniendo carrito para cedula: $cedula")
        try {
            val items = api.obtener(cedula)
            Log.d("CARRITO", "[Service] ✓ Carrito obtenido: ${items.size} items")
            items.forEach { item ->
                Log.d("CARRITO", "[Service]   - ${item.nombre}: ${item.cantidad} x ${item.subtotal}")
            }
            items
        } catch (e: retrofit2.HttpException) {
            Log.e("CARRITO", "[Service] ✗ HTTP ${e.code()}: ${e.message()}")
            Log.e("CARRITO", "[Service] Error body: ${e.response()?.errorBody()?.string()}")
            throw e
        } catch (e: Exception) {
            Log.e("CARRITO", "[Service] ✗ Error: ${e.javaClass.simpleName} - ${e.message}", e)
            throw e
        }
    }

    suspend fun remover(codigo: String, cedula: String): String = withContext(Dispatchers.IO) {
        Log.d("CARRITO", "[Service] Removiendo item del carrito")
        Log.d("CARRITO", "[Service] Codigo: $codigo, Cedula: $cedula")
        try {
            val response = api.remover(codigo, cedula)
            Log.d("CARRITO", "[Service] ✓ Item removido: $response")
            response
        } catch (e: retrofit2.HttpException) {
            Log.e("CARRITO", "[Service] ✗ HTTP ${e.code()}: ${e.message()}")
            Log.e("CARRITO", "[Service] Error body: ${e.response()?.errorBody()?.string()}")
            throw e
        } catch (e: Exception) {
            Log.e("CARRITO", "[Service] ✗ Error: ${e.javaClass.simpleName} - ${e.message}", e)
            throw e
        }
    }

    suspend fun confirmar(confirmacion: ConfirmacionCarrito): FacturaResponse = withContext(Dispatchers.IO) {
        Log.d("CARRITO", "[Service] Confirmando carrito")
        Log.d("CARRITO", "[Service] Cedula: ${confirmacion.cedula}")
        Log.d("CARRITO", "[Service] Cuotas: ${confirmacion.numeroCuotas}")
        try {
            val factura = api.confirmar(confirmacion)
            Log.d("CARRITO", "[Service] ✓ Carrito confirmado. Factura: ${factura.numFactura}")
            factura
        } catch (e: retrofit2.HttpException) {
            Log.e("CARRITO", "[Service] ✗ HTTP ${e.code()}: ${e.message()}")
            Log.e("CARRITO", "[Service] Error body: ${e.response()?.errorBody()?.string()}")
            throw e
        } catch (e: Exception) {
            Log.e("CARRITO", "[Service] ✗ Error: ${e.javaClass.simpleName} - ${e.message}", e)
            throw e
        }
    }
}