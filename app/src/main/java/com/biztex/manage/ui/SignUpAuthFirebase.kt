package com.biztex.manage.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.biztex.manage.R
import com.biztex.manage.databinding.ActivityRegisterBinding
import com.biztex.manage.utils.ValidationForm
import com.google.firebase.auth.FirebaseAuth

class SignUpAuthFirebase: AppCompatActivity(){
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        auth = FirebaseAuth.getInstance()
        setupListeners()

        binding.buttonRegister.setOnClickListener {
            val userName: String = binding.etRegisterUsername.text.toString().trim()
            val email: String = binding.etRegisterEmail.text.toString().trim()
            val password: String = binding.etRegisterPassword.text.toString().trim()
            val passwordConfirm: String = binding.etRegisterPasswordConfirm.text.toString().trim()
            if (isValidate(userName, email, password, passwordConfirm)){
                signUpFunc(email, password)
            }
        }

    }

    private fun setupListeners() {
        binding.etRegisterUsername.addTextChangedListener(TextFieldValidation(binding.etRegisterUsername))
        binding.etRegisterEmail.addTextChangedListener(TextFieldValidation(binding.etRegisterEmail))
        binding.etRegisterPassword.addTextChangedListener(TextFieldValidation(binding.etRegisterPassword))
        binding.etRegisterPasswordConfirm.addTextChangedListener(TextFieldValidation(binding.etRegisterPasswordConfirm))
    }

    private fun isValidate(userName: String, email: String, password: String, passwordConfirm: String): Boolean =
        validateUserName(userName) && validateEmail(email) && validatePassword(password) && validatePasswordConfirm(password, passwordConfirm)

    private fun validateUserName(userName: String): Boolean {
        if (userName.isEmpty()) {
            binding.editRegisterUsername.error = "Please enter username."
            binding.etRegisterUsername.requestFocus()
            return false
        } else {
            binding.editRegisterUsername.error = null
        }
        return true
    }

    private fun validateEmail(email: String): Boolean {
        if(email.isEmpty()){
            binding.editRegisterEmail.error = "Please enter email."
            binding.editRegisterEmail.requestFocus()

            return false
        }else if(!ValidationForm.isValidEmail(email)){
            binding.editRegisterEmail.error = "The email format does not match."
            binding.editRegisterEmail.requestFocus()
            return false
        }else{
            binding.editRegisterEmail.error = null
        }
        return true
    }


    private fun validatePassword(password: String): Boolean{
        if(password.isEmpty()){
            binding.editRegisterPassword.error = "Please enter password."
            binding.editRegisterPassword.requestFocus()
            return false
        }else if(!ValidationForm.isValidPassword(password)){
            binding.editRegisterPassword.error = "The password format does not match."
            binding.editRegisterPassword.requestFocus()
            return false
        }else{
            binding.editRegisterPassword.error = null
        }
        return true
    }

    private fun validatePasswordConfirm(password: String, passwordConfirm: String): Boolean {
        if(passwordConfirm.isEmpty()){
            binding.editRegisterPasswordConfirm.error = "Please enter the confirm password."
            binding.editRegisterPasswordConfirm.requestFocus()
            return false
        }else if(passwordConfirm != password){
            binding.editRegisterPasswordConfirm.error = "The password does not match."
            binding.editRegisterPasswordConfirm.requestFocus()
            return false
        }else{
            binding.editRegisterPasswordConfirm.error = null
        }
        return true
    }





    /**
     * applying text watcher on each text field
     */
    inner class TextFieldValidation(private val view: View) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val userName: String = binding.etRegisterUsername.text.toString().trim()
            val passwordConfirm: String = binding.etRegisterPasswordConfirm.text.toString().trim()
            val email: String = binding.etRegisterEmail.text.toString().trim()
            val password: String = binding.etRegisterPassword.text.toString().trim()
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.etRegisterUsername -> {
                    validateUserName(userName)
                }
                R.id.etRegisterEmail -> {
                    validateEmail(email)
                }
                R.id.etRegisterPassword -> {
                    validatePassword(password)
                }
                R.id.etRegisterPasswordConfirm -> {
                    validatePasswordConfirm(password, passwordConfirm)
                }
            }
        }
    }


    private fun signUpFunc(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "createUserWithEmail:success")
                    auth.addAuthStateListener {
                        it.currentUser?.let { firebase ->
                            firebase.sendEmailVerification()
                                .addOnCompleteListener{ task ->
                                    if(task.isSuccessful){
                                        if (task.isSuccessful) {
                                            Log.d(TAG, "Email sent.")
                                            Toast.makeText(baseContext, "Verification email sent.", Toast.LENGTH_SHORT).show()
                                            val loginIntent = Intent(this, LoginAuthFirebase::class.java)
                                            startActivity(loginIntent)
                                            finish()
                                        } else {
                                            Log.w(TAG, "Failed to send verification email.\n" +
                                                    "${task.exception}")
                                            Toast.makeText(baseContext, "Failed to send verification email.\n${task.exception}", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                        }
                    }
                }else{
                    Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object{
        private const val TAG = "Authentication_result";
    }
}