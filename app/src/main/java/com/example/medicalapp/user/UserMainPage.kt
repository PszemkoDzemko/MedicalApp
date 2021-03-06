package com.example.medicalapp.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.medicalapp.R
import com.example.medicalapp.doctor.ShowDoctorsFragment
import com.example.medicalapp.registration.UserDetails
import com.example.medicalapp.visits.NewVisitFragment
import com.example.medicalapp.visits.OldVisitFragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging

class UserMainPage : AppCompatActivity() {
    private val fbAuth = FirebaseAuth.getInstance()
    private var database = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_main_page)
        val showDoctorsFragment = ShowDoctorsFragment()
        val newVisitsFragment = NewVisitFragment()
        val oldVisitFragment = OldVisitFragment()
        val prescriptionFragment = ReferralFragment()
        val profileFragment = ProfileFragment()
        setCurrentFragment(showDoctorsFragment)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.doctorsMenu -> setCurrentFragment(showDoctorsFragment)
                R.id.visitMenu -> setCurrentFragment(newVisitsFragment)
                R.id.registerToVisitMenu -> setCurrentFragment(oldVisitFragment)
                R.id.prescriptions -> setCurrentFragment(prescriptionFragment)
                R.id.profileMenu -> setCurrentFragment(profileFragment)
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
                        val intent = Intent(applicationContext, UserDetails::class.java).apply {
                            flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        }
                        startActivity(intent)
                    }
                }
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply { replace(R.id.flFragment,fragment)
            .addToBackStack(null)
            .commit() }
    }
}



