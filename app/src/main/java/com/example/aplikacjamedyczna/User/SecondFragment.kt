package com.example.aplikacjamedyczna.User

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.SessionManager

class SecondFragment : Fragment(R.layout.fragment_second)  {
    lateinit var logoutButton:Button
    lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext().applicationContext)
        logoutButton = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { sessionManager.logoutUser() }
    }



}