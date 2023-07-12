package com.manoffocus.mfrickandmorty.data

sealed class ResourceUser<T>(var data: T? = null, var message: String? = null, var code: Int? = null){
    class Success<T>(data: T?): ResourceUser<T>(data)
    class Error<T>(message: String, data: T? = null, code: Int? = null): ResourceUser<T>(data, message, code)
    class Loading<T>(data: T? = null): ResourceUser<T>(data)
    class Empty<T>(data: T? = null): ResourceUser<T>(data)
}