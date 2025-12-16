package ec.edu.monster.controlador.auth

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/**
 * Servicio de autenticación local (sin llamadas a backend).
 * Devuelve true sólo si username == "MONSTER" y password == "MONSTER9".
 */
class UserService {

    /**
     * Valida credenciales localmente. Mantiene la firma suspend para que ViewModels
     * puedan llamarlo desde coroutines sin cambiar lógica.
     */
    suspend fun login(username: String, password: String): Boolean = withContext(Dispatchers.Default) {
        username == "MONSTER" && password == "MONSTER9"
    }
}
