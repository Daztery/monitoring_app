package com.example.monitoringapp.util

sealed class UIViewState<out T : Any> {
    data class Success<out T : Any>(val result: T) : UIViewState<T>()
    data class Error(val message: String) : UIViewState<Nothing>()
    object Loading : UIViewState<Nothing>()
}
