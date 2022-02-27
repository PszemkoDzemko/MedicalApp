package com.example.aplikacjamedyczna

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.ContactsContract

class SessionManager(var context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    private var PRIVATE_MODE = 0

    init {
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object{
        const val PREF_NAME: String = "MedicalApp"
        const val IS_LOGIN: String = "isLoggedIn"
        const val KEY_EMAIL: String = "email"
        const val IS_DOCTOR: String = "doctor"
    }

    fun createUserSession(email: String){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString(KEY_EMAIL,email)
        editor.commit()
    }
    fun createDoctorSession(email: String){
        editor.putBoolean(IS_DOCTOR,true)
        editor.putString(KEY_EMAIL,email)
        editor.commit()
    }

    fun checkUserLogin(){
        if(!this.isLoggedIn()){
            val intent = Intent (context,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }
    fun checkDoctorLogin(){
        if(!this.isDoctorLoggedIn()){
            val intent = Intent (context,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    fun getUserDetails():HashMap<String,String>{
        val user: Map<String,String> = HashMap()
        (user as HashMap)[KEY_EMAIL] = pref.getString(KEY_EMAIL,null).toString()
        return user
    }
    fun logoutUser(){
        editor.clear()
        editor.commit()

        val intent = Intent (context,MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
    fun isLoggedIn():Boolean{
        return pref.getBoolean(IS_LOGIN,false)
    }
    fun isDoctorLoggedIn():Boolean{
        return pref.getBoolean(IS_DOCTOR,false)
    }

}