package com.thuatnguyen.tindersample.repository

import com.google.common.truth.Truth.assertThat
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.thuatnguyen.tindersample.api.UserApiService
import com.thuatnguyen.tindersample.api.UserRemoteDataSource
import com.thuatnguyen.tindersample.db.UserDao
import com.thuatnguyen.tindersample.errorJson
import com.thuatnguyen.tindersample.errorResponseBody
import com.thuatnguyen.tindersample.model.ErrorResponse
import com.thuatnguyen.tindersample.model.Result
import com.thuatnguyen.tindersample.model.UserResponse
import com.thuatnguyen.tindersample.userResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import retrofit2.HttpException
import retrofit2.Response

@ExperimentalCoroutinesApi
internal class UserRepositoryTest {

    private val dao = mock(UserDao::class.java)
    private val service = mock(UserApiService::class.java)
    private lateinit var remoteDataSource: UserRemoteDataSource
    private lateinit var userRepository: UserRepository
    private val testCoroutinesDispatcher = TestCoroutineDispatcher()
    private lateinit var gson: Gson

    @Before
    fun setUp() {
        gson = GsonBuilder().create()
        remoteDataSource = UserRemoteDataSource(service)
        userRepository = UserRepository(remoteDataSource, dao, testCoroutinesDispatcher)
    }

    @Test
    fun loadUsersFromNetwork_withSuccess() = runBlockingTest {
        val expected = listOf(
            Result.Loading,
            Result.Success(userResponse.results.map { it.user })
        )
        `when`(service.getUsers()).thenReturn(userResponse)
        val results = userRepository.getUsersFromNetwork().toList()
        assertThat(results).isEqualTo(expected)
    }

    @Test
    fun loadUsersFromNetwork_withError() = runBlockingTest {
        val failureResponse = Response.error<UserResponse>(
            503,
            errorResponseBody
        )
        val expected = listOf(
            Result.Loading,
            Result.Error(gson.fromJson(errorJson, ErrorResponse::class.java).error)
        )
        `when`(service.getUsers()).thenThrow(HttpException(failureResponse))
        val results = userRepository.getUsersFromNetwork().toList()
        assertThat(results).isEqualTo(expected)
    }

    @Test
    fun loadFavoriteUsersFromLocal() = runBlockingTest {
        val users = userResponse.results.map { it.user }
        val expected = listOf(
            Result.Loading,
            Result.Success(users)
        )
        `when`(dao.getAll()).thenReturn(users)
        val results = userRepository.getFavoriteUsersFromLocal().toList()
        assertThat(results).isEqualTo(expected)
    }

}