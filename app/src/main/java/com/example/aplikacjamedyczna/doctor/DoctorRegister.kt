package com.example.aplikacjamedyczna.doctor

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacjamedyczna.DatabaseHelper
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.Validation

class DoctorRegister : AppCompatActivity() {
    private val activity = this@DoctorRegister
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var validation: Validation
    private lateinit var backToRegister:TextView
    private lateinit var doctorNameRegister: EditText
    private lateinit var doctorSurnameRegister: EditText
    private lateinit var doctorSpecializationRegister: EditText
    private lateinit var doctorEmailRegister: EditText
    private lateinit var doctorPasswordRegister: EditText
    private lateinit var doctorRepasswordRegister: EditText
    private lateinit var doctorRegisterButton: Button
    private var errors=0
    private var emptyError=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doctor_register)
        supportActionBar?.hide()

        databaseHelper = DatabaseHelper(activity)
        validation = Validation(activity)

        emptyError = getString(R.string.emptyError)
        doctorNameRegister = findViewById(R.id.doctorNameRegisterForm)
        doctorSurnameRegister = findViewById(R.id.doctorSurnameRegisterForm)
        doctorSpecializationRegister = findViewById(R.id.doctorSpecializationRegisterForm)
        doctorEmailRegister = findViewById(R.id.doctorMailRegisterForm)
        doctorPasswordRegister = findViewById(R.id.doctorPasswordRegisterForm)
        doctorRepasswordRegister = findViewById(R.id.doctopRepasswordRegisterForm)
        doctorRegisterButton = findViewById(R.id.doctorRegisterButton)

        backToRegister = findViewById(R.id.backToRegister)

        backToRegister.setOnClickListener { finish() }

        doctorRegisterButton.setOnClickListener { postDataToSQL() }

    }

    private fun postDataToSQL() {
        errors=0
        if(!validation.nameValidation(doctorNameRegister.text.toString().trim())){
            doctorNameRegister.error = emptyError
            errors++
        }
        if(!validation.surnameValidation(doctorSurnameRegister.text.toString().trim())){
            doctorSurnameRegister.error = emptyError
            errors++
        }
        if(!validation.emailValidation(doctorEmailRegister.text.toString().trim())){
            doctorEmailRegister.error = emptyError
            errors++
        }
        if(!validation.phoneValidation(doctorSpecializationRegister.text.toString().trim())){
            doctorSpecializationRegister.error = emptyError
            errors++
        }
        if(!validation.passwordValidation(doctorPasswordRegister.text.toString().trim())){
            doctorPasswordRegister.error = emptyError
            errors++
        }
        if(!validation.repasswordValidation(doctorRepasswordRegister.text.toString().trim())){
            doctorRepasswordRegister.error = emptyError
            errors++
        }
        if(!validation.matchPasswordValidation(doctorPasswordRegister.text.toString().trim(),doctorRepasswordRegister.text.toString().trim())){
            doctorRepasswordRegister.error = getString(R.string.notSamePasswordError)
            errors++
        }
        if(errors==0){
            val doctor = Doctor(
            name = doctorNameRegister.text.toString().trim(),
            surname = doctorSurnameRegister.text.toString().trim(),
            email = doctorEmailRegister.text.toString().trim(),
            specialization = doctorSpecializationRegister.text.toString().trim(),
            password = doctorPasswordRegister.text.toString().trim()
            )
            databaseHelper.addDoctor(doctor)
            finish()
        }
    }

}