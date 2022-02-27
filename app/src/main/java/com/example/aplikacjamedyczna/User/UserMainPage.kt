package com.example.aplikacjamedyczna.User

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.SessionManager

class UserMainPage : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var welcomeText:TextView
    lateinit var wyloguj:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mainpage)
        supportActionBar?.hide()

        sessionManager = SessionManager(applicationContext)

        welcomeText = findViewById(R.id.testText)

        sessionManager.checkLogin()

        val user: HashMap<String,String> =sessionManager.getUserDetails()
        val name: String = user.get(SessionManager.KEY_EMAIL)!!


        welcomeText.text = getString(R.string.welcomeMessage,name)
        wyloguj = findViewById(R.id.logoutButton)

        wyloguj.setOnClickListener{
            sessionManager.logoutUser()
        }

    }
}