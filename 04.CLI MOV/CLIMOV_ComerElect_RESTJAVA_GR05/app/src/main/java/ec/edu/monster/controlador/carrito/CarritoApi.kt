package ec.edu.monster.controlador.carrito

import ec.edu.monster.modelo.ConfirmacionCarrito
import ec.edu.monster.modelo.FacturaResponse
import ec.edu.monster.modelo.ItemCarrito
import ec.edu.monster.modelo.ProductoCarrito
import retrofit2.http.*

interface CarritoApi {

    @POST("/api/carrito/agregar")
    suspend fun agregar(@Body item: ItemCarrito): String // Asumir que devuelve mensaje o algo

    @GET("/api/carrito")
    suspend fun obtener(@Query("cedula") cedula: String): List<ProductoCarrito>

    @DELETE("/api/carrito/remover/{codigo}")
    suspend fun remover(@Path("codigo") codigo: String, @Query("cedula") cedula: String): String

    @POST("/api/carrito/confirmar")
    suspend fun confirmar(@Body confirmacion: ConfirmacionCarrito): FacturaResponse
}