package com.thuatnguyen.tindersample.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.thuatnguyen.tindersample.model.Location
import com.thuatnguyen.tindersample.model.Name
import com.thuatnguyen.tindersample.model.User
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserDaoTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var testUser: User

    @Before
    @Throws(Exception::class)
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        userDao = appDatabase.userDao()

        testUser = User(
            "thuat26",
            "thuat@gmail.com", "91989223",
            Name("Thuat", "Nguyen", "Mr"),
            Location("HCM", "HCM", "Au Co", "70000"),
            "123456", "09123456789", "",
            null, null, null, null, null, null, null, null
        )
    }


    @After
    @Throws(Exception::class)
    fun tearDown() {
        appDatabase.close()
    }

    @Test
    fun insertAndRetrieveUser() {
        userDao.insert(testUser)
        val result = userDao.getAll()
        assertThat(result.size).isEqualTo(1)
        assertThat(result[0]).isEqualTo(testUser)
    }
}