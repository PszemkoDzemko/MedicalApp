package com.example.aplikacjamedyczna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.aplikacjamedyczna.doctor.DoctorMainPage
import com.example.aplikacjamedyczna.user.Register
import com.example.aplikacjamedyczna.user.UserMainPage
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private val activity = this@MainActivity
    private lateinit var toRegisterText: TextView
    private lateinit var emailLoginForm: TextView
    private lateinit var passwordLoginForm: TextView
    private lateinit var loginButton: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var validation: Validation
    private var errors=0
    var emptyError=""
    private lateinit var sessionManager: SessionManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        sessionManager = SessionManager(applicationContext)
        if(sessionManager.isDoctorLoggedIn()){
            val intent = Intent(applicationContext,DoctorMainPage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }//kurwa ale jestem zajebisty że to zrobiłem z 30 min się męczyłem nad tym bo else nie działa tak jak powinien xD
        if(sessionManager.isLoggedIn()){
            val intent = Intent(applicationContext,UserMainPage::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
        emptyError = getString(R.string.emptyError)
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
    private fun login() {
        errors=0
        if (!validation.emailValidation(emailLoginForm.text.toString().trim())) {
            emailLoginForm.error = emptyError
            errors++
        }
        if (!validation.passwordValidation(passwordLoginForm.text.toString().trim())) {
            passwordLoginForm.error = emptyError
            errors++
        }
        if (errors == 0) {
            if (databaseHelper.checkDoctor(emailLoginForm.text.toString().trim(), passwordLoginForm.text.toString().trim())) {
                sessionManager.createDoctorSession(emailLoginForm.text.toString().trim())
                val intent = Intent(applicationContext, DoctorMainPage::class.java)
                startActivity(intent)
                finish()
            }

            if (databaseHelper.checkUser(emailLoginForm.text.toString().trim(), passwordLoginForm.text.toString().trim())) {
                sessionManager.createUserSession(emailLoginForm.text.toString().trim())
                val intent = Intent(applicationContext, UserMainPage::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this@MainActivity, "Nazwa używtkownika lub hasło nieprawidłowe", Toast.LENGTH_LONG).show()
            //to wywala bład po zalogowaniu się jako doktor, ale czemu nie wiem
            }
        }
    }
}