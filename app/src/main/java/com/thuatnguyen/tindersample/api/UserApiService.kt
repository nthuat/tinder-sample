package com.thuatnguyen.tindersample.api

import com.thuatnguyen.tindersample.model.UserResponse
import retrofit2.http.GET

interface UserApiService {

    @GET("?randomapi")
    suspend fun getUsers(): UserResponse

}