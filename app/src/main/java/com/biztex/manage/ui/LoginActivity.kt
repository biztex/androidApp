package com.biztex.manage.ui

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat.startActivity
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
    }
    fun signIn(view: View){
        val db = DatabasesHelper(this)
        val etUsernameLayout = findViewById<TextInputLayout>(R.id.edit_username)
        val etPasswordLayout = findViewById<TextInputLayout>(R.id.edit_password)

        val username = etUsernameLayout.editText?.text.toString().trim()
        val password = etPasswordLayout.editText?.text.toString().trim()

        if (db.validateUser(username, password)) {
            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent);
            finish()
        } else {
            // Login failed, show error message
            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
        }
    }

    fun signUpLayout(view: View) {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        startActivity(registerIntent)
        startActivity(intent)
    }
}