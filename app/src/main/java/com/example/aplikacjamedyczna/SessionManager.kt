package com.example.aplikacjamedyczna

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.provider.ContactsContract

class SessionManager(var context: Context) {
    var pref: SharedPreferences
    var editor: SharedPreferences.Editor
    var PRIVATE_MODE: Int = 0

    init {
        pref = context.getSharedPreferences(PREF_NAME,PRIVATE_MODE)
        editor = pref.edit()
    }

    companion object{
        val PREF_NAME: String = "MedicalApp"
        val IS_LOGIN: String = "isLoggedIn"
        val KEY_EMAIL: String = "email"

    }

    fun createLoginSession(email: String){
        editor.putBoolean(IS_LOGIN,true)
        editor.putString(KEY_EMAIL,email)
        editor.commit()
    }

    fun checkLogin(){
        if(!this.isLoggedIn()){
            val intent = Intent (context,MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    fun getUserDetails():HashMap<String,String>{
        val user: Map<String,String> = HashMap<String,String>()
        (user as HashMap).put(KEY_EMAIL, pref.getString(KEY_EMAIL,null).toString())
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
}