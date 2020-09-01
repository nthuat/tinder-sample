package com.thuatnguyen.tindersample.repository

import com.thuatnguyen.tindersample.api.UserRemoteDataSource
import com.thuatnguyen.tindersample.db.UserDao
import com.thuatnguyen.tindersample.model.Result
import com.thuatnguyen.tindersample.model.User
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val remoteDataSource: UserRemoteDataSource,
    private val userDao: UserDao,
    private val dispatcher: CoroutineDispatcher
) {
    fun loadUsersFromNetwork(): Flow<Result<List<User>>> {
        return flow {
            emit(Result.Loading)
            emit(remoteDataSource.getUsers())
        }.flowOn(dispatcher)
    }

    fun loadFavoriteUsers(): Flow<Result<List<User>>> {
        return flow {
            emit(Result.Loading)
            emit(Result.Success(userDao.getAll()))
        }.flowOn(dispatcher)
    }

    fun saveFavoriteUser(user: User) {
        userDao.insert(user)
    }
}