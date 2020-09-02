package com.thuatnguyen.tindersample.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.thuatnguyen.tindersample.util.MainCoroutineRule
import com.thuatnguyen.tindersample.util.getOrAwaitValue
import com.thuatnguyen.tindersample.model.Result
import com.thuatnguyen.tindersample.model.User
import com.thuatnguyen.tindersample.repository.UserRepository
import com.thuatnguyen.tindersample.util.testUser
import com.thuatnguyen.tindersample.util.userResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*

@ExperimentalCoroutinesApi
class UserViewModelTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private val repository = mock(UserRepository::class.java)
    private lateinit var viewModel: UserViewModel

    @Before
    fun setUp() {
        viewModel = UserViewModel(repository)
    }

    @Test
    fun getFavoriteUsers_withSuccess() = runBlocking {
        val fakeUsers = listOf(testUser)
        val expected = Result.Success(fakeUsers)
        `when`(repository.getFavoriteUsersFromLocal()).thenReturn(flowOf((expected)))

        viewModel.getUsers(true)

        val result = viewModel.userLiveData.getOrAwaitValue()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun getFavoriteUsers_withError() = runBlocking {
        val error = Result.Error("Unexpected error!")
        `when`(repository.getFavoriteUsersFromLocal()).thenReturn(flowOf((error)))

        viewModel.getUsers(true)

        val result = viewModel.userLiveData.getOrAwaitValue()
        assertThat(result).isEqualTo(error)
    }

    @Test
    fun getUsersFromNetwork_withSuccess() = runBlocking {
        val fakeUser = userResponse.results.map { it.user }
        val expected = Result.Success(fakeUser)
        `when`(repository.getUsersFromNetwork()).thenReturn(flowOf((expected)))

        viewModel.getUsers(false)

        val result = viewModel.userLiveData.getOrAwaitValue()
        assertThat(result).isEqualTo(expected)
    }

    @Test
    fun getUsersFromNetwork_withError() = runBlocking {
        val serverError = Result.Error("Server error!")
        `when`(repository.getUsersFromNetwork()).thenReturn(flowOf((serverError)))

        viewModel.getUsers(false)

        val result = viewModel.userLiveData.getOrAwaitValue()
        assertThat(result).isEqualTo(serverError)
    }

    @Test
    fun saveFavoriteUser_withSuccess() = runBlocking {
        val fakeUsers = userResponse.results.map { it.user }
        `when`(repository.getUsersFromNetwork()).thenReturn(flowOf((Result.Success(fakeUsers))))
        viewModel.getUsers(false)

        val position = 0
        viewModel.saveFavoriteUser(position)
        verify(repository).saveFavoriteUser(fakeUsers[position])
    }

    @Test
    fun saveFavoriteUser_withError() = runBlocking {
        val fakeUsers = userResponse.results.map { it.user }
        `when`(repository.getUsersFromNetwork()).thenReturn(flowOf((Result.Success(fakeUsers))))
        viewModel.getUsers(false)

        val position = 10

        viewModel.saveFavoriteUser(position)
        verify(repository, never()).saveFavoriteUser(any(User::class.java))

    }

    @Test
    fun resetUserData() {
        viewModel.resetUserData()
        assertThat(viewModel.userLiveData.getOrAwaitValue()).isNull()
    }
}

private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)
