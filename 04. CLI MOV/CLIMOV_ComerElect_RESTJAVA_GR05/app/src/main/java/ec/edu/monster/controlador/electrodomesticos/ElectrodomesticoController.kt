package ec.edu.monster.controlador.electrodomesticos

import ec.edu.monster.modelo.ElectrodomesticoDTO
import ec.edu.monster.modelo.ResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface ElectrodomesticoController {

    @GET("/api/electrodomesticos")
    suspend fun listar(): List<ElectrodomesticoDTO>

    @GET("/api/electrodomesticos/{codigo}")
    suspend fun obtener(@Path("codigo") codigo: String): ElectrodomesticoDTO

    @Multipart
    @POST("/api/electrodomesticos")
    suspend fun crear(
        @Part("nombre") nombre: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part("descripcion") descripcion: RequestBody?,
        @Part imagen: MultipartBody.Part?
    ): ElectrodomesticoDTO

    @Multipart
    @PUT("/api/electrodomesticos/{codigo}")
    suspend fun actualizar(
        @Path("codigo") codigo: String,
        @Part("nombre") nombre: RequestBody,
        @Part("precio") precio: RequestBody,
        @Part("descripcion") descripcion: RequestBody?,
        @Part imagen: MultipartBody.Part?
    ): ElectrodomesticoDTO

    @DELETE("/api/electrodomesticos/{codigo}")
    suspend fun eliminar(@Path("codigo") codigo: String): ResponseDto<Any>
}
