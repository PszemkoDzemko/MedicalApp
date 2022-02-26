package com.example.aplikacjamedyczna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.android.material.textfield.TextInputLayout

class MainActivity : AppCompatActivity() {
    private lateinit var toRegisterText: TextView
    private lateinit var emailLoginForm: TextView
    private lateinit var passwordLoginForm: TextView
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        emailLoginForm = findViewById(R.id.emailLoginForm)
        passwordLoginForm = findViewById(R.id.passwordLoginForm)
        toRegisterText = findViewById(R.id.toRegisterLabel)
        loginButton = findViewById(R.id.loginButton)

        toRegisterText.setOnClickListener{
            val intentRegister = Intent(applicationContext, Register::class.java)
            startActivity(intentRegister)
        }
        loginButton.setOnClickListener{
            val intentMainPage = Intent(applicationContext, MainPage::class.java)
            startActivity(intentMainPage)
        }
    }

}