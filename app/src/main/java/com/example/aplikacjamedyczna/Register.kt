package com.example.aplikacjamedyczna

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacjamedyczna.doctor.DoctorRegister
import com.example.aplikacjamedyczna.user.User

class Register : AppCompatActivity() {
    private val activity = this@Register
    private lateinit var toLoginLabel: TextView
    private lateinit var toDoctorRegister: TextView
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

        nameRegistraterForm = findViewById(R.id.doctorNameRegisterForm)
        surnameRegistraterForm = findViewById(R.id.doctorSurnameRegisterForm)
        mailRegistraterForm = findViewById(R.id.doctorMailRegisterForm)
        phoneRegistraterForm = findViewById(R.id.doctorSpecializationRegisterForm)
        passwordRegistraterForm = findViewById(R.id.doctorPasswordRegisterForm)
        repasswordRegistraterForm = findViewById(R.id.doctopRepasswordRegisterForm)
        registerButton = findViewById(R.id.doctorRegisterButton)
        toLoginLabel = findViewById(R.id.backToRegister)
        databaseHelper = DatabaseHelper(activity)
        validation = Validation(activity)
        toDoctorRegister= findViewById(R.id.toDoctorRegister)

        toDoctorRegister.setOnClickListener {
            val intent = Intent(applicationContext, DoctorRegister::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }
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