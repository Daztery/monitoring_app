package com.example.monitoringapp.data.network.request

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
    var oldPassword: String = "",
    var newPassword: String = ""
)
