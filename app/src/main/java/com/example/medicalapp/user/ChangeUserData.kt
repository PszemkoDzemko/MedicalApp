package com.example.medicalapp.user

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.medicalapp.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class ChangeUserData : Fragment(R.layout.fragment_change_user_data) {private lateinit var newUserName: EditText
    private lateinit var newUserSurname: EditText
    private lateinit var newUserPassword: EditText
    private lateinit var newUserAddress: EditText
    private lateinit var confirmChangeData: Button
    private val fbAuth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        confirmChangeData = view.findViewById(R.id.goToChangeEditProfileButton)
        newUserName = view.findViewById(R.id.userNameEditProfile)
        newUserSurname = view.findViewById(R.id.userSurnameEditProfile)
        newUserAddress = view.findViewById(R.id.userAddressEditProfile)
        newUserPassword = view.findViewById(R.id.userPasswordEditProfile)
        val uid = fbAuth.uid
        val user = fbAuth.currentUser
        confirmChangeData.setOnClickListener {
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
                        Log.e("ProfileFragmentError",error.message.toString())
                    }
            }
            if(newUserAddress.text.isNotEmpty()){
                database.collection("users").document(uid.toString()).update("address",newUserAddress.text.toString())
            }
            val myFragment = ProfileFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
            Toast.makeText(activity?.applicationContext,getString(R.string.changesBeenSaved), Toast.LENGTH_SHORT).show()
        }

    }
}