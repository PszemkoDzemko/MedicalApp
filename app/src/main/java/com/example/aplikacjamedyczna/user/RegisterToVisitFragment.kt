package com.example.aplikacjamedyczna.user

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.aplikacjamedyczna.MainActivity
import com.example.aplikacjamedyczna.R
import com.google.firebase.auth.FirebaseAuth

class RegisterToVisitFragment : Fragment(R.layout.fragment_register_to_visit) {
    private lateinit var logoutButton: Button
    private val fbAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            fbAuth.signOut()
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
    }
}