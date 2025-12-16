package ec.edu.monster.controlador.electrodomesticos

import android.net.Uri
import android.util.Log
import ec.edu.monster.modelo.ElectrodomesticoDTO
import ec.edu.monster.modelo.ResponseDto
import ec.edu.monster.controlador.ApiClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

/**
 * Service de más alto nivel que usan las vistas/ViewModels.
 * Delegará las llamadas de red al `ElectrodomesticoController` (wrapper Retrofit).
 */
class ElectrodomesticoService(
    private val controller: ElectrodomesticoController = ApiClient.create(ElectrodomesticoController::class.java)
) {

    suspend fun listar(): List<ElectrodomesticoDTO> = withContext(Dispatchers.IO) {
        controller.listar()
    }

    suspend fun obtener(codigo: String): ElectrodomesticoDTO = withContext(Dispatchers.IO) {
        controller.obtener(codigo)
    }

    suspend fun crear(dto: ElectrodomesticoDTO, imageUri: Uri? = null, imageFile: File? = null): ElectrodomesticoDTO = withContext(Dispatchers.IO) {
        try {
            Log.d("ECREAR", "[Service] Preparando datos multipart")
            
            val nombreBody = dto.nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val precioBody = dto.precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val descripcionBody = dto.descripcion.takeIf { it.isNotBlank() }?.toRequestBody("text/plain".toMediaTypeOrNull())
            
            Log.d("ECREAR", "[Service] - nombre: ${dto.nombre}")
            Log.d("ECREAR", "[Service] - precio: ${dto.precio}")
            Log.d("ECREAR", "[Service] - descripcion: ${dto.descripcion.takeIf { it.isNotBlank() } ?: "vacía"}")
            
            val imagePart = imageFile?.let { file ->
                Log.d("ECREAR", "[Service] - imagen: ${file.name} (${file.length()} bytes)")
                Log.d("ECREAR", "[Service] - imagen existe: ${file.exists()}")
                Log.d("ECREAR", "[Service] - imagen legible: ${file.canRead()}")
                
                // Detectar tipo MIME específico basado en la extensión
                val mimeType = when (file.extension.lowercase()) {
                    "jpg", "jpeg" -> "image/jpeg"
                    "png" -> "image/png"
                    "gif" -> "image/gif"
                    "webp" -> "image/webp"
                    else -> "image/jpeg" // Default
                }
                Log.d("ECREAR", "[Service] - tipo MIME: $mimeType")
                
                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                MultipartBody.Part.createFormData("imagen", file.name, requestFile)
            }
            
            if (imagePart == null) {
                Log.d("ECREAR", "[Service] - imagen: NO SE ENVIARÁ")
            }
            
            Log.d("ECREAR", "[Service] Llamando al controller (Retrofit)...")
            val resultado = controller.crear(nombreBody, precioBody, descripcionBody, imagePart)
            
            Log.d("ECREAR", "[Service] ✅ Respuesta recibida del backend")
            Log.d("ECREAR", "[Service] - codigo: ${resultado.codigo}")
            Log.d("ECREAR", "[Service] - imageUrl: ${resultado.imageUrl ?: "null"}")
            
            resultado
        } catch (e: retrofit2.HttpException) {
            Log.e("ECREAR", "[Service] ❌ HttpException en Service")
            Log.e("ECREAR", "[Service] Código HTTP: ${e.code()}")
            Log.e("ECREAR", "[Service] Mensaje: ${e.message()}")
            
            // Intentar leer el cuerpo del error
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                Log.e("ECREAR", "[Service] Error Body: $errorBody")
            }
            
            throw Exception("HTTP ${e.code()}: ${errorBody ?: e.message()}")
        } catch (e: Exception) {
            Log.e("ECREAR", "[Service] ❌ Excepción en Service")
            Log.e("ECREAR", "[Service] Tipo: ${e.javaClass.simpleName}")
            Log.e("ECREAR", "[Service] Mensaje: ${e.message}")
            throw e
        }
    }

    suspend fun actualizar(codigo: String, dto: ElectrodomesticoDTO, imageUri: Uri? = null, imageFile: File? = null): ElectrodomesticoDTO = withContext(Dispatchers.IO) {
        try {
            Log.d("EACTUALIZAR", "[Service] Preparando datos multipart")
            Log.d("EACTUALIZAR", "[Service] - codigo: $codigo")
            
            val nombreBody = dto.nombre.toRequestBody("text/plain".toMediaTypeOrNull())
            val precioBody = dto.precio.toString().toRequestBody("text/plain".toMediaTypeOrNull())
            val descripcionBody = dto.descripcion.takeIf { it.isNotBlank() }?.toRequestBody("text/plain".toMediaTypeOrNull())
            
            val imagePart = imageFile?.let { file ->
                Log.d("EACTUALIZAR", "[Service] - imagen nueva: ${file.name}")
                
                val mimeType = when (file.extension.lowercase()) {
                    "jpg", "jpeg" -> "image/jpeg"
                    "png" -> "image/png"
                    "gif" -> "image/gif"
                    "webp" -> "image/webp"
                    else -> "image/jpeg"
                }
                Log.d("EACTUALIZAR", "[Service] - tipo MIME: $mimeType")
                
                val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())
                MultipartBody.Part.createFormData("imagen", file.name, requestFile)
            }
            
            Log.d("EACTUALIZAR", "[Service] Llamando al controller...")
            val resultado = controller.actualizar(codigo, nombreBody, precioBody, descripcionBody, imagePart)
            
            Log.d("EACTUALIZAR", "[Service] ✅ Actualización exitosa")
            resultado
        } catch (e: retrofit2.HttpException) {
            Log.e("EACTUALIZAR", "[Service] ❌ HttpException en Service")
            Log.e("EACTUALIZAR", "[Service] Código HTTP: ${e.code()}")
            Log.e("EACTUALIZAR", "[Service] Mensaje: ${e.message()}")
            
            val errorBody = e.response()?.errorBody()?.string()
            if (errorBody != null) {
                Log.e("EACTUALIZAR", "[Service] Error Body: $errorBody")
            }
            
            throw Exception("HTTP ${e.code()}: ${errorBody ?: e.message()}")
        } catch (e: Exception) {
            Log.e("EACTUALIZAR", "[Service] ❌ Error: ${e.message}")
            throw e
        }
    }

    suspend fun eliminar(codigo: String): ResponseDto<Any> = withContext(Dispatchers.IO) {
        controller.eliminar(codigo)
    }
}
