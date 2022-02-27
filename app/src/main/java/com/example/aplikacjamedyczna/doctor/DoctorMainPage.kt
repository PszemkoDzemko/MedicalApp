package com.example.aplikacjamedyczna.doctor

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.SessionManager

class DoctorMainPage : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    private lateinit var doctorLogoutButton:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.doctor_main_page)
        supportActionBar?.hide()
        sessionManager = SessionManager(applicationContext)

        doctorLogoutButton = findViewById(R.id.doctorLogoutButton)
        doctorLogoutButton.setOnClickListener { sessionManager.logoutUser() }
        sessionManager.checkDoctorLogin()
        val user: HashMap<String,String> =sessionManager.getUserDetails()
        val name: String = user.get(SessionManager.KEY_EMAIL)!!
    }
}