package com.thuatnguyen.tindersample.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thuatnguyen.tindersample.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}