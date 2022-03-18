package com.example.aplikacjamedyczna.user

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.doctor.ShowDoctorsFragment
import com.example.aplikacjamedyczna.registration.UserDetails
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class UserMainPage : AppCompatActivity() {
    private val fbAuth = FirebaseAuth.getInstance()
    private var database = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_page)
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
        val uid = fbAuth.currentUser?.uid.toString()

        val userDataExists = database.collection("users").document(uid)
        userDataExists.get().addOnCompleteListener { task->
            if(task.isSuccessful){
                val document = task.result
                if(document != null){
                    if(document.exists()){
                    }else{
                        val intent = Intent(applicationContext, UserDetails::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply { replace(R.id.flFragment,fragment)
        commit()}
    }
}



