package com.example.aplikacjamedyczna.user

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class UserMainPage : AppCompatActivity() {
    lateinit var sessionManager: SessionManager
    lateinit var searchInput: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_page)
        supportActionBar?.hide()
        sessionManager = SessionManager(applicationContext)

        sessionManager.checkUserLogin()
        val user: HashMap<String,String> =sessionManager.getUserDetails()
        val name: String = user.get(SessionManager.KEY_EMAIL)!!

        searchInput = findViewById(R.id.searchInput)
        val searchFragment = SearchFragment()
        searchInput.setOnClickListener {
            //funkcja do szukania otwierana w nowym fragmencie
            setCurrentFragment(searchFragment)
        }
        val firstFragment = VisitsFragment()
        val secondFragment= SecondFragment()
        val registerToVisitFragment = RegisterToVisitFragment()
        setCurrentFragment(firstFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { when(it.itemId){
            R.id.visitsMenu->setCurrentFragment(firstFragment)
            R.id.page_2->setCurrentFragment(secondFragment)
            R.id.registerToVisitMenu->setCurrentFragment(registerToVisitFragment)
        }
            true
        }
    }
    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply { replace(R.id.flFragment,fragment)
        commit()}
    }
}

