package com.thuatnguyen.tindersample.repository

import com.thuatnguyen.tindersample.api.UserApiService
import com.thuatnguyen.tindersample.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class UserRepository(private val userApiService: UserApiService) {

    fun getUsersFromNetwork(): Flow<List<User>> {
        return flow {
            val user = userApiService.getUsers().results.map { it.user }
            emit(user)
        }.flowOn(Dispatchers.IO)
    }
}