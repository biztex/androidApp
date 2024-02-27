package com.biztex.manage.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biztex.manage.R
import com.biztex.manage.utils.ValidationForm
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth

class SignUpAuthFirebase: AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
    }

    fun signUp(view: View){
        val usernameLayout = findViewById<TextInputLayout>(R.id.edit_register_username)
        val emailLayout = findViewById<TextInputLayout>(R.id.edit_register_email)
        val passwordLayout = findViewById<TextInputLayout>(R.id.edit_register_password)
        val passwordConfirmLayout = findViewById<TextInputLayout>(R.id.edit_register_password_confirm)

        val username = usernameLayout.editText?.text.toString().trim()
        val email = emailLayout.editText?.text.toString().trim()
        val password = passwordLayout.editText?.text.toString().trim()
        val passwordConfirm = passwordConfirmLayout.editText?.text.toString().trim()

        if(username.isEmpty()){
            Toast.makeText(this, "Please enter username.", Toast.LENGTH_SHORT).show()
            return
        }
        if(!ValidationForm.isValidEmail(email)){
            Toast.makeText(this, "Email format is invalid.", Toast.LENGTH_SHORT).show()
            return
        }
        if(!ValidationForm.isValidPassword(password)){
            Toast.makeText(this, "Password format is invalid.", Toast.LENGTH_SHORT).show()
            return
        }
        if(password != passwordConfirm){
            Toast.makeText(this, "The password does not match.", Toast.LENGTH_SHORT).show()
            return
        }

        signUpFunc(email, password)

    }



    private fun signUpFunc(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Toast.makeText(baseContext, "Signup successful.", Toast.LENGTH_SHORT).show()
                    val loginIntent = Intent(this, LoginActivity::class.java)
                    startActivity(loginIntent)
                    finish()
                }else{
                    Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }
}