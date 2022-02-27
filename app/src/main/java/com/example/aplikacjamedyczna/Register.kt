package com.example.aplikacjamedyczna

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacjamedyczna.User.User

class Register : AppCompatActivity() {
    private val activity = this@Register
    private lateinit var toLoginLabel: TextView
    private lateinit var nameRegistraterForm: EditText
    private lateinit var surnameRegistraterForm: EditText
    private lateinit var mailRegistraterForm: EditText
    private lateinit var phoneRegistraterForm: EditText
    private lateinit var passwordRegistraterForm: EditText
    private lateinit var repasswordRegistraterForm: EditText
    private lateinit var registerButton: Button
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var validation: Validation
    private var errors=0
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.registration)
        supportActionBar?.hide()

        nameRegistraterForm = findViewById(R.id.nameRegisterForm)
        surnameRegistraterForm = findViewById(R.id.surnameRegisterForm)
        mailRegistraterForm = findViewById(R.id.mailRegisterForm)
        phoneRegistraterForm =findViewById(R.id.phoneRegisterForm)
        passwordRegistraterForm = findViewById(R.id.passwordRegisterForm)
        repasswordRegistraterForm = findViewById(R.id.repasswordRegisterForm)
        registerButton = findViewById(R.id.registerButton)
        toLoginLabel = findViewById(R.id.toLoginLabel)
        databaseHelper = DatabaseHelper(activity)
        validation = Validation(activity)

        toLoginLabel.setOnClickListener {
            finish()
        }
        registerButton.setOnClickListener{
            postDataToSQLite()
        }

    }
    private fun postDataToSQLite() {
        errors=0
        if (!validation.nameValidation(nameRegistraterForm.text.toString().trim())) {
            nameRegistraterForm.setError("To pole nie może być puste")
            errors++
        }
        if (!validation.surnameValidation(surnameRegistraterForm.text.toString().trim())) {
            surnameRegistraterForm.setError("To pole nie może być puste")
            errors++
        }
        if (!validation.emailValidation(mailRegistraterForm.text.toString().trim())) {
            mailRegistraterForm.setError("To pole nie może być puste")
            errors++
        }
        if (!validation.phoneValidation(phoneRegistraterForm.text.toString().trim())) {
            phoneRegistraterForm.setError("To pole nie może być puste")
            errors++
        }
        if (!validation.passwordValidation(passwordRegistraterForm.text.toString().trim())) {
            passwordRegistraterForm.setError("To pole nie może być puste")
            errors++
        }
        if (!validation.repasswordValidation(repasswordRegistraterForm.text.toString().trim())) {
            repasswordRegistraterForm.setError("To pole nie może być puste")
            errors++
        }
        if (!validation.matchPasswordValidation(
                passwordRegistraterForm.text.toString().trim(),
                repasswordRegistraterForm.text.toString().trim()
            )
        ) {
            repasswordRegistraterForm.setError("Hasła nie są identyczne")
            errors++
        }
        if (errors == 0) {
            val user = User(
                name = nameRegistraterForm.text.toString().trim(),
                surname = surnameRegistraterForm.text.toString().trim(),
                email = mailRegistraterForm.text.toString().trim(),
                phone = phoneRegistraterForm.text.toString().trim(),
                password = passwordRegistraterForm.text.toString().trim()
            )
            databaseHelper.addUser(user)
            finish()
        }
    //else błąd nie udało się zarejestrować
    }
}