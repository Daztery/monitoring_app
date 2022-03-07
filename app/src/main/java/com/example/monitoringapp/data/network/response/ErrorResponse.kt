package com.example.monitoringapp.data.network.response

data class ErrorResponse(
    var message: String,
    var success: Boolean = false,
)

data class DataErrorResponse<T>(
    var message: String,
    var success: Boolean = false,
    var data: T? = null,
)