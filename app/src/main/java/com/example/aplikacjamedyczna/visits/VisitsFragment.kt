package com.example.aplikacjamedyczna.visits

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.visits.VisitAdapter

class VisitsFragment : Fragment(R.layout.fragment_visits)  {
    private lateinit var recycleView: RecyclerView
    private lateinit var visitId: ArrayList<String>
    private lateinit var visitDoctorName: ArrayList<String>
    private lateinit var visitDoctorSurname: ArrayList<String>
    private lateinit var visitDoctorSpec: ArrayList<String>
    private lateinit var visitDate: ArrayList<String>
    private lateinit var visitTime: ArrayList<String>
    private var visitAdapter: VisitAdapter = VisitAdapter()
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycleView = view.findViewById(R.id.visitsRecycleView)
        visitId = ArrayList()
        visitDoctorName = ArrayList()
        visitDoctorSurname = ArrayList()
        visitDoctorSpec = ArrayList()
        visitDate = ArrayList()
        visitTime = ArrayList()
        visitAdapter.setNewVisit(visitId,visitDoctorName,visitDoctorSurname,visitDoctorSpec,visitDate,visitTime)
        val layoutManager = LinearLayoutManager(activity)
        recycleView.layoutManager = layoutManager
        recycleView.adapter = visitAdapter

    }
}