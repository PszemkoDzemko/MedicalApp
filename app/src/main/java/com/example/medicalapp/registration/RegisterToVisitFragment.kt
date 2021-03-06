package com.example.medicalapp.registration

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.medicalapp.R
import com.example.medicalapp.data.Doctor
import java.util.*

class RegisterToVisitFragment(doc: Doctor) : Fragment(R.layout.fragment_register_to_visit) {
    private lateinit var chooseData: Button
    private lateinit var chooseTime: Button
    private lateinit var registerToVisit: Button
    private lateinit var dataTextView: TextView
    private lateinit var timeTextView: TextView
    private var doctor = doc
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chooseData = view.findViewById(R.id.chooseVisitDataButton)
        chooseTime = view.findViewById(R.id.chooseVisitTimeButton)
        registerToVisit = view.findViewById(R.id.registerVisitButton)
        dataTextView = view.findViewById(R.id.choosenDataTextView)
        timeTextView = view.findViewById(R.id.choosenTimeTextView)
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val hour = myCalendar.get(Calendar.HOUR_OF_DAY)
        val minute = myCalendar.get(Calendar.MINUTE)
        var myHour: Int
        var myMinute: Int
        var myYear: Int
        var myMonth: Int
        var myDay: Int
        var allData = ""
        var allTime = ""
        chooseData.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(),
                { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                    myYear = selectedYear
                    myMonth = selectedMonth+1
                    myDay = selectedDayOfMonth
                    allData = if(myMonth<10){
                        if(myDay<10){
                            "$myYear-0$myMonth-0$myDay"
                        }else {
                            "$myYear-0$myMonth-$myDay"
                        }
                    }else if(myDay<10){
                        "$myYear-$myMonth-0$myDay"
                    }else{
                        "$myYear-$myMonth-$myDay"
                    }
                    dataTextView.text = allData
                },
                year,
                month,
                day,
            )
            dpd.datePicker.minDate = myCalendar.timeInMillis;
            dpd.show();
        }

        chooseTime.setOnClickListener {
            val tpd = TimePickerDialog(
                requireContext(), { view, selectedHour, selectedMin->
                    myHour = selectedHour
                    myMinute = selectedMin
                    allTime = if(myHour<10){
                        if(myMinute<10){
                            "0$myHour:0$myMinute"
                        }else{
                            "0$myHour:$myMinute"
                        }
                    }else if(myMinute<10){
                        "$myHour:0$myMinute"
                    }else{
                        "$myHour:$myMinute"
                    }

                    timeTextView.text = allTime
                }
                ,hour,minute,true).show()
        }

        registerToVisit.setOnClickListener {
            val myFragment = ConfirmVisitRegisterFragment(doctor,allData,allTime)
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }
    }
}