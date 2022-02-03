package com.example.monitoringapp.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {

    private const val NAME = "APP_DATA"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    private val USER_TOKEN = Pair("token", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    fun clear() {
        token = ""
    }

    private inline fun SharedPreferences.edit(
        operation: (SharedPreferences.Editor) -> Unit
    ) {
        val editor = edit()
        operation(editor)
        editor.apply()
    }

    fun userIsAuthenticated(): Boolean {
        return !token.isNullOrBlank()
    }

    var token: String?
        get() = preferences.getString(USER_TOKEN.first, USER_TOKEN.second)
        set(value) = preferences.edit {
            it.putString(USER_TOKEN.first, value)
        }

}