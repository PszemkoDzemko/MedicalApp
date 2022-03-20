package com.example.aplikacjamedyczna.visits

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Visits

class VisitsFragment : Fragment(), OnVisitItemClick {

    private lateinit var adapter:VisitAdapter
    private val respository = FirebaseRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_visits, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = VisitAdapter(viewLifecycleOwner,this)
        val recyclerView = view.findViewById<RecyclerView>(R.id.visitsRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        respository.getVisitData().observe(viewLifecycleOwner) { list ->
            adapter.setVisits(list)
        }
    }

    override fun onVisitClick(visits: Visits, position: Int) {
        //przejscie do szczegółów wizyty


    }

}