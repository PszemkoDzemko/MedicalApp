package com.example.aplikacjamedyczna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.example.aplikacjamedyczna.User.UserMainPage

class MainActivity : AppCompatActivity() {
    private val activity = this@MainActivity
    private lateinit var toRegisterText: TextView
    private lateinit var emailLoginForm: TextView
    private lateinit var passwordLoginForm: TextView
    private lateinit var loginButton: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var validation: Validation
    private var errors=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

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
            passwordLoginForm.setError("Błędny login lub hasło")
            errors++
        }
        if (errors == 0) {
                val intentMainPage = Intent(applicationContext, UserMainPage::class.java)
                startActivity(intentMainPage)
                finish()
            }
        //else błąd nie udało się zalogować
    }
}