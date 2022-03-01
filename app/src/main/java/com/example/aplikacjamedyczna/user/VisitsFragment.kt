package com.example.aplikacjamedyczna.user

import android.content.Context
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.DatabaseHelper
import com.example.aplikacjamedyczna.R


class VisitsFragment : Fragment(R.layout.fragment_visits) {

    private lateinit var recycleView:RecyclerView
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var doctor_id: ArrayList<String>
    private lateinit var doctor_name: ArrayList<String>
    private lateinit var doctor_specialization: ArrayList<String>
    private lateinit var userMainPage: UserMainPage
    private lateinit var customAdapter: CustomAdapter
    private lateinit var cursor: Cursor
    override fun onAttach(context: Context) {
        super.onAttach(context)
        databaseHelper = DatabaseHelper(activity)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userMainPage = UserMainPage()
        recycleView = view.findViewById(R.id.doctorRecycleView)
        doctor_id = ArrayList();
        doctor_name = ArrayList();
        doctor_specialization = ArrayList();
        storeDataInArrays()

        //customAdapter = CustomAdapter(userMainPage.activity,userMainPage.applicationContext,doctor_id,doctor_name,doctor_specialization)
        //recycleView.adapter = customAdapter


    }
    fun storeDataInArrays(){
        val cursor = databaseHelper.readAllDoctors()
        if(cursor.count<0){
            Toast.makeText(userMainPage,"No data",Toast.LENGTH_SHORT)
        }else{
            while (cursor.moveToNext()){//Pobiera dane z kursora i wsadza je to tych array stringów
                doctor_id.add(cursor.getString(0))
                doctor_name.add(cursor.getString(1))
                doctor_specialization.add(cursor.getString(4))
            }
        }

    }
}