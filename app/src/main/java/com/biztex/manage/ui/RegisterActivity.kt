package com.biztex.manage.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biztex.manage.R
import com.biztex.manage.model.User
import com.biztex.manage.utils.DatabasesHelper
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity() {
    private lateinit var db: DatabasesHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun signUp(view: View){
        db = DatabasesHelper(this)
        val etUsernameLayout = findViewById<TextInputLayout>(R.id.edit_register_username);
        val etEmailLayout = findViewById<TextInputLayout>(R.id.edit_register_email);
        val etPasswordLayout = findViewById<TextInputLayout>(R.id.edit_register_password);
        val etPasswordConfirmLayout = findViewById<TextInputLayout>(R.id.edit_register_password_confirm);

        val etUsername = etUsernameLayout.editText?.text
        val etEmail = etEmailLayout.editText?.text
        val etPassword = etPasswordLayout.editText?.text
        val etPasswordConfirm = etPasswordConfirmLayout.editText?.text


        val user = User(
            username = etUsername.toString().trim(),
            email = etEmail.toString().trim(),
            password = etPassword.toString().trim()
        )

        if(validateInput(user)){
            db.addUser(user)
            Toast.makeText(this, "User Register successfully", Toast.LENGTH_SHORT).show()
            finish()
        }
        else{
            Toast.makeText(this, "Registration error. Please check your details", Toast.LENGTH_LONG).show()
        }
    }

    private fun validateInput(user: User): Boolean {
        if (user.username.isEmpty() || user.email.isEmpty() || user.password.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}