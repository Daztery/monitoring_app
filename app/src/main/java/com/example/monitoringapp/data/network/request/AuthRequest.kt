package com.example.monitoringapp.data.network.request

import com.example.monitoringapp.data.model.Patient

data class SignInRequest(
    var identification: String,
    var password: String
)

data class RefreshTokenRequest(
    var token: String,
    var refreshToken: String
)

data class RecoverPasswordRequest(
    var identification: String = "",
    var birthdate: Long = 0
)

data class UpdatePasswordRequest(
    var identification: String = "",
    var oldPassword: String = "",
    var newPassword: String = ""
)

data class RegisterPatientRequest(
    var email: String = "",
    var password: String = "",
    var idType: String = "",
    var identification: String = "",
    var patient: PatientRequest = PatientRequest(),
)

data class PatientRequest(
    var firstName: String = "",
    var lastName: String = "",
    var birthdate: Long = 0,
    var phone: String = "",
    var height: Int = 0,
    var weight: Int = 0,
    var bloodType: String = "",
)


