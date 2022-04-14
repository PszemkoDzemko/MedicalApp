package com.example.medicalapp.registration

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.medicalapp.R
import com.example.medicalapp.user.UserMainPage
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {
    private val fbAuth = FirebaseAuth.getInstance()
    private lateinit var toLoginLabel: TextView
    private lateinit var mailRegistraterForm: EditText
    private lateinit var passwordRegistraterForm: EditText
    private lateinit var repasswordRegistraterForm: EditText
    private lateinit var registerButton: Button
    private lateinit var validation: Validation
    private var errors=0
    private var emptyError=""
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        emptyError = getString(R.string.emptyError)
        mailRegistraterForm = findViewById(R.id.userMailRegisterForm)
        passwordRegistraterForm = findViewById(R.id.userCityRegisterForm)
        repasswordRegistraterForm = findViewById(R.id.userAddressRegisterForm)
        registerButton = findViewById(R.id.userDataSaveButton)
        toLoginLabel = findViewById(R.id.backToRegister)
        validation = Validation(applicationContext)
        toLoginLabel.setOnClickListener {
            finish()
        }
        registerButton.setOnClickListener{
            register()
        }
    }
    private fun register() {
        errors=0
        if (!validation.emailValidation(mailRegistraterForm.text.toString().trim())) {
            mailRegistraterForm.error = emptyError
            errors++
        }
        if (!validation.passwordValidation(passwordRegistraterForm.text.toString().trim())) {
            passwordRegistraterForm.error = emptyError
            errors++
        }
        if (!validation.repasswordValidation(repasswordRegistraterForm.text.toString().trim())) {
            repasswordRegistraterForm.error = emptyError
            errors++
        }
        if (!validation.matchPasswordValidation(
                passwordRegistraterForm.text.toString().trim(),
                repasswordRegistraterForm.text.toString().trim())) {
            repasswordRegistraterForm.error = getString(R.string.notSamePasswordError)
            errors++
        }
        if (errors == 0) {
            fbAuth.createUserWithEmailAndPassword(mailRegistraterForm.text.toString().trim(),passwordRegistraterForm.text.toString().trim())
                .addOnSuccessListener { authRes ->
                    if(authRes.user != null){
                        authRes.user!!.sendEmailVerification()
                        val intent = Intent(applicationContext, UserMainPage::class.java).apply {
                            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                        startActivity(intent)
                    }
                }
                .addOnFailureListener {exc ->
                    Log.d("reg_debug",exc.message.toString())
                }
        }
    }
}