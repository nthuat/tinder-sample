package com.thuatnguyen.tindersample.util

import com.google.common.truth.Truth.assertThat
import com.thuatnguyen.tindersample.model.UserResponse
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

class NetworkUtilTest {

    @Test
    fun parseErrorMessage_withHttpException() {
        val failureResponse = Response.error<UserResponse>(
            400,
            errorJson.toResponseBody("".toMediaTypeOrNull())
        )
        val errorMessage = parseErrorMessage(HttpException(failureResponse))

        assertThat(errorMessage).isEqualTo(errorResponse.error)
    }

    @Test
    fun parseErrorMessage_withIOException() {
        val errorMessage = parseErrorMessage(IOException())
        assertThat(errorMessage).isEqualTo("No internet connection!")
    }

    @Test
    fun parseErrorMessage_withUnknownException() {
        val unknownException = Exception("Unknown")
        val errorMessage = parseErrorMessage(unknownException)
        assertThat(errorMessage).isEqualTo(
            "Error: ${unknownException.message}"
        )
    }

}