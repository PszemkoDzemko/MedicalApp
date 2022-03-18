package com.example.aplikacjamedyczna.doctor

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Doctor

class ShowDoctorsFragment : Fragment(), OnDoctorItemLongClick {

    private var doctorViewModel: DoctorViewModel = DoctorViewModel()
    private val adapter = DoctorAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_doctors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.doctorsRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        doctorViewModel.doctors.observe(viewLifecycleOwner) { list ->
            adapter.setDoctors(list)
        }
    }

    override fun onDoctorLongClick(doctor: Doctor, position: Int) {

    }
}