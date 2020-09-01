package com.thuatnguyen.tindersample.model


sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val message: String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}

val Result<*>?.isSucceeded
    get() = this is Result.Success && data != null

val Result<*>?.isLoading
    get() = this is Result.Loading

val Result<*>?.isError
    get() = this is Result.Error

val <T> Result<T>.data: T?
    get() = (this as? Result.Success)?.data

val Result<*>?.message: String?
    get() = (this as? Result.Error)?.message