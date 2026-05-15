package com.example.nammamela

import android.content.Context

object SessionManager {

    private const val PREF = "app_session"
    private const val KEY_ROLE = "role"
    private const val KEY_LOGGED_IN = "logged_in"

    const val ROLE_USER = "user"
    const val ROLE_MANAGER = "manager"

    fun setRole(context: Context, role: String) {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        pref.edit()
            .putString(KEY_ROLE, role)
            .apply()
    }

    fun getRole(context: Context): String {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return pref.getString(KEY_ROLE, ROLE_USER) ?: ROLE_USER
    }

    fun isManager(context: Context): Boolean {
        return getRole(context) == ROLE_MANAGER
    }

    // ✅ NEW: proper session control (IMPORTANT FIX)
    fun setLoggedIn(context: Context, value: Boolean) {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        pref.edit().putBoolean(KEY_LOGGED_IN, value).apply()
    }

    fun isLoggedIn(context: Context): Boolean {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return pref.getBoolean(KEY_LOGGED_IN, false)
    }

    fun clearSession(context: Context) {
        val pref = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)
        pref.edit().clear().apply()
    }
}