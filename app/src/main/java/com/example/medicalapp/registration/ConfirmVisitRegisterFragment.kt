package com.example.medicalapp.registration

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.medicalapp.FirebaseRepository
import com.example.medicalapp.R
import com.example.medicalapp.data.Doctor
import com.example.medicalapp.data.UsersData
import com.example.medicalapp.data.Visits
import com.example.medicalapp.doctor.ShowDoctorsFragment
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ConfirmVisitRegisterFragment(doc: Doctor, data:String, time:String) : Fragment(R.layout.fragment_confirm_visit_register) {

    private val doctor = doc
    private val visitData = data
    private val visitTime = time
    private lateinit var patientNameSurname: TextView
    private lateinit var doctorNameSurname: TextView
    private lateinit var docSpec: TextView
    private lateinit var localization: TextView
    private lateinit var dataAndTime: TextView
    private lateinit var confirmVisitButton: Button
    private val respository = FirebaseRepository()
    private var userUid = ""
    private var database = Firebase.firestore
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        patientNameSurname = view.findViewById(R.id.patientConfirmVisitFragmentTextView)
        doctorNameSurname = view.findViewById(R.id.doctorConfirmVisitFragmentTextView)
        docSpec = view.findViewById(R.id.docSpecConfirmVisitFragmentTextView)
        localization = view.findViewById(R.id.localizationConfirmVisitFragmentTextView)
        dataAndTime = view.findViewById(R.id.dataAndTimeConfirmVisitFragmentTextView)
        confirmVisitButton = view.findViewById(R.id.confirmVisitButton)
        respository.getUserData().observe(viewLifecycleOwner){user->
            bindUserData(user)
        }
        doctorNameSurname.text = "${doctor.name} ${doctor.surname}"
        docSpec.text = "${doctor.specialization}"
        localization.text = "${doctor.localization}"
        dataAndTime.text = "$visitData, $visitTime"
        confirmVisitButton.setOnClickListener {
            val visits = Visits(null,visitData,visitTime,userUid,doctor.uid)
            database.collection("visits").add(visits)
                .addOnSuccessListener {docRef->
                    database.collection("visits").document(docRef.id).update("id",docRef.id)
                    val myFragment = ShowDoctorsFragment()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                        ?.detach(myFragment)
                        ?.attach(myFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                }
                .addOnFailureListener { exe->
                    Log.e("ConfirmVisit",exe.message.toString())
                }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindUserData(user: UsersData) {
        patientNameSurname.text = "${user.name} ${user.surname}"
        userUid = user.uid.toString()
    }

}