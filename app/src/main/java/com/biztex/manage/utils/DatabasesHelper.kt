package com.biztex.manage.utils

import android.content.Context
import androidx.room.Room
import com.biztex.manage.db.AppDatabase

class DatabasesHelper(context: Context) {
    private val db: AppDatabase = Room.databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java, "app_database"
    ).fallbackToDestructiveMigration().build()

    fun getDatabase(): AppDatabase{
        return db
    }
}