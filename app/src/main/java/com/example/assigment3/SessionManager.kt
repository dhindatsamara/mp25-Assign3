package com.example.assigment3

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("MEMBER_PREF", Context.MODE_PRIVATE)

    fun saveUserData(name: String, email: String, password: String) {
        prefs.edit().apply {
            putString("NAME", name)
            putString("EMAIL", email)
            putString("PASSWORD", password)
            putBoolean("LOGGED_IN", false)
            apply()
        }
    }

    fun loginUser(name: String, email: String) {
        prefs.edit().apply {
            putBoolean("LOGGED_IN", true)
            putString("NAME", name)
            putString("EMAIL", email)
            apply()
        }
    }

    fun logout() {
        prefs.edit().putBoolean("LOGGED_IN", false).apply()
    }

    fun isLoggedIn(): Boolean = prefs.getBoolean("LOGGED_IN", false)
    fun getName(): String? = prefs.getString("NAME", null)
    fun getEmail(): String? = prefs.getString("EMAIL", null)
    fun getPassword(): String? = prefs.getString("PASSWORD", null)
}
