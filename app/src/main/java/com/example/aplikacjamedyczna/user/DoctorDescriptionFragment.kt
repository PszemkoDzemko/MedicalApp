package com.example.aplikacjamedyczna.user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.aplikacjamedyczna.DatabaseHelper
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.SessionManager

class DoctorDescriptionFragment(doctorid: String) : Fragment(R.layout.fragment_doctor_description) {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private var userMainPage = UserMainPage()
    private lateinit var docName:TextView
    private lateinit var docSurname:TextView
    private lateinit var docSpec:TextView
    private lateinit var visitDate:TextView
    private lateinit var visitTime:TextView
    private lateinit var registerToVisitButton: Button
    var doctrid:String = doctorid
    override fun onAttach(context: Context) {
        super.onAttach(context)
        databaseHelper = DatabaseHelper(activity)
        sessionManager = SessionManager(context)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        docName = view.findViewById(R.id.docDescNameLabel)
        docSurname = view.findViewById(R.id.docDescSurnameLabel)
        docSpec = view.findViewById(R.id.docDescEmailLabel)
        visitDate = view.findViewById(R.id.vistDateRegisterInput)
        visitTime = view.findViewById(R.id.visitTimeRegisterInput)
        registerToVisitButton = view.findViewById(R.id.registerToVisitButton)

        storeToStringData()

        val user: HashMap<String,String> = sessionManager.getUserDetails()
        val userMail: String = user[SessionManager.KEY_EMAIL]!!

        registerToVisitButton.setOnClickListener{
            databaseHelper.registerToVisit(userMail,docName.text.toString().trim(),docSurname.text.toString().trim(),docSpec.text.toString().trim(), visitDate.text.toString().trim(), visitTime.text.toString().trim())

        }
    }

    private fun storeToStringData() {
        val cursor = databaseHelper.readDocDescription(doctrid)
        if(cursor.count < 0 ){
            Toast.makeText(userMainPage,"No data", Toast.LENGTH_SHORT).show()
        }else{
            while (cursor.moveToNext()) {
                docName.text = cursor.getString(1)
                docSurname.text = cursor.getString(2)
                docSpec.text = cursor.getString(4)
            }
        }

    }
}