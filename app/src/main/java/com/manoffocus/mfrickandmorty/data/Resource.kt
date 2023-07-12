package com.manoffocus.mfrickandmorty.data

sealed class Resource<T>(var data: T? = null, var message: String? = null, var code: RepositoryExceptionCodes? = null, loading: Boolean = false){
    class Success<T>(data: T?, loading: Boolean = false): Resource<T>(data = data, loading = loading)
    class Error<T>(message: String, data: T? = null, code: RepositoryExceptionCodes? = null): Resource<T>(data = data, message = message, code = code)
    class Loading<T>(data: T? = null, loading: Boolean = false): Resource<T>(data = data, loading = loading)
    class Empty<T>(data: T? = null, loading: Boolean = false): Resource<T>(data = data, loading = loading)
}