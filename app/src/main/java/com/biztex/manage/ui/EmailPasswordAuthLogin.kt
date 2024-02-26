package com.biztex.manage.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biztex.manage.MainActivity
import com.biztex.manage.R
import com.biztex.manage.utils.ValidationForm
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers.Main

class EmailPasswordAuthLogin: AppCompatActivity(){

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
    }

    fun signIn(view: View) {
        val passwordLayout = findViewById<TextInputLayout>(R.id.edit_password)
        val emailLayout = findViewById<TextInputLayout>(R.id.edit_email)

        val passwordInput = passwordLayout.editText?.text.toString().trim()
        val emailInput = emailLayout.editText?.text.toString().trim()
        Log.d(TAG, "Result:$emailInput")

        if(!ValidationForm.isValidEmail(emailInput)){
            Toast.makeText(this, "Email format is invalid.", Toast.LENGTH_SHORT).show()
            return
        }
        if(passwordInput.isEmpty()){
            Toast.makeText(this, "Please enter password.", Toast.LENGTH_SHORT).show()
            return
        }
        signInFunc(emailInput, passwordInput)
    }

    private fun signInFunc(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Toast.makeText(baseContext, "Authentication successful.", Toast.LENGTH_SHORT).show()
                    //Navigate to the next activity
                    val mainIntent =  Intent(this, MainActivity::class.java);
                    startActivity(mainIntent)
                    finish()
                }else{
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
    }

    companion object {
        private const val TAG = "authentication_result"
    }

}
