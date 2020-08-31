package com.thuatnguyen.tindersample.model

data class User(
    val username: String,
    val email: String,
    val dob: String,
    val name: Name,
    val location: Location,
    val password: String,
    val phone: String,
    val picture: String,
    val gender: String?,
    val md5: String?,
    val registered: String?,
    val sSN: String?,
    val salt: String?,
    val sha1: String?,
    val sha256: String?,
    val cell: String?
)

data class Name(
    val first: String,
    val last: String,
    val title: String
) {
    fun getFullName(): String {
        return "$first $last"
    }
}

data class Location(
    val city: String,
    val state: String,
    val street: String,
    val zip: String
)