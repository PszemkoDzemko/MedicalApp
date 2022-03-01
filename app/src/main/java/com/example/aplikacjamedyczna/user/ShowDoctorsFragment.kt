package com.example.aplikacjamedyczna.user

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.DatabaseHelper
import com.example.aplikacjamedyczna.R


class ShowDoctorsFragment : Fragment(R.layout.fragment_show_doctors) {

    private lateinit var recycleView:RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var doctor_id: ArrayList<String>
    private lateinit var doctor_name: ArrayList<String>
    private lateinit var doctor_surname: ArrayList<String>
    private lateinit var doctor_specialization: ArrayList<String>
    private lateinit var userMainPage: UserMainPage
    private var doctorAdapter: DoctorAdapter = DoctorAdapter()
    override fun onAttach(context: Context) {
        super.onAttach(context)
        databaseHelper = DatabaseHelper(activity)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userMainPage = UserMainPage()
        recycleView = view.findViewById(R.id.doctorRecycleView)
        doctor_id = ArrayList()
        doctor_name = ArrayList()
        doctor_surname = ArrayList()
        doctor_specialization = ArrayList()
        storeDataInArrays()
        val layoutManager = LinearLayoutManager(activity)
        doctorAdapter.setDoctorList(doctor_id,doctor_name,doctor_surname,doctor_specialization)

        recycleView.layoutManager = layoutManager
        recycleView.adapter = doctorAdapter
        val searchView = view.findViewById<SearchView>(R.id.searchView)
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                //Log.e("Tag","$newText")
                doctorAdapter.filter.filter(newText)
                return true
            }
        })
    }


    fun storeDataInArrays(){
        val cursor = databaseHelper.readAllDoctors()
        if(cursor.count<0){
            Toast.makeText(userMainPage,"No data",Toast.LENGTH_SHORT)
        }else{
            while (cursor.moveToNext()){//Pobiera dane z kursora i wsadza je to tych array stringów
                doctor_id.add(cursor.getString(0))
                doctor_name.add(cursor.getString(1))
                doctor_surname.add(cursor.getString(2))
                doctor_specialization.add(cursor.getString(4))
            }
        }

    }
}