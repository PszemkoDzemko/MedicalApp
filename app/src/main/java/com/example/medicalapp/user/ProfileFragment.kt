package com.example.medicalapp.user

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.medicalapp.FirebaseRepository
import com.example.medicalapp.MainActivity
import com.example.medicalapp.R
import com.example.medicalapp.visits.NewVisitFragment
import com.google.firebase.auth.FirebaseAuth
import kotlin.system.exitProcess

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private lateinit var logoutButton: Button
    private lateinit var changeData: Button
    private lateinit var userName: TextView
    private lateinit var deleteAccButton: Button
    private lateinit var userSurname:TextView
    private val fbAuth = FirebaseAuth.getInstance()
    private val repository = FirebaseRepository()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        logoutButton = view.findViewById(R.id.logoutButton)
        changeData = view.findViewById(R.id.goToChangeEditProfileButton)
        userName = view.findViewById(R.id.nameProfileFragment)
        userSurname = view.findViewById(R.id.surnameProfileFragment)
        deleteAccButton = view.findViewById(R.id.accDeleteButton)
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
        deleteAccButton.setOnClickListener {
            val sureToDelete = LayoutInflater.from(context).inflate(R.layout.sure_to_delete,null)
            AlertDialog.Builder(requireContext())
                .setView(sureToDelete)
                .setPositiveButton("OK"){
                        dialog, _ ->
                        fbAuth.currentUser?.delete()?.addOnSuccessListener {
                            fbAuth.signOut()
                            val intent = Intent(context, MainActivity::class.java).apply {
                                flags = (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                            }
                            startActivity(intent)
                        }
                }
                .setNegativeButton(R.string.cancel){
                        dialog,_ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }

        changeData.setOnClickListener {
            val myFragment = ChangeUserData()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

    }

}