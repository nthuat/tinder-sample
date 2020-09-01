package com.thuatnguyen.tindersample.model

data class UserResponse(
    val results: List<UserInfo>
)

data class UserInfo(
    val user: User,
    val seed: String?,
    val version: String?
)