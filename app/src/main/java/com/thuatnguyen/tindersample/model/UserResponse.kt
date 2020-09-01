package com.thuatnguyen.tindersample.model

data class UserResponse(
    val results: List<UserInfo>
)

data class UserInfo(
    val seed: String,
    val user: User,
    val version: String
)