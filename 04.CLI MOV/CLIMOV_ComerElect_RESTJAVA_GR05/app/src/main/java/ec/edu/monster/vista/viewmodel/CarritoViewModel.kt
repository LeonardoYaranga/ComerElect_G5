package ec.edu.monster.vista.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.monster.controlador.carrito.CarritoService
import ec.edu.monster.modelo.ProductoCarrito
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CarritoViewModel(
    private val service: CarritoService = CarritoService()
) : ViewModel() {

    private val _items = MutableStateFlow<List<ProductoCarrito>>(emptyList())
    val items: StateFlow<List<ProductoCarrito>> = _items.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarCarrito(cedula: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val list = service.obtener(cedula)
                _items.value = list
            } catch (t: Throwable) {
                _error.value = t.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }
}