package com.example.aplikacjamedyczna.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.R

class ReferralFragment : Fragment() {
    private lateinit var adapter:ReferralAdapter
    private val respository:FirebaseRepository = FirebaseRepository()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_referral, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.prescriptionRecyclerView)
        adapter = ReferralAdapter(viewLifecycleOwner,activity)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        respository.getReferralData().observe(viewLifecycleOwner) { list ->
            adapter.setPrescription(list)
        }

    }

}