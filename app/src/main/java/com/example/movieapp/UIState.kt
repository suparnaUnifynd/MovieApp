package com.example.movieapp

//sealed class UIState<T>(
//    val data: T? = null,
//    val message: String? = null
//) {
//    class Success<T>(data: T) : UIState<T>(data)
//    class Loading<T>(data: T? = null) : UIState<T>(data)
//    class Error<T>(message: String, data: T? = null) : UIState<T>(data, message)
//}
sealed class UIState {
    object Loading: UIState()
    data class Error(val message: String): UIState()
    data class Success<out T>(val data: T): UIState()
    data class LoadingDueTo(val reason: String = ""): UIState()
}
