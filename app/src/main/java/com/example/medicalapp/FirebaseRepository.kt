package com.example.medicalapp

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.medicalapp.data.Doctor
import com.example.medicalapp.data.Referral
import com.example.medicalapp.data.UsersData
import com.example.medicalapp.data.Visits
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()

    fun getUserData(): LiveData<UsersData> {
        val result = MutableLiveData<UsersData>()
        val uid = auth.currentUser?.uid
        database.collection("users").document(uid!!)
            .get()
            .addOnSuccessListener {
                val user = it.toObject(UsersData::class.java)
                result.postValue(user)
            }
            .addOnFailureListener {
                Log.d("Repository",it.message.toString())
            }
        return result
    }

    fun getDoctorData(): LiveData<List<Doctor>> {
        val result = MutableLiveData<List<Doctor>>()

        database.collection("doctors").get()
            .addOnSuccessListener {
                val doctor = it.toObjects(Doctor::class.java)
                result.postValue(doctor)
            }
            .addOnFailureListener {
                Log.d("Repository",it.message.toString())
            }
        return result
    }

    fun addDocRating(id:String,rating:String,nrRating:String){
        database.collection("doctors").document(id).update("rating",rating)
        database.collection("doctors").document(id).update("nrRating",nrRating)

    }

    fun getNewVisitData(): LiveData<List<Visits>> {
        val result = MutableLiveData<List<Visits>>()
        val uid = auth.currentUser?.uid
        database.collection("visits").whereEqualTo("id_pac",uid).whereEqualTo("done",false).get()
            .addOnSuccessListener {
                val visit = it.toObjects(Visits::class.java)
                result.postValue(visit)
            }
            .addOnFailureListener {
                Log.d("Repository",it.message.toString())
            }
        return result
    }

    fun getOldVisitData(): LiveData<List<Visits>> {
        val result = MutableLiveData<List<Visits>>()
        val uid = auth.currentUser?.uid
        database.collection("visits").whereEqualTo("id_pac",uid).whereEqualTo("done",true).get()
            .addOnSuccessListener {
                val visit = it.toObjects(Visits::class.java)
                result.postValue(visit)
            }
            .addOnFailureListener {
                Log.d("Repository",it.message.toString())
            }
        return result
    }

    fun getDoctorDataById(id:String): LiveData<Doctor> {
        val result = MutableLiveData<Doctor>()
        database.collection("doctors").document(id)
            .get()
            .addOnSuccessListener {
                val doc = it.toObject(Doctor::class.java)
                result.postValue(doc)
            }
            .addOnFailureListener {
                Log.d("Repository",it.message.toString())
            }
        return result
    }
    fun getReferralData(): LiveData<List<Referral>> {
        val result = MutableLiveData<List<Referral>>()
        val uid = auth.currentUser?.uid
        database.collection("referral").whereEqualTo("id_pac",uid).whereEqualTo("done",false).get()
            .addOnSuccessListener {
                val prescription = it.toObjects(Referral::class.java)
                result.postValue(prescription)
            }
            .addOnFailureListener {
                Log.d("Repository",it.message.toString())
            }
        return result
    }

}