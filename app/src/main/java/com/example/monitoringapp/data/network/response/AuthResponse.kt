package com.example.monitoringapp.data.network.response

import com.example.monitoringapp.data.model.User
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    var success: Boolean?,
    @SerializedName("data")
    var userData: User?
)
/*
data class ExtendTokenResponse(
    var success: Boolean,
    @SerializedName("data")
    var token: String?
)

data class GetSelfUserResponse(
    var success: Boolean?,
    @SerializedName("data")
    var user: User?
)*/