package com.thuatnguyen.tindersample.api

import com.thuatnguyen.tindersample.model.Result
import com.thuatnguyen.tindersample.util.safeApiCall
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(private val apiService: UserApiService) {

    suspend fun getUsers() = safeApiCall {
        Result.Success(apiService.getUsers().results.map { it.user })
    }
}