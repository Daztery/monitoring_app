package com.example.monitoringapp.data.model

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
    var refreshToken: String? = ""
)

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
)

data class Doctor(
    var id: Int? = 0,
    var firstName: String? = "",
    var lastName: String? = "",
    var birthdate: String? = "",
    var phone: String = "",
    var userId: Int? = 0,
    var medicalCenterId: Int? = 0,
)
