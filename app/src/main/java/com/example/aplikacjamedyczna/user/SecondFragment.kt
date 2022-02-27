package com.example.aplikacjamedyczna.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.SessionManager

class SecondFragment : Fragment(R.layout.fragment_second)  {
    private lateinit var logoutButton:Button
    private lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sessionManager = SessionManager(requireContext().applicationContext)
        logoutButton = view.findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener { sessionManager.logoutUser() }
    }



}