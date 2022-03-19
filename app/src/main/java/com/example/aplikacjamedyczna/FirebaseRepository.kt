package com.example.aplikacjamedyczna

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.aplikacjamedyczna.data.Doctor
import com.example.aplikacjamedyczna.data.Visits
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.auth.User
import com.google.firebase.ktx.Firebase

class FirebaseRepository {
    private val auth = FirebaseAuth.getInstance()
    private val database = FirebaseFirestore.getInstance()

    fun getUserData(): LiveData<User>{
        val result = MutableLiveData<User>()
        val uid = auth.currentUser?.uid
        database.collection("users").document(uid!!)
            .get()
            .addOnSuccessListener {
                val user: User? = it.toObject(User::class.java)
                result.postValue(user)
            }
            .addOnFailureListener {
                Log.d("Repository",it.message.toString())
            }
        return result
    }

    fun getDoctorData(): LiveData<List<Doctor>>{
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

    fun getVisitData(): LiveData<List<Visits>>{
        val result = MutableLiveData<List<Visits>>()
        database.collection("visits").get()
            .addOnSuccessListener {
                val visit = it.toObjects(Visits::class.java)
                result.postValue(visit)
            }
            .addOnFailureListener {
                Log.d("Repository",it.message.toString())
            }
        return result
    }

}