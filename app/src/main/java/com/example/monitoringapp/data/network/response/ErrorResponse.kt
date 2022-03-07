package com.example.monitoringapp.data.network.response

data class ErrorResponse(
    var name: String = "",
    var message: String = "",
    var statusCode: Int = 0,
)

data class DataErrorResponse<T>(
    var success: Boolean = false,
    var message: String,
    var data: T? = null,
)

data class GenericErrorResponse(
    var success: Boolean = false,
    var error: ErrorResponse
)