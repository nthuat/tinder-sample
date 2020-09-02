package com.thuatnguyen.tindersample.api

import com.google.common.truth.Truth.assertThat
import com.thuatnguyen.tindersample.model.Result
import com.thuatnguyen.tindersample.model.UserResponse
import com.thuatnguyen.tindersample.util.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.reset
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
class UserRemoteDataSourceTest {
    private lateinit var userRemoteDataSource: UserRemoteDataSource
    private val apiService = mock(UserApiService::class.java)

    @Before
    fun setUp() {
        userRemoteDataSource = UserRemoteDataSource(apiService)
    }

    @After
    fun tearDown() {
        reset(apiService)
    }

    @Test
    fun getUsers_withSuccess() = runBlockingTest {
        Mockito.`when`(apiService.getUsers()).thenReturn(userResponse)
        val result = userRemoteDataSource.getUsers()
        assertThat(result).isEqualTo(Result.Success(listOf(testUser)))
    }


    @Test
    fun getUsers_withError() = runBlockingTest {
        val failureResponse = Response.error<UserResponse>(
            503,
            errorJson.toResponseBody("".toMediaTypeOrNull())
        )
        Mockito.`when`(apiService.getUsers()).thenThrow(HttpException(failureResponse))
        val result = userRemoteDataSource.getUsers()
        assertThat(result).isEqualTo(
            Result.Error(errorResponse.error)
        )
    }
}