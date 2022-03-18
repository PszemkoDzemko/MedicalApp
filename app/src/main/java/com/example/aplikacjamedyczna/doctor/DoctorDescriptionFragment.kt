package com.example.aplikacjamedyczna.doctor

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Doctor
import com.example.aplikacjamedyczna.user.UserMainPage

class DoctorDescriptionFragment(doctor:Doctor) : Fragment(R.layout.fragment_doctor_description) {
    private lateinit var docName:TextView
    private lateinit var docSurname:TextView
    private lateinit var docSpec:TextView
    private lateinit var visitDate:TextView
    private lateinit var visitTime:TextView
    private lateinit var registerToVisitButton: Button
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        docName = view.findViewById(R.id.docDescNameLabel)
        docSurname = view.findViewById(R.id.docDescSurnameLabel)
        docSpec = view.findViewById(R.id.docDescEmailLabel)
        visitDate = view.findViewById(R.id.vistDateRegisterInput)
        visitTime = view.findViewById(R.id.visitTimeRegisterInput)
        registerToVisitButton = view.findViewById(R.id.registerToVisitButton)
        registerToVisitButton.setOnClickListener{

        }
    }

}