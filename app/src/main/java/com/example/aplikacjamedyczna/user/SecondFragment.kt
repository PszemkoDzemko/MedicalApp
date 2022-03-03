package com.example.aplikacjamedyczna.user

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.DatabaseHelper
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.SessionManager

class SecondFragment : Fragment(R.layout.fragment_second)  {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var sessionManager: SessionManager
    private lateinit var recycleView: RecyclerView
    private lateinit var visitId: ArrayList<String>
    private lateinit var visitDoctorName: ArrayList<String>
    private lateinit var visitDoctorSurname: ArrayList<String>
    private lateinit var visitDoctorSpec: ArrayList<String>
    private lateinit var visitDate: ArrayList<String>
    private lateinit var visitTime: ArrayList<String>
    private var visitAdapter: VisitAdapter = VisitAdapter()
    private lateinit var userMail: String
    override fun onAttach(context: Context) {
        super.onAttach(context)
        databaseHelper = DatabaseHelper(activity)
        sessionManager = SessionManager(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleView = view.findViewById(R.id.visitsRecycleView)
        val user: HashMap<String,String> = sessionManager.getUserDetails()
        userMail = user[SessionManager.KEY_EMAIL]!!
        visitId = ArrayList()
        visitDoctorName = ArrayList()
        visitDoctorSurname = ArrayList()
        visitDoctorSpec = ArrayList()
        visitDate = ArrayList()
        visitTime = ArrayList()
        storeDataToArrays()
        visitAdapter.setNewVisit(visitId,visitDoctorName,visitDoctorSurname,visitDoctorSpec,visitDate,visitTime)
        val layoutManager = LinearLayoutManager(activity)
        recycleView.layoutManager = layoutManager
        recycleView.adapter = visitAdapter

    }

    private fun storeDataToArrays() {
        val cursor = databaseHelper.readPatientVisit(userMail)
        if(cursor.count<0){

        }else{
            while (cursor.moveToNext()) {
                visitId.add(cursor.getString(0))
                visitDoctorName.add(cursor.getString(2))
                visitDoctorSurname.add(cursor.getString(3))
                visitDoctorSpec.add(cursor.getString(4))
                visitDate.add(cursor.getString(5))
                visitTime.add(cursor.getString(6))
            }
        }
    }


}