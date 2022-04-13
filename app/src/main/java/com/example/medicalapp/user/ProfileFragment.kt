package com.example.medicalapp.user

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.medicalapp.MainActivity
import com.example.medicalapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var logoutButton: Button
    private lateinit var newUserName: EditText
    private lateinit var newUserSurname: EditText
    private lateinit var newUserPassword: EditText
    private lateinit var newUserAddress: EditText
    private lateinit var changeData: Button
    private val database = FirebaseFirestore.getInstance()
    private val fbAuth = FirebaseAuth.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton = view.findViewById(R.id.logoutButton)
        changeData = view.findViewById(R.id.acceptChangesEditProfileButton)
        newUserName = view.findViewById(R.id.userNameEditProfile)
        newUserSurname = view.findViewById(R.id.userSurnameEditProfile)
        newUserAddress = view.findViewById(R.id.userAddressEditProfile)
        newUserPassword = view.findViewById(R.id.userPasswordEditProfile)
        val uid = fbAuth.uid
        val user = fbAuth.currentUser
        logoutButton.setOnClickListener {
            fbAuth.signOut()
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            }
            startActivity(intent)
        }
        changeData.setOnClickListener {
            if(newUserName.text.isNotEmpty()){
                database.collection("users").document(uid.toString()).update("name",newUserName.text.toString())
            }
            if(newUserSurname.text.isNotEmpty()){
                database.collection("users").document(uid.toString()).update("surname",newUserSurname.text.toString())
            }
            if(newUserPassword.text.isNotEmpty()) {
                user!!.updatePassword(newUserPassword.text.toString())
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d("Pass", "Hasło zostało zmienione")
                        }
                    }
                    .addOnFailureListener { error ->
                        Log.e("err",error.message.toString())
                    }
            }
            if(newUserAddress.text.isNotEmpty()){
                database.collection("users").document(uid.toString()).update("address",newUserAddress.text.toString())
            }
            val myFragment = ProfileFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
            Toast.makeText(activity?.applicationContext,"Zmiany zostały zapisane", Toast.LENGTH_SHORT).show()
        }

    }

}