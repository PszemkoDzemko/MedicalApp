package com.example.medicalapp.user

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.medicalapp.FirebaseRepository
import com.example.medicalapp.MainActivity
import com.example.medicalapp.R
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var logoutButton: Button
    private lateinit var changeData: Button
    private lateinit var userName: TextView
    private lateinit var userSurname:TextView
    private val fbAuth = FirebaseAuth.getInstance()
    private val repository = FirebaseRepository()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton = view.findViewById(R.id.logoutButton)
        changeData = view.findViewById(R.id.goToChangeEditProfileButton)
        userName = view.findViewById(R.id.nameProfileFragment)
        userSurname = view.findViewById(R.id.surnameProfileFragment)
        repository.getUserData().observe(viewLifecycleOwner) { list ->
            userName.text = list.name
            userSurname.text = list.surname
        }
        logoutButton.setOnClickListener {
            fbAuth.signOut()
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }


        changeData.setOnClickListener {
            val myFragment = ChangeUserData()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

    }

}