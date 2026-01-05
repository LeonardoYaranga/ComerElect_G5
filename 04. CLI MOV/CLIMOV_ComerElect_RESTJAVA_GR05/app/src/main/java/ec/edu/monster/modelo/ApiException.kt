package ec.edu.monster.modelo

/**
 * Excepción personalizada para errores del backend
 */
class ApiException(
    message: String,
    val errorType: String? = null,
    val httpCode: Int? = null
) : Exception(message)
