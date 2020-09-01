package com.thuatnguyen.tindersample.repository

import com.thuatnguyen.tindersample.api.UserApiService
import com.thuatnguyen.tindersample.db.UserDao
import com.thuatnguyen.tindersample.model.Result
import com.thuatnguyen.tindersample.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userApiService: UserApiService,
    private val userDao: UserDao
) {
    fun getUsersFromNetwork(): Flow<Result<List<User>>> {
        return flow {
            emit(Result.Loading)
            val user = userApiService.getUsers().results.map { it.user }
            emit(Result.Success(user))
        }.catch { emit(Result.Error(it.message ?: "Unexpected error!")) }
            .flowOn(Dispatchers.IO)
    }

    fun getFavoriteUsers(): Flow<Result<List<User>>> {
        return userDao.getAll()
            .map { if (it.isNotEmpty()) Result.Success(it) else Result.Error("No data found!") }
    }

    fun saveFavoriteUser(user: User) {
        userDao.insert(user)
    }
}