package com.biztex.manage.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biztex.manage.MainActivity
import com.biztex.manage.R
import com.biztex.manage.utils.ValidationForm
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class LoginAuthFirebase: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
    }

    fun signIn(view: View){
        val passwordLayout = findViewById<TextInputLayout>(R.id.edit_password);
        val emailLayout = findViewById<TextInputLayout>(R.id.edit_email);

        val passwordInputValue = passwordLayout.editText?.text.toString().trim()
        val emailInputValue = emailLayout.editText?.text.toString().trim()

        if(!ValidationForm.isValidEmail(emailInputValue)){
            Toast.makeText(this, "Email format is invalid.", Toast.LENGTH_SHORT).show()
            return
        }
        if(passwordInputValue.isEmpty()){
            Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show()
            return
        }

        signInFunc(emailInputValue, passwordInputValue)
    }


    private fun signInFunc(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Toast.makeText(baseContext, "Authentication successful.", Toast.LENGTH_SHORT).show()
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

}