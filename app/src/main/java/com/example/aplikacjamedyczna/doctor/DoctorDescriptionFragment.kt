package com.example.aplikacjamedyczna.doctor

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Doctor
import com.example.aplikacjamedyczna.registration.RegisterToVisitFragment
import com.squareup.picasso.Picasso

class DoctorDescriptionFragment(doctor:Doctor) : Fragment(R.layout.fragment_doctor_description) {
    private val doc = doctor
    private val fbRes = FirebaseRepository()
    private lateinit var docName:TextView
    private lateinit var docSurname:TextView
    private lateinit var docSpec:TextView
    private lateinit var nrRat:TextView
    private lateinit var docImg:ImageView
    private lateinit var docRatingBar:RatingBar
    private lateinit var rateButton:Button
    private lateinit var registerToVisitButton: Button
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        docName = view.findViewById(R.id.docDescNameLabel)
        docSurname = view.findViewById(R.id.docDescSurnameLabel)
        docSpec = view.findViewById(R.id.docDescSpecLabel)
        docImg = view.findViewById(R.id.imageView)
        rateButton = view.findViewById(R.id.rateButton)
        docRatingBar = view.findViewById(R.id.editableRatingBar)
        nrRat = view.findViewById(R.id.nrRatTextView)
        if(doc.img.toString().isEmpty()){
            docImg.setImageResource(R.drawable.defaultdoc)
        }else{
            Picasso.get().load(doc.img).into(docImg)
        }
        registerToVisitButton = view.findViewById(R.id.registerToVisitButton)
        docName.text = doc.name
        docSurname.text = doc.surname
        docSpec.text = doc.specialization
        nrRat.text = doc.nrRating + " Opinii"
        val rat = doc.rating?.toFloat()!! / doc.nrRating?.toFloat()!!
        docRatingBar.rating = rat
        rateButton.setOnClickListener {
            val newNrRat = doc.nrRating.toInt().plus(1)
            val newRat = doc.rating.toFloat().plus(docRatingBar.rating)
            fbRes.addDocRating(doc.uid.toString(),newRat.toString(),newNrRat.toString())
        }
        registerToVisitButton.setOnClickListener{
            val myFragment = RegisterToVisitFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }
    }

}