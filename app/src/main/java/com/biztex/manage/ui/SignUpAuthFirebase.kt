package com.biztex.manage.ui

import android.app.Activity
import android.content.Context
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
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.ActionCodeSettings
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
            if (isValidate()){
                val emailLayout = findViewById<TextInputLayout>(R.id.edit_register_email)
                val passwordLayout = findViewById<TextInputLayout>(R.id.edit_register_password)
                val email = emailLayout.editText?.text.toString().trim()
                val password = passwordLayout.editText?.text.toString().trim()
                //signUpFunc(email, password)
            }
        }

    }

    private fun setupListeners() {
        binding.etRegisterUsername.addTextChangedListener(TextFieldValidation(binding.etRegisterUsername))
    }

    private fun isValidate(): Boolean = validateUserName()

    private fun validateUserName(): Boolean {
        if (binding.etRegisterUsername.text.toString().trim().isEmpty()) {
            binding.editRegisterUsername.error = "Please enter username."
            binding.etRegisterUsername.requestFocus()
            return false
        } else {
            binding.editRegisterUsername.error = null
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
            // checking ids of each text field and applying functions accordingly.
            when (view.id) {
                R.id.etRegisterUsername -> {
                    validateUserName()
                }
            }
        }
    }

    fun signUp(view: View){
//
//        if(username.isEmpty()){
//            usernameLayout.error = "Please enter username."
//            Toast.makeText(this, "Please enter username.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(!ValidationForm.isValidEmail(email)){
//            emailLayout.error = "Email format is invalid."
//            Toast.makeText(this, "Email format is invalid.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(!ValidationForm.isValidPassword(password)){
//            passwordLayout.error = "Please enter at least 8 alphanumeric characters.";
//            Toast.makeText(this, "Password format is invalid.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(password != passwordConfirm){
//            passwordConfirmLayout.error = "The password does not match.";
//            Toast.makeText(this, "The password does not match.", Toast.LENGTH_SHORT).show()
//            return
//        }

    }



    private fun signUpFunc(email: String, password: String){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful){
                    Log.d(TAG, "createUserWithEmail:success")
                    sendVerificationEmail()
                }else{
                    Toast.makeText(baseContext, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sendVerificationEmail() {
        val user = auth.currentUser
        user?.let {
            val actionCodeSettings = ActionCodeSettings.newBuilder()
                .setUrl("https://biztex.page.link/mVFa?login=true")
                .setAndroidPackageName(
                    "com.biztex.manage",
                    true,
                    null
                )
                .setHandleCodeInApp(true)
                .build()
            it.sendEmailVerification(actionCodeSettings)
                .addOnCompleteListener{ task ->
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


    companion object{
        private const val TAG = "Authentication_result";
    }
}