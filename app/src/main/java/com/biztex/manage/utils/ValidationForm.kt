package com.biztex.manage.utils

import android.text.Editable
import android.text.TextWatcher
import android.view.View

class ValidationForm {
    companion object{
        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String):Boolean {
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z]).{8,}$".toRegex()
            return passwordPattern.matches(password)
        }




    }
}