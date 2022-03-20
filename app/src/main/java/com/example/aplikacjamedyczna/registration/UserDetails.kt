package com.example.aplikacjamedyczna.registration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.UsersData
import com.example.aplikacjamedyczna.user.UserMainPage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserDetails : AppCompatActivity() {
    private val activity = this@UserDetails
    private lateinit var validation: Validation
    private lateinit var userNameRegister: EditText
    private lateinit var userSurnameRegister: EditText
    private lateinit var userPeselRegister: EditText
    private lateinit var userPhoneRegister: EditText
    private lateinit var userCityRegister: EditText
    private lateinit var userAddressRegister: EditText
    private lateinit var userSaveButton: Button
    private var errors=0
    private var emptyError=""
    private val fbAuth = FirebaseAuth.getInstance()
    private var database = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_details)

        validation = Validation(activity)
        emptyError = getString(R.string.emptyError)
        userNameRegister = findViewById(R.id.userNameRegisterForm)
        userSurnameRegister = findViewById(R.id.userSurnameRegisterForm)
        userPeselRegister = findViewById(R.id.userPeselRegisterForm)
        userPhoneRegister = findViewById(R.id.userPhoneRegisterForm)
        userCityRegister = findViewById(R.id.userCityRegisterForm)
        userAddressRegister = findViewById(R.id.userAddressRegisterForm)
        userSaveButton = findViewById(R.id.userDataSaveButton)
        userSaveButton.setOnClickListener { addUserData() }

    }

    private fun addUserData() {
        errors=0
        if(!validation.nameValidation(userNameRegister.text.toString().trim())){
            userNameRegister.error = emptyError
            errors++
        }
        if(!validation.surnameValidation(userSurnameRegister.text.toString().trim())){
            userSurnameRegister.error = emptyError
            errors++
        }
        if(!validation.emailValidation(userPeselRegister.text.toString().trim())){
            userPeselRegister.error = emptyError
            errors++
        }
        if(!validation.phoneValidation(userPhoneRegister.text.toString().trim())){
            userPhoneRegister.error = emptyError
            errors++
        }
        if(!validation.passwordValidation(userCityRegister.text.toString().trim())){
            userCityRegister.error = emptyError
            errors++
        }
        if(!validation.repasswordValidation(userAddressRegister.text.toString().trim())){
            userAddressRegister.error = emptyError
            errors++
        }
        if(errors==0){
            val uid = fbAuth.currentUser?.uid.toString()
            val name = userNameRegister.text.toString()
            val surname = userSurnameRegister.text.toString()
            val pesel = userPeselRegister.text.toString()
            val phone = userPhoneRegister.text.toString()
            var address = userCityRegister.text.toString()
            address += " "
            address += userAddressRegister.text.toString()
            val user = UsersData(uid,name,surname,pesel,phone,address)
            database.collection("users").document(uid).set(user).addOnSuccessListener {
                val intent = Intent(applicationContext, UserMainPage::class.java)
                startActivity(intent)
            }.addOnFailureListener {exe->
                Log.d("UserDetails", exe.message.toString())
            }
        }
    }

}