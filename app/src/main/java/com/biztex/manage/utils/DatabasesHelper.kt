package com.biztex.manage.utils

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.biztex.manage.model.User

class DatabasesHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserManage.db"
        private const val TABLE_USER = "user"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_USER_NAME = "user_name"
        private const val COLUMN_USER_PASSWORD = "user_password"
    }
    // SQL statement to create the user table
    private val CREATE_USER_TABLE = (
            "CREATE TABLE $TABLE_USER(" +
                    "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_USER_NAME TEXT," +
                    "$COLUMN_USER_PASSWORD TEXT" + ")")

    // SQL statement to drop the user table
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE)
        onCreate(db)
    }

    fun addUser(user: User) {
        val db = this.writableDatabase

        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.username)
        values.put(COLUMN_USER_PASSWORD, user.password)

        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun validateUser(username: String, password: String): Boolean {
        val columns = arrayOf(COLUMN_USER_ID)
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_NAME = ? AND $COLUMN_USER_PASSWORD = ?"
        val selectionArgs = arrayOf(username, password)

        val cursor = db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null)
        val cursorCount = cursor.count
        cursor.close()
        db.close()

        return cursorCount > 0
    }

}