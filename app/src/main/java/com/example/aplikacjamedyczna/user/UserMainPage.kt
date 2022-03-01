package com.example.aplikacjamedyczna.user

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserMainPage : AppCompatActivity() {
    private lateinit var sessionManager: SessionManager
    private lateinit var searchInput: EditText
    private lateinit var doctorAdapter: DoctorAdapter
    val activity = this@UserMainPage

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_page)
        supportActionBar?.hide()
        sessionManager = SessionManager(applicationContext)
        sessionManager.checkUserLogin()
//        val user: HashMap<String,String> =sessionManager.getUserDetails()
//        val name: String = user.get(SessionManager.KEY_EMAIL)!!
        val showDoctorsFragment = ShowDoctorsFragment()
        val secondFragment = SecondFragment()
        val registerToVisitFragment = RegisterToVisitFragment()
        setCurrentFragment(showDoctorsFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.visitsMenu -> setCurrentFragment(showDoctorsFragment)
                R.id.page_2 -> setCurrentFragment(secondFragment)
                R.id.registerToVisitMenu -> setCurrentFragment(registerToVisitFragment)
            }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply { replace(R.id.flFragment,fragment)
        commit()}
    }
}



