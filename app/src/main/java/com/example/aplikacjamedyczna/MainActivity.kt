package com.example.aplikacjamedyczna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.aplikacjamedyczna.user.UserMainPage

class MainActivity : AppCompatActivity() {
    private val activity = this@MainActivity
    private lateinit var toRegisterText: TextView
    private lateinit var emailLoginForm: TextView
    private lateinit var passwordLoginForm: TextView
    private lateinit var loginButton: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var validation: Validation
    private var errors=0
    lateinit var sessionManager: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        sessionManager = SessionManager(applicationContext)
        if(sessionManager.isLoggedIn()){
            val intent = Intent(applicationContext,UserMainPage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        emailLoginForm = findViewById(R.id.emailLoginForm)
        passwordLoginForm = findViewById(R.id.passwordLoginForm)
        toRegisterText = findViewById(R.id.toRegisterLabel)
        loginButton = findViewById(R.id.loginButton)
        validation = Validation(activity)
        databaseHelper = DatabaseHelper(activity)
        toRegisterText.setOnClickListener{
            val intentRegister = Intent(applicationContext, Register::class.java)
            startActivity(intentRegister)
        }
        loginButton.setOnClickListener{
            login()

        }
    }
    fun login() {
        errors=0
        if (!validation.emailValidation(emailLoginForm.text.toString().trim())) {
            emailLoginForm.setError("To pole nie może być puste")
            errors++
        }
        if (!validation.passwordValidation(passwordLoginForm.text.toString().trim())) {
            passwordLoginForm.setError("To pole nie może być puste")
            errors++
        }
        if (!databaseHelper.checkUser(emailLoginForm.text.toString().trim(), passwordLoginForm.text.toString().trim())){
            Toast.makeText(this@MainActivity, "Nazwa używtkownika lub hasło nieprawidłowe",Toast.LENGTH_LONG).show()
            errors++
        }
        if (errors == 0) {
            sessionManager.createLoginSession(emailLoginForm.text.toString().trim())
            val intentMainPage = Intent(applicationContext, UserMainPage::class.java)
            startActivity(intentMainPage)
            finish()
        }
        //else błąd nie udało się zalogować
    }
}