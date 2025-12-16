package ec.edu.monster.vista.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.monster.controlador.electrodomesticos.ElectrodomesticoService
import ec.edu.monster.modelo.ElectrodomesticoDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CatalogoViewModel(
    private val service: ElectrodomesticoService = ElectrodomesticoService()
) : ViewModel() {

    private val _productos = MutableStateFlow<List<ElectrodomesticoDTO>>(emptyList())
    val productos: StateFlow<List<ElectrodomesticoDTO>> = _productos.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun cargarProductos() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val list = service.listar()
                _productos.value = list
            } catch (t: Throwable) {
                _error.value = t.message ?: "Error desconocido"
            } finally {
                _loading.value = false
            }
        }
    }
}