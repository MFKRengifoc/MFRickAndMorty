package com.manoffocus.mfrickandmorty.data

sealed class Resource<T>(var data: T? = null, var message: String? = null, var code: RepositoryExceptionCodes? = null){
    class Success<T>(data: T?, code: RepositoryExceptionCodes): Resource<T>(data = data)
    class Error<T>(message: String, data: T? = null, code: RepositoryExceptionCodes?): Resource<T>(data = data, message = message, code = code)
    class Loading<T>(data: T? = null): Resource<T>(data = data)
    class Empty<T>(data: T? = null): Resource<T>(data = data)
}