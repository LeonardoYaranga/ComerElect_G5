package ec.edu.monster.controlador.facturas

import ec.edu.monster.modelo.FacturaRequest
import ec.edu.monster.modelo.FacturaResponse
import ec.edu.monster.modelo.banquito.AmortizacionDTO
import retrofit2.http.*

interface FacturaApi {

    @POST("/api/facturas")
    suspend fun generarFactura(@Body request: FacturaRequest): FacturaResponse

    @GET("/api/facturas/{numFactura}")
    suspend fun obtenerFactura(@Path("numFactura") numFactura: String): FacturaResponse

    @GET("/api/facturas")
    suspend fun obtenerTodas(): List<FacturaResponse>

    @GET("/api/facturas/{numFactura}/amortizacion")
    suspend fun obtenerAmortizacion(@Path("numFactura") numFactura: String): List<AmortizacionDTO>
}
