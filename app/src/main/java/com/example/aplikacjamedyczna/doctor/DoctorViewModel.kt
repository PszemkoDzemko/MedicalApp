package com.example.aplikacjamedyczna.doctor

import androidx.lifecycle.ViewModel
import com.example.aplikacjamedyczna.FirebaseRepository

class DoctorViewModel:ViewModel() {
    private val respository = FirebaseRepository()
    val doctors = respository.getDoctorData()
}