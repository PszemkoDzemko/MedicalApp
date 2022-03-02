package com.example.aplikacjamedyczna

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.example.aplikacjamedyczna.user.UserMainPage

class DoctorDescriptionFragment(doctorid: String) : Fragment(R.layout.fragment_doctor_description) {
    private lateinit var databaseHelper: DatabaseHelper
    private var userMainPage = UserMainPage()
    private lateinit var docName:TextView
    private lateinit var docSurname:TextView
    private lateinit var docSpec:TextView
    var doctrid:String = doctorid
    override fun onAttach(context: Context) {
        super.onAttach(context)
        databaseHelper = DatabaseHelper(activity)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        docName = view.findViewById(R.id.docDescNameLabel)
        docSurname = view.findViewById(R.id.docDescSurnameLabel)
        docSpec = view.findViewById(R.id.docDescEmailLabel)
        storeToStringData()
    }

    private fun storeToStringData() {
        val cursor = databaseHelper.readDocDescription(doctrid)
        if(cursor.count < 0 ){
            Toast.makeText(userMainPage,"No data", Toast.LENGTH_SHORT)
        }else{
            while (cursor.moveToNext()) {
                docName.text = cursor.getString(1)
                docSurname.text = (cursor.getString(2))
                docSpec.text = (cursor.getString(4))
            }
        }

    }
}