package com.mukshi.blisscart.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPref (context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "your_app_prefs"
        private const val KEY_USERNAME = "username"
    }

    fun saveUsername(username: String) {
        val editor = prefs.edit()
        editor.putString(KEY_USERNAME, username)
        editor.apply()
    }

    fun getUsername(): String? {
        return prefs.getString(KEY_USERNAME, null)
    }
}