package ec.edu.monster.controlador.carrito

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
        api.agregar(item)
    }

    suspend fun obtener(cedula: String): List<ProductoCarrito> = withContext(Dispatchers.IO) {
        api.obtener(cedula)
    }

    suspend fun remover(codigo: String, cedula: String): String = withContext(Dispatchers.IO) {
        api.remover(codigo, cedula)
    }

    suspend fun confirmar(confirmacion: ConfirmacionCarrito): FacturaResponse = withContext(Dispatchers.IO) {
        api.confirmar(confirmacion)
    }
}