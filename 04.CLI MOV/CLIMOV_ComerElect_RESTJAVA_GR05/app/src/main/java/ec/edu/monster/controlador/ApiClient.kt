package ec.edu.monster.controlador

import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

object ApiClient {
    // Backend compartido via ngrok desde otra computadora
    private const val BASE_URL = "https://7a08bd688cd8.ngrok-free.app"
    
    private val loggingInterceptor = HttpLoggingInterceptor { message ->
        Log.d("HTTP_LOG", message)
    }.apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .connectTimeout(60, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(60, TimeUnit.SECONDS)
        .build()

    // Gson personalizado con adaptadores para LocalDate
    private val gson = GsonBuilder()
        .registerTypeAdapter(
            LocalDate::class.java,
            JsonDeserializer<LocalDate> { json, _, _ ->
                LocalDate.parse(json.asJsonPrimitive.asString, DateTimeFormatter.ISO_LOCAL_DATE)
            }
        )
        .registerTypeAdapter(
            LocalDate::class.java,
            JsonSerializer<LocalDate> { src, _, _ ->
                JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE))
            }
        )
        .setLenient() // Recomendado para APIs reales
        .create()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> create(service: Class<T>): T = retrofit.create(service)
}