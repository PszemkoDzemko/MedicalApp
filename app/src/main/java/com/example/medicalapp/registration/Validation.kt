package com.example.medicalapp.registration

import android.content.Context

class Validation(private val context: Context) {
    fun nameValidation(name:String):Boolean{
        if(name.isEmpty()){
            return false
        }
        return true
    }
    fun surnameValidation(surname:String):Boolean{
        if(surname.isEmpty()){
            return false
        }
        return true
    }
    fun emailValidation(email:String):Boolean{
        if(email.isEmpty()){
            return false
        }
        return true
    }
    fun phoneValidation(phone:String):Boolean{
        if(phone.isEmpty()){
            return false
        }
        return true
    }
    fun passwordValidation(password:String):Boolean {
        if(password.isEmpty()){
            return false
        }
        return true
    }
    fun repasswordValidation(repassword:String):Boolean{
        if(repassword.isEmpty()){
            return false
        }
        return true
    }
    fun matchPasswordValidation(password: String, repassword: String):Boolean {
        if(repassword!=password){
            return false
        }
        return true
    }
}