package com.biztex.manage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.biztex.manage.model.User

@Dao
interface UserDao{
    @Insert
    fun insertUser(user: Int)

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    fun getUser(username: String, password: String): User?

}