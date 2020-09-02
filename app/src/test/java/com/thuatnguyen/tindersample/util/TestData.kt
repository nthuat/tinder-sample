package com.thuatnguyen.tindersample.util

import com.thuatnguyen.tindersample.model.*
import org.mockito.Mockito

val testUser = User(
    "thuat26",
    "thuat@gmail.com", "91989223",
    Name("Thuat", "Nguyen", "Mr"),
    Location("HCM", "HCM", "Au Co", "70000"),
    "123456", "09123456789", "",
    null, null, null, null, null, null, null, null
)

val testUser2 = User(
    "andrew",
    "andrew@xyz.com", "930930388",
    Name("Andrew", "Nguyen", "Mr"),
    Location("HCM", "HCM", "BT", "70000"),
    "919911", "090939023", "",
    null, null, null, null, null, null, null, null
)

val userResponse = UserResponse(
    listOf(
        UserInfo(testUser, null, null)
    )
)
const val errorJson = "{\n" +
        "  error: \"Uh oh, something has gone wrong. Please tweet us @randomapi about the issue. Thank you.\"\n" +
        "}"

val errorResponse =
    ErrorResponse("Uh oh, something has gone wrong. Please tweet us @randomapi about the issue. Thank you.")

fun <T> anyClass(type: Class<T>): T = Mockito.any<T>(type)