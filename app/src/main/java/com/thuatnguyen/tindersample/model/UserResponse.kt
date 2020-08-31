package com.thuatnguyen.tindersample.model

data class UserResponse(
    val results: List<Result>
)

data class Result(
    val seed: String,
    val user: User,
    val version: String
)