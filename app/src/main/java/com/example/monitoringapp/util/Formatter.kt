package com.example.monitoringapp.util

import java.text.SimpleDateFormat
import java.util.*

object Formatter {

    fun capitalizeWords(text: String): String {

        if ("#" in text) return text

        val lowerText = text.lowercase()

        val capitalized = lowerText.split(" ").joinToString(" ") { letter ->
            letter.replaceFirstChar { it.titlecase(Locale.getDefault()) }
        }.trimEnd()

        return capitalized
    }

    fun getLocaleDate(date: String): Date? {
        val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
        parser.timeZone = TimeZone.getTimeZone("UTC")
        val utcDate = parser.parse(date)
        parser.timeZone = TimeZone.getDefault()

        if (utcDate == null) {
            return null
        }

        val parsedDateStr = parser.format(utcDate)

        return parser.parse(parsedDateStr)
    }

    fun formatUTCDate(date: Date): String{
        val parser = SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.ENGLISH)
        parser.timeZone = TimeZone.getTimeZone("UTC")

        return parser.format(date)
    }

    fun formatLocalDate(date: Date): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(date)
    }

    fun formatLocalYearFirstDate(date: Date): String {
        val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
        return formatter.format(date)
    }

    fun formatDate(date: String): String {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val parsedDate = getLocaleDate(date)

        return if (parsedDate == null) {
            date
        } else {
            formatter.format(parsedDate)
        }
    }

    fun formatHour(date: String): String {
        val formatter = SimpleDateFormat("HH:mm:ss a", Locale.getDefault())
        val parsedDate = getLocaleDate(date)

        return if (parsedDate == null) {
            date
        } else {
            formatter.format(parsedDate)
        }
    }

    fun formatHourShorted(date: String): String {
        val formatter = SimpleDateFormat("HH:mm a", Locale.getDefault())
        val parsedDate = getLocaleDate(date)

        return if (parsedDate == null) {
            date
        } else {
            formatter.format(parsedDate)
        }
    }

    fun formatDateDayOfTheWeek(date: String): String {
        val formatter = SimpleDateFormat("EEEE dd", Locale.getDefault())
        val parsedDate = getLocaleDate(date)

        return if (parsedDate == null) {
            date
        } else {
            val finalDate = formatter.format(parsedDate)
            finalDate.replaceFirstChar { it.titlecase(Locale.getDefault()) }
        }
    }

    fun formatDateMonthYear(date: String): String {
        val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        val parsedDate = getLocaleDate(date)

        return if (parsedDate == null) {
            date
        } else {
            val finalDate = formatter.format(parsedDate)
            finalDate.replaceFirstChar { it.titlecase(Locale.getDefault()) }
        }
    }
}