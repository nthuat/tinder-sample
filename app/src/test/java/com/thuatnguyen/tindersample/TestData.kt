package com.thuatnguyen.tindersample

import com.thuatnguyen.tindersample.model.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

val userResponse = UserResponse(
    listOf(
        UserInfo(
            User(
                "thuat26",
                "thuat@gmail.com", "91989223",
                Name("Thuat", "Nguyen", "Mr"),
                Location("HCM", "HCM", "Au Co", "70000"),
                "123456", "09123456789", "",
                null, null, null, null, null, null, null, null
            ), null, null
        )
    )
)
const val errorJson = "{\n" +
        "  error: \"Uh oh, something has gone wrong. Please tweet us @randomapi about the issue. Thank you.\"\n" +
        "}"

val errorResponseBody = errorJson.toResponseBody("".toMediaTypeOrNull())

