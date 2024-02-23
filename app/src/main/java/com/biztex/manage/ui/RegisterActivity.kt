package com.biztex.manage.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biztex.manage.R
import com.biztex.manage.model.User
import com.biztex.manage.utils.DatabasesHelper

class RegisterActivity : AppCompatActivity() {
    private lateinit var db: DatabasesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
//
//        db = DatabasesHelper(this)
//
//        val etUsername = findViewById<EditText>(R.id.etRegisterUsername)
//        val etEmail = findViewById<EditText>(R.id.etRegisterEmail)
//        val etPassword = findViewById<EditText>(R.id.etRegisterPassword)
//        val etPasswordConfirm = findViewById<EditText>(R.id.etRegisterPasswordConfirm)
//        val btnRegister = findViewById<Button>(R.id.button_register)
//
//        btnRegister.setOnClickListener {
//            val user = User(
//                username = etUsername.text.toString().trim(),
//                email = etEmail.text.toString().trim(),
//                password = etPassword.text.toString().trim()
//            )
//
//            if(validateInput(user)){
//                db.addUser(user)
//                Toast.makeText(this, "User Register successfully", Toast.LENGTH_SHORT).show()
//                finish()
//            }
//            else{
//                Toast.makeText(this, "Registration error. Please check your details", Toast.LENGTH_LONG).show()
//            }
//        }


    }


    private fun validateInput(user: User): Boolean {
        if (user.username.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}