package com.example.aplikacjamedyczna

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.aplikacjamedyczna.registration.Register
import com.example.aplikacjamedyczna.registration.Validation
import com.example.aplikacjamedyczna.user.UserMainPage
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private val activity = this@MainActivity
    private lateinit var toRegisterText: TextView
    private lateinit var emailLoginForm: TextView
    private lateinit var passwordLoginForm: TextView
    private lateinit var loginButton: Button
    private lateinit var validation: Validation
    private var errors=0
    var emptyError=""
    private val fbAuth = FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        isCurrentUser()
    }

    private fun isCurrentUser() {
        fbAuth.currentUser?.let { auth->
            val intent = Intent(applicationContext, UserMainPage::class.java).apply {
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emptyError = getString(R.string.emptyError)
        emailLoginForm = findViewById(R.id.emailLoginForm)
        passwordLoginForm = findViewById(R.id.passwordLoginForm)
        toRegisterText = findViewById(R.id.toRegisterLabel)
        loginButton = findViewById(R.id.loginButton)
        validation = Validation(activity)
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
            fbAuth.signInWithEmailAndPassword(emailLoginForm.text.toString().trim(),passwordLoginForm.text.toString().trim())
                .addOnSuccessListener {authRes->
                    if(authRes.user!=null) {
                        val intent = Intent(applicationContext, UserMainPage::class.java).apply {
                            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {exc->
                    Toast.makeText(this,exc.message.toString(),Toast.LENGTH_LONG).show()
                }

        }
    }
}