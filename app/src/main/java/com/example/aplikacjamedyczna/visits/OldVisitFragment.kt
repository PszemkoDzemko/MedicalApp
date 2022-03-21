package com.example.aplikacjamedyczna.visits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.R

class OldVisitFragment : Fragment() {

    private lateinit var adapter:OldVisitAdapter
    private val respository = FirebaseRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_old_visit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OldVisitAdapter(viewLifecycleOwner,activity)
        val recyclerView = view.findViewById<RecyclerView>(R.id.oldVisitRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        respository.getOldVisitData().observe(viewLifecycleOwner) { list ->
            adapter.setVisits(list)
        }

    }
}