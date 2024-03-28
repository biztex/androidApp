package com.biztex.manage.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.biztex.manage.MainActivity
import com.biztex.manage.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import androidx.databinding.DataBindingUtil
import com.biztex.manage.databinding.ActivityLoginBinding

class LoginAuthFirebase: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        setupListeners()

        binding.loginBtn.setOnClickListener{
            val email = binding.editEmailInput.text.toString().trim()
            val password = binding.editPasswordInput.text.toString().trim()
            if(email.isEmpty()){
                binding.editEmail.error = "Please enter email."
                binding.editEmail.requestFocus()
                return@setOnClickListener

            }
            if(password.isEmpty()){
                binding.editPassword.error = "Please enter password."
                binding.editPassword.requestFocus()
                return@setOnClickListener

            }
            if(email.isNotBlank() && password.isNotBlank()){
                signInFunc(email, password)
            }
        }

    }

    private fun setupListeners() {
        binding.editEmailInput.addTextChangedListener(TextFieldValidation(binding.editEmailInput))
        binding.editPasswordInput.addTextChangedListener(TextFieldValidation(binding.editPasswordInput))
    }

    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (view.id){
                R.id.edit_email_input -> {
                    binding.editEmail.error = null
                }
                R.id.edit_password_input -> {
                    binding.editPassword.error = null

                }
            }
        }
    }

    private fun signInFunc(email:String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    val user = auth.currentUser
                    auth.addAuthStateListener {
                        it.currentUser?.let { firebaseUser ->
                            if(firebaseUser.isEmailVerified){
                                Toast.makeText(baseContext, "Authentication successful.", Toast.LENGTH_SHORT).show()
                                val mainIntent = Intent(this, MainActivity::class.java)
                                startActivity(mainIntent)
                                finish()
                            }
                            else{
                                firebaseUser.sendEmailVerification()
                                Toast.makeText(baseContext, "Email resent", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }else {
                    Toast.makeText(baseContext, "Authentication failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener {
            it.currentUser?.let { firebaseUser ->
                if(firebaseUser.isEmailVerified){
                    val mainIntent = Intent(this, MainActivity::class.java)
                    startActivity(mainIntent)
                    finish()
                }
            }
        }
    }

}