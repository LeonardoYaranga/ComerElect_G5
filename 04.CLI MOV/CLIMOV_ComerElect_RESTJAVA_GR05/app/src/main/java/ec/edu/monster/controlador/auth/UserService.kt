package ec.edu.monster.controlador.auth

import ec.edu.monster.modelo.auth.Rol
import ec.edu.monster.modelo.auth.Usuario
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Servicio de autenticación local con usuarios hardcoded.
 * Replica la lógica de AccountController.java de la aplicación web.
 */
class UserService {

    companion object {
        /**
         * Usuarios quemados en el sistema (igual que en la app web Java).
         */
        private val USUARIOS_QUEMADOS = listOf(
            // 👤 ADMIN
            Usuario(
                username = "MONSTER",
                cedula = "1111111111",
                password = "MONSTER9",
                rol = Rol.ADMIN,
                nombreCompleto = "Administrador"
            ),
            // 👥 CLIENTES
            Usuario(
                username = "JOEL",
                cedula = "0102030405",
                password = "JOEL9",
                rol = Rol.CLIENTE,
                nombreCompleto = "Joel Cliente"
            ),
            Usuario(
                username = "DOME",
                cedula = "0203040506",
                password = "DOME9",
                rol = Rol.CLIENTE,
                nombreCompleto = "Domenica Cliente"
            ),
            Usuario(
                username = "LEO",
                cedula = "0304050607",
                password = "LEO9",
                rol = Rol.CLIENTE,
                nombreCompleto = "Leonardo Cliente"
            ),
            Usuario(
                username = "JOE",
                cedula = "0405060708",
                password = "JOE9",
                rol = Rol.CLIENTE,
                nombreCompleto = "JOE BROWN"
            )
        )
    }

    /**
     * Valida credenciales y devuelve el usuario si es válido.
     * @param usernameOrCedula Puede ser el username o la cédula
     * @param password Contraseña del usuario
     * @return Usuario si las credenciales son válidas, null en caso contrario
     */
    suspend fun login(usernameOrCedula: String, password: String): Usuario? = withContext(Dispatchers.Default) {
        USUARIOS_QUEMADOS.find { usuario ->
            (usuario.username.equals(usernameOrCedula, ignoreCase = true) || 
             usuario.cedula == usernameOrCedula) && 
            usuario.password == password
        }
    }

    /**
     * Obtiene un usuario por su cédula.
     */
    fun obtenerUsuarioPorCedula(cedula: String): Usuario? {
        return USUARIOS_QUEMADOS.find { it.cedula == cedula }
    }

    /**
     * Obtiene todos los usuarios (útil para debug/testing).
     */
    fun obtenerTodosLosUsuarios(): List<Usuario> = USUARIOS_QUEMADOS
}
