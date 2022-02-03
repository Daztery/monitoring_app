package com.example.monitoringapp.util

import android.os.Build
import com.google.gson.Gson
import java.io.Reader
import java.lang.reflect.Type
import java.util.*

object DataUtil {

    private val converter = Gson()

    fun <T> getFromJson(json: String, classOfT: Class<T>): T {
        return converter.fromJson(json, classOfT)
    }

    fun <T> getFromJson(json: Reader, type: Type): T {
        return converter.fromJson(json, type)
    }

    fun <T> stringify(data: T): String {
        return converter.toJson(data)
    }

    fun getDateDiffFromNow(date: Date): Long {
        val current = Calendar.getInstance().time
        return date.time - current.time
    }

    fun getCurrentDate(): Date {
        return Calendar.getInstance().time
    }
}