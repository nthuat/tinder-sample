package com.thuatnguyen.tindersample.util

import com.google.gson.GsonBuilder
import com.thuatnguyen.tindersample.model.ErrorResponse
import com.thuatnguyen.tindersample.model.Result
import retrofit2.HttpException
import java.io.IOException

suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>): Result<T> {
    return try {
        call()
    } catch (exception: Exception) {
        Result.Error(parseErrorMessage(exception))
    }
}

fun parseErrorMessage(exception: Exception): String {
    return when (exception) {
        is HttpException -> {
            convertErrorBody(exception)?.error ?: exception.message()
        }
        is IOException -> {
            "No internet connection!"
        }
        else -> {
            "Error: ${exception.message}"
        }
    }
}

private fun convertErrorBody(throwable: HttpException): ErrorResponse? {
    return try {
        throwable.response()?.errorBody()?.string()?.let {
            GsonBuilder().create().fromJson(it, ErrorResponse::class.java)
        }
    } catch (exception: Exception) {
        null
    }
}

