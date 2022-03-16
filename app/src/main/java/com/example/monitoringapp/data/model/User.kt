package com.example.monitoringapp.data.model

import com.example.monitoringapp.util.Formatter
import java.io.Serializable
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class User(
    var id: Int? = 0,
    var email: String? = "",
    var idType: String? = "",
    var identification: String? = "",
    var createdAt: String? = "",
    var updatedAt: String? = "",
    var patient: Patient? = Patient(),
    var doctor: Doctor? = Doctor(),
    var token: String? = "",
    var refreshToken: String? = "",
) : Serializable

data class Patient(
    var id: Int? = 0,
    var firstName: String? = "",
    var lastName: String? = "",
    var birthdate: String? = "",
    var phone: String = "",
    var height: Int? = 0,
    var weight: Int? = 0,
    var bloodType: String? = "",
    var userId: Int? = 0,
    var status: String? = ""
) : Serializable {
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    fun getBirthday(): String {
        val date = Formatter.getLocaleDate(birthdate ?: "")
        return Formatter.formatLocalDate(date ?: Date())
    }

    fun getAge(): Int {

        var date: Date? = null
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        try {
            date = sdf.parse(getBirthday())
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date == null) return 0

        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.time = date

        val year = dob[Calendar.YEAR]
        val month = dob[Calendar.MONTH]
        val day = dob[Calendar.DAY_OF_MONTH]

        dob[year, month + 1] = day

        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]

        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }

        return age
    }
}

data class Doctor(
    var id: Int? = 0,
    var firstName: String? = "",
    var lastName: String? = "",
    var birthdate: String? = "",
    var phone: String = "",
    var userId: Int? = 0,
    var medicalCenterId: Int? = 0,
    var medicalCenter: MedicalCenter? = MedicalCenter()
) : Serializable {
    fun getFullName(): String {
        return "$firstName $lastName"
    }

    fun getBirthday(): String {
        val date = Formatter.getLocaleDate(birthdate ?: "")
        return Formatter.formatLocalDate(date ?: Date())
    }

    fun getAge(): Int {

        var date: Date? = null
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        try {
            date = sdf.parse(getBirthday())
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (date == null) return 0

        val dob = Calendar.getInstance()
        val today = Calendar.getInstance()

        dob.time = date

        val year = dob[Calendar.YEAR]
        val month = dob[Calendar.MONTH]
        val day = dob[Calendar.DAY_OF_MONTH]

        dob[year, month + 1] = day

        var age = today[Calendar.YEAR] - dob[Calendar.YEAR]

        if (today[Calendar.DAY_OF_YEAR] < dob[Calendar.DAY_OF_YEAR]) {
            age--
        }

        return age
    }
}
