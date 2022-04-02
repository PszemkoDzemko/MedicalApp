package com.example.aplikacjamedyczna.registration

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.TextView
import android.widget.TimePicker
import androidx.fragment.app.Fragment
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Doctor
import java.util.*

class RegisterToVisitFragment(doc:Doctor) : Fragment(R.layout.fragment_register_to_visit) {
    private lateinit var chooseData:Button
    private lateinit var chooseTime:Button
    private lateinit var registerToVisit:Button
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
        var myHour = 0
        var myMinute = 0
        var myYear = 0
        var myMonth = 0
        var myDay = 0
        var allData = ""
        var allTime = ""

        chooseData.setOnClickListener {
            val dpd = DatePickerDialog(
                requireContext(),
                { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                    myYear = selectedYear
                    myMonth = selectedMonth+1
                    myDay = selectedDayOfMonth
                    allData = "$myYear/$myMonth/$myDay"
                    dataTextView.text = allData
                },
                year,
                month,
                day
            ).show()
        }

        chooseTime.setOnClickListener {
            val tpd = TimePickerDialog(
                requireContext(), { view, selectedHour, selectedMin->
                    myHour = selectedHour
                    myMinute = selectedMin
                    allTime = "$myHour:$myMinute"
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

