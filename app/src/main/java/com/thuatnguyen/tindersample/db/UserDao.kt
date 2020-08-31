package com.thuatnguyen.tindersample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.thuatnguyen.tindersample.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user")
    fun getAll(): Flow<List<User>>

    @Insert
    fun insert(user: User)
}