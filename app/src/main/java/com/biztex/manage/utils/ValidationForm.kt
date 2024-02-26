package com.biztex.manage.utils

class ValidationForm {
    companion object{
        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }

        fun isValidPassword(password: String): Boolean{
            val passwordPattern = "^(?=.*[0-9])(?=.*[a-z]).{8,}$".toRegex()
            return password.matches(passwordPattern)
        }
    }


}