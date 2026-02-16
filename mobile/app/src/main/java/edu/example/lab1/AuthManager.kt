package edu.example.lab1

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

class AuthManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences("auth", Context.MODE_PRIVATE)
    private val gson = Gson()

    fun saveToken(token: String) {
        prefs.edit().putString("token", token).apply()
    }

    fun getToken(): String? {
        return prefs.getString("token", null)
    }

    fun saveUser(user: User) {
        val json = gson.toJson(user)
        prefs.edit().putString("user", json).apply()
    }

    fun getUser(): User? {
        val json = prefs.getString("user", null)
        return if (json != null) gson.fromJson(json, User::class.java) else null
    }

    fun logout() {
        prefs.edit().clear().apply()
    }

    fun isLoggedIn(): Boolean {
        return getToken() != null
    }
}
