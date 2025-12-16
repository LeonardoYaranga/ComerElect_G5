package ec.edu.monster.vista.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ec.edu.monster.controlador.electrodomesticos.ElectrodomesticoService
import ec.edu.monster.modelo.ElectrodomesticoDTO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File

/**
 * ViewModel sencillo que carga la lista de electrodomésticos desde `ElectrodomesticoService`.
 * Expone `items` y `isLoading` como StateFlow para que la UI los observe.
 */
class ElectrodomesticosViewModel(
    private val service: ElectrodomesticoService = ElectrodomesticoService()
) : ViewModel() {

    private val _items = MutableStateFlow<List<ElectrodomesticoDTO>>(emptyList())
    val items: StateFlow<List<ElectrodomesticoDTO>> = _items.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        load()
    }

    fun load() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val list = service.listar()
                _items.value = list
                
                // Log para debug de imágenes
                list.forEach { item ->
                    Log.d("EIMAGEN", "Producto: ${item.nombre}, ImageURL: ${item.imageUrl ?: "SIN URL"}")
                }
            } catch (t: Throwable) {
                // En una app real manejar errores (mostrar mensaje, retry, etc.)
                t.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun crear(dto: ElectrodomesticoDTO, imageFile: File? = null, onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                Log.d("ECREAR", "═══════════════════════════════════════")
                Log.d("ECREAR", "[ViewModel] Iniciando creación de electrodoméstico")
                Log.d("ECREAR", "[ViewModel] Nombre: ${dto.nombre}")
                Log.d("ECREAR", "[ViewModel] Precio: ${dto.precio}")
                Log.d("ECREAR", "[ViewModel] Descripción: ${dto.descripcion}")
                Log.d("ECREAR", "[ViewModel] Imagen: ${if (imageFile != null) "SÍ (${imageFile.name}, ${imageFile.length()} bytes)" else "NO"}")
                
                val resultado = service.crear(dto, imageFile = imageFile)
                
                Log.d("ECREAR", "[ViewModel] ✅ Creación exitosa")
                Log.d("ECREAR", "[ViewModel] Código asignado: ${resultado.codigo}")
                Log.d("ECREAR", "[ViewModel] ImageURL: ${resultado.imageUrl ?: "SIN URL"}")
                Log.d("ECREAR", "═══════════════════════════════════════")
                
                load() // Refrescar lista
                onSuccess()
            } catch (t: Throwable) {
                val errorMsg = t.message ?: "Error desconocido"
                Log.e("ECREAR", "[ViewModel] ❌ ERROR al crear")
                Log.e("ECREAR", "[ViewModel] Mensaje: $errorMsg")
                Log.e("ECREAR", "[ViewModel] Tipo: ${t.javaClass.simpleName}")
                Log.e("ECREAR", "═══════════════════════════════════════")
                t.printStackTrace()
                
                _errorMessage.value = errorMsg
                onError(errorMsg)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun actualizar(codigo: String, dto: ElectrodomesticoDTO, imageFile: File? = null, onSuccess: () -> Unit = {}, onError: (String) -> Unit = {}) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            try {
                Log.d("EACTUALIZAR", "═══════════════════════════════════════")
                Log.d("EACTUALIZAR", "[ViewModel] Iniciando actualización")
                Log.d("EACTUALIZAR", "[ViewModel] Código: $codigo")
                Log.d("EACTUALIZAR", "[ViewModel] Nombre: ${dto.nombre}")
                Log.d("EACTUALIZAR", "[ViewModel] Precio: ${dto.precio}")
                Log.d("EACTUALIZAR", "[ViewModel] Imagen: ${if (imageFile != null) "SÍ (${imageFile.name})" else "NO"}")
                
                val resultado = service.actualizar(codigo, dto, imageFile = imageFile)
                
                Log.d("EACTUALIZAR", "[ViewModel] ✅ Actualización exitosa")
                Log.d("EACTUALIZAR", "[ViewModel] ImageURL: ${resultado.imageUrl ?: "SIN URL"}")
                Log.d("EACTUALIZAR", "═══════════════════════════════════════")
                
                load() // Refrescar lista
                onSuccess()
            } catch (t: Throwable) {
                val errorMsg = t.message ?: "Error desconocido"
                Log.e("EACTUALIZAR", "[ViewModel] ❌ ERROR al actualizar")
                Log.e("EACTUALIZAR", "[ViewModel] Mensaje: $errorMsg")
                Log.e("EACTUALIZAR", "═══════════════════════════════════════")
                t.printStackTrace()
                
                _errorMessage.value = errorMsg
                onError(errorMsg)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun eliminar(codigo: String, onSuccess: () -> Unit = {}) {
        viewModelScope.launch {
            try {
                service.eliminar(codigo)
                load() // Refrescar lista
                onSuccess()
            } catch (t: Throwable) {
                t.printStackTrace()
            }
        }
    }
}
