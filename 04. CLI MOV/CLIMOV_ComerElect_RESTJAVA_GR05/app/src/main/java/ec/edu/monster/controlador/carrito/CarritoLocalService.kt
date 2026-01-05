package ec.edu.monster.controlador.carrito

import android.util.Log
import ec.edu.monster.modelo.ElectrodomesticoDTO
import java.math.BigDecimal

/**
 * Servicio de carrito LOCAL (no hace llamadas al servidor).
 * El carrito se guarda en memoria y se usa para crear facturas.
 */
class CarritoLocalService private constructor() {
    
    data class ItemCarritoLocal(
        val producto: ElectrodomesticoDTO,
        var cantidad: Int
    ) {
        val subtotal: BigDecimal
            get() = (producto.precio ?: BigDecimal.ZERO).multiply(BigDecimal(cantidad))
    }
    
    private val items = mutableMapOf<String, ItemCarritoLocal>() // codigo -> item
    
    companion object {
        @Volatile
        private var INSTANCE: CarritoLocalService? = null
        
        fun getInstance(): CarritoLocalService {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: CarritoLocalService().also { INSTANCE = it }
            }
        }
    }
    
    /**
     * Agrega un producto al carrito o incrementa su cantidad si ya existe
     */
    fun agregar(producto: ElectrodomesticoDTO, cantidad: Int) {
        Log.d("CARRITO_LOCAL", "Agregando: ${producto.nombre} x$cantidad")
        
        val item = items[producto.codigo]
        if (item != null) {
            // Ya existe, incrementar cantidad
            item.cantidad += cantidad
            Log.d("CARRITO_LOCAL", "Producto ya existía. Nueva cantidad: ${item.cantidad}")
        } else {
            // Nuevo producto
            items[producto.codigo] = ItemCarritoLocal(producto, cantidad)
            Log.d("CARRITO_LOCAL", "Producto agregado como nuevo")
        }
        
        Log.d("CARRITO_LOCAL", "Total items en carrito: ${items.size}")
    }
    
    /**
     * Obtiene todos los items del carrito
     */
    fun obtenerItems(): List<ItemCarritoLocal> {
        Log.d("CARRITO_LOCAL", "Obteniendo items del carrito: ${items.size} productos")
        return items.values.toList()
    }
    
    /**
     * Remueve un producto del carrito
     */
    fun remover(codigo: String) {
        Log.d("CARRITO_LOCAL", "Removiendo producto: $codigo")
        items.remove(codigo)
        Log.d("CARRITO_LOCAL", "Items restantes: ${items.size}")
    }
    
    /**
     * Actualiza la cantidad de un producto
     */
    fun actualizarCantidad(codigo: String, nuevaCantidad: Int) {
        Log.d("CARRITO_LOCAL", "Actualizando cantidad de $codigo a $nuevaCantidad")
        val item = items[codigo]
        if (item != null) {
            item.cantidad = nuevaCantidad
            Log.d("CARRITO_LOCAL", "Cantidad actualizada")
        } else {
            Log.w("CARRITO_LOCAL", "Producto no encontrado en carrito")
        }
    }
    
    /**
     * Limpia todo el carrito
     */
    fun limpiar() {
        Log.d("CARRITO_LOCAL", "Limpiando carrito completo")
        items.clear()
    }
    
    /**
     * Calcula el total del carrito
     */
    fun calcularTotal(): BigDecimal {
        return items.values.sumOf { it.subtotal }
    }
    
    /**
     * Obtiene la cantidad de items distintos en el carrito
     */
    fun cantidadItems(): Int {
        return items.size
    }
}
