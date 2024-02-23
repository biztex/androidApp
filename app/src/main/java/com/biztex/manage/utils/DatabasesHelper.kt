package com.biztex.manage.utils

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.biztex.manage.model.User
import at.favre.lib.crypto.bcrypt.BCrypt

class DatabasesHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserManage.db"
        private const val TABLE_USER = "user"
        private const val COLUMN_USER_ID = "user_id"
        private const val COLUMN_USER_NAME = "user_name"
        private const val COLUMN_USER_EMAIL = "user_email"
        private const val COLUMN_USER_PASSWORD = "user_password"
    }
    // SQL statement to create the user table
    private val CREATE_USER_TABLE = (
            "CREATE TABLE $TABLE_USER(" +
                    "$COLUMN_USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "$COLUMN_USER_NAME TEXT," +
                    "$COLUMN_USER_EMAIL TEXT," +
                    "$COLUMN_USER_PASSWORD TEXT" + ")")

    // SQL statement to drop the user table
    private val DROP_USER_TABLE = "DROP TABLE IF EXISTS $TABLE_USER"

    // Hashing Password
    fun hashPassword(password: String): String {
        val bcryptHashString = BCrypt.withDefaults().hashToString(12, password.toCharArray())
        return bcryptHashString
    }

    fun verifyPassword(password: String, hash: String): Boolean {
        val result = BCrypt.verifyer().verify(password.toCharArray(), hash)
        return result.verified
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_USER_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_USER_TABLE)
        onCreate(db)
    }

    fun addUser(user: User) {
        val db = this.writableDatabase
        val hashPassword = hashPassword(user.password)
        val values = ContentValues()
        values.put(COLUMN_USER_NAME, user.username)
        values.put(COLUMN_USER_EMAIL, user.email)
        values.put(COLUMN_USER_PASSWORD, hashPassword)

        db.insert(TABLE_USER, null, values)
        db.close()
    }

    fun validateUser(username: String, password: String): Boolean {
        val columns = arrayOf(COLUMN_USER_PASSWORD) // Ensure you're querying the password column
        val db = this.readableDatabase
        val selection = "$COLUMN_USER_NAME = ?"
        val selectionArgs = arrayOf(username)

        db.query(TABLE_USER, columns, selection, selectionArgs, null, null, null).use { cursor ->
            if (cursor != null && cursor.moveToFirst()) {
                val passwordIndex = cursor.getColumnIndex(COLUMN_USER_PASSWORD)
                if (passwordIndex != -1) { // Check if the column index is valid
                    val hashedPassword = cursor.getString(passwordIndex)
                    db.close() // Close database before returning
                    return verifyPassword(password, hashedPassword)
                }
            }
        }

        db.close() // Make sure to close the database if cursor condition fails
        return false
    }

}