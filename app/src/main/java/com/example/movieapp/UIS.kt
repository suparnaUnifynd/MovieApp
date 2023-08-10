package com.example.movieapp

sealed class UIS {
    object Loading: UIS()
    data class Error(val message: String): UIS()
    data class Success<out T>(val data: T): UIS()
    data class LoadingDueTo(val reason: String = ""): UIS()
}
