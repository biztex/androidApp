package com.biztex.manage.model

data class User(
    val id: Int? = null, // Optional: used for database operations, can be null when creating a new user
    val username: String,
    val email: String,
    val password: String
)