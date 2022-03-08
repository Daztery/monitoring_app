package com.example.monitoringapp.data.network.response

import com.example.monitoringapp.data.model.User
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    var success: Boolean?,
    @SerializedName("data")
    var userData: User?
)

data class LogoutResponse(
    var success: Boolean?,
    @SerializedName("data")
    var message: String?
)


