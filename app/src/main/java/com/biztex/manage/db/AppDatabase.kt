package com.biztex.manage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.biztex.manage.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}