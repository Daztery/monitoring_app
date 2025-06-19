package com.example.monitoringapp.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesHelper {

    private const val NAME = "APP_DATA"
    private const val MODE = Context.MODE_PRIVATE
    private lateinit var preferences: SharedPreferences

    //User Data
    private val USER_TOKEN = Pair("token", "")
    private val USER_DATA = Pair("user_data", "")
    private val TYPE_USER = Pair("type_user", "")
    private val MEDICAL_CENTER = Pair("medical_center", "")
    private val PRIORITY_TYPE = Pair("priority_type", "")

    fun init(context: Context) {
        preferences = context.getSharedPreferences(NAME, MODE)
    }

    fun clear() {
        token = ""
        userData = ""
        type = ""
        medicalCenter = ""
        priorityType = ""
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

    var userData: String?
        get() = preferences.getString(USER_DATA.first, USER_DATA.second)
        set(value) = preferences.edit {
            it.putString(USER_DATA.first, value)
        }

    var type: String?
        get() = preferences.getString(TYPE_USER.first, TYPE_USER.second)
        set(value) = preferences.edit {
            it.putString(TYPE_USER.first, value)
        }

    var medicalCenter: String?
        get() = preferences.getString(MEDICAL_CENTER.first, MEDICAL_CENTER.second)
        set(value) = preferences.edit {
            it.putString(MEDICAL_CENTER.first, value)
        }

    var priorityType: String?
        get() = preferences.getString(PRIORITY_TYPE.first, PRIORITY_TYPE.second)
        set(value) = preferences.edit {
            it.putString(PRIORITY_TYPE.first, value)
        }

}