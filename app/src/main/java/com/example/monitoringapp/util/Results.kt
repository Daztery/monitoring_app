package com.example.monitoringapp.util

import com.example.monitoringapp.data.network.response.CollectionResponse

sealed class OperationResult<out T> {
    data class Success<T>(val data: T?) : OperationResult<T>()
    data class Error(val exception: String) : OperationResult<Nothing>()
}

sealed class CallListResult<out T> {
    data class Success<T>(val data: List<T>) : CallListResult<CollectionResponse<T>>()
    data class Error(val exception: String) : CallListResult<Nothing>()
}

sealed class CallCodeResult<out T> {
    data class Success<T>(val code: Int) : CallCodeResult<T>()
    data class Error(val exception: String) : CallCodeResult<Nothing>()
}




