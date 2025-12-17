package ec.edu.monster.controlador.auth

import android.content.Context
import android.content.SharedPreferences
import ec.edu.monster.modelo.auth.Rol
import ec.edu.monster.modelo.auth.Usuario

/**
 * Gestor de sesión del usuario.
 * Guarda: cédula, username, rol y nombre completo.
 */
class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "AppPrefs"
        private const val KEY_CEDULA = "cedula"
        private const val KEY_USERNAME = "username"
        private const val KEY_ROL = "rol"
        private const val KEY_NOMBRE_COMPLETO = "nombreCompleto"
    }

    /**
     * Guarda la sesión completa del usuario.
     */
    fun guardarSesion(usuario: Usuario) {
        prefs.edit().apply {
            putString(KEY_CEDULA, usuario.cedula)
            putString(KEY_USERNAME, usuario.username)
            putString(KEY_ROL, usuario.rol.name)
            putString(KEY_NOMBRE_COMPLETO, usuario.nombreCompleto)
            apply()
        }
    }

    /**
     * Guarda solo la cédula (compatibilidad con código anterior).
     */
    fun saveCedula(cedula: String) {
        prefs.edit().putString(KEY_CEDULA, cedula).apply()
    }

    /**
     * Obtiene la cédula del usuario logueado.
     */
    fun getCedula(): String? {
        return prefs.getString(KEY_CEDULA, null)
    }

    /**
     * Obtiene el username del usuario logueado.
     */
    fun getUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }

    /**
     * Obtiene el rol del usuario logueado.
     */
    fun getRol(): Rol? {
        val rolString = prefs.getString(KEY_ROL, null)
        return rolString?.let { 
            try {
                Rol.valueOf(it)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }

    /**
     * Obtiene el nombre completo del usuario logueado.
     */
    fun getNombreCompleto(): String? {
        return prefs.getString(KEY_NOMBRE_COMPLETO, null)
    }

    /**
     * Verifica si el usuario logueado es ADMIN.
     */
    fun esAdmin(): Boolean {
        return getRol() == Rol.ADMIN
    }

    /**
     * Verifica si el usuario logueado es CLIENTE.
     */
    fun esCliente(): Boolean {
        return getRol() == Rol.CLIENTE
    }

    /**
     * Verifica si hay una sesión activa.
     */
    fun hayUsuarioLogueado(): Boolean {
        return getCedula() != null && getRol() != null
    }

    /**
     * Cierra la sesión y limpia todos los datos.
     */
    fun clearSession() {
        prefs.edit().clear().apply()
    }
}