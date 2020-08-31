package com.thuatnguyen.tindersample.model

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey
    val username: String,
    val email: String,
    val dob: String,
    @Embedded
    val name: Name,
    @Embedded
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