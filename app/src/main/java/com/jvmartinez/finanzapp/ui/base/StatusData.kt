package com.jvmartinez.finanzapp.ui.base

sealed class StatusData<out T> {
    object Loading : StatusData<Nothing>()
    data class Success<out T>(val data: T) : StatusData<T>()
    data class Error(val error: String, val exception: Throwable? = null) : StatusData<Nothing>()
    object Empty : StatusData<Nothing>()
}
