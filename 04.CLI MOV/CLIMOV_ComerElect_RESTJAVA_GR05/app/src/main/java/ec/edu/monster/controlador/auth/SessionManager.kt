package ec.edu.monster.controlador.auth

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREF_NAME = "AppPrefs"
        private const val KEY_CEDULA = "cedula"
    }

    fun saveCedula(cedula: String) {
        prefs.edit().putString(KEY_CEDULA, cedula).apply()
    }

    fun getCedula(): String? {
        return prefs.getString(KEY_CEDULA, null)
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}