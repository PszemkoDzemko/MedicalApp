package com.example.aplikacjamedyczna.doctor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Doctor

class ShowDoctorsFragment : Fragment(), OnDoctorItemClick {

    private val respository = FirebaseRepository()
    private val adapter = DoctorAdapter(this)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_doctors, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.doctorsRecycleView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        respository.getDoctorData().observe(viewLifecycleOwner) { list ->
            adapter.setDoctors(list)
        }
        val searchView:SearchView = view.findViewById(R.id.doctorSearchView)
        searchView.setOnQueryTextListener(object:SearchView.OnQueryTextListener{
            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return true
            }

            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return true
            }
        })
    }

    override fun onDoctorClick(doctor: Doctor, position: Int) {
        val myFragment = DoctorDescriptionFragment(doctor)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
            ?.addToBackStack(null)
            ?.commit()
    }


}