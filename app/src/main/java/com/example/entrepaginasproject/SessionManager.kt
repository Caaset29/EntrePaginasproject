package com.example.entrepaginasproject

import android.content.Context
import android.content.SharedPreferences
import com.example.entrepaginasproject.api.UserData

class SessionManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("user_session", Context.MODE_PRIVATE)

    fun saveUser(user: UserData) {
        val editor = prefs.edit()
        editor.putInt("ID", user.id)
        editor.putString("NOMBRE", user.nombre)
        editor.putString("ALIAS", user.alias ?: "")
        editor.putString("EMAIL", user.email)
        editor.putString("CELULAR", user.celular ?: "")
        editor.putString("ROL", user.rol)
        editor.apply()
    }

    fun getUser(): UserData {
        return UserData(
            id = prefs.getInt("ID", 0),
            nombre = prefs.getString("NOMBRE", "") ?: "",
            alias = prefs.getString("ALIAS", ""),
            email = prefs.getString("EMAIL", "") ?: "",
            celular = prefs.getString("CELULAR", ""),
            rol = prefs.getString("ROL", "cliente") ?: "cliente"
        )
    }

    fun clearSession() {
        prefs.edit().clear().apply()
    }
}