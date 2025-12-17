package ec.edu.monster.modelo.auth

/**
 * Modelo de usuario del sistema.
 */
data class Usuario(
    val username: String,
    val cedula: String,
    val password: String,
    val rol: Rol,
    val nombreCompleto: String
)

/**
 * Roles del sistema.
 */
enum class Rol {
    ADMIN,
    CLIENTE
}
