package com.biztex.manage.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.biztex.manage.MainActivity
import com.biztex.manage.MyApp
import com.biztex.manage.R
import com.biztex.manage.utils.DatabasesHelper
import com.google.android.material.textfield.TextInputLayout
import java.util.function.ToLongBiFunction

class LoginActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = DatabasesHelper(this)

        val etUsername = findViewById<EditText>(R.id.edit_username_input)
        val etPassword = findViewById<EditText>(R.id.edit_password_input)
        val btnLogin = findViewById<Button>(R.id.login_btn)

        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if(db.validateUser(username, password)){
                Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
            else{
                // Login failed, show error message
                Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
            }
        }
    }

}