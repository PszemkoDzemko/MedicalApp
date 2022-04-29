package com.example.medicalapp.doctor

import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.example.medicalapp.FirebaseRepository
import com.example.medicalapp.R
import com.example.medicalapp.data.Doctor
import com.example.medicalapp.registration.RegisterToVisitFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso

class DoctorDescriptionFragment(doctor: Doctor) : Fragment(R.layout.fragment_doctor_description),
    OnMapReadyCallback {
    private lateinit var mMap: GoogleMap
    private val doc = doctor
    private val fbRes = FirebaseRepository()
    private lateinit var docName: TextView
    private lateinit var docSurname: TextView
    private lateinit var docSpec: TextView
    private lateinit var nrRat: TextView
    private lateinit var docImg: ImageView
    private lateinit var docRatingBar: RatingBar
    private lateinit var rateButton: Button
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
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if(doc.img.toString().isEmpty()){
            docImg.setImageResource(R.drawable.defaultdoc)
        }else{
            Picasso.get().load(doc.img).into(docImg)
        }
        registerToVisitButton = view.findViewById(R.id.goToRegisterToVisitButton)
        docName.text = doc.name
        docSurname.text = doc.surname
        docSpec.text = doc.specialization
        nrRat.text = doc.nrRating + getString(R.string.opinion)
        val rat = doc.rating?.toFloat()!! / doc.nrRating?.toFloat()!!
        docRatingBar.rating = rat
        rateButton.setOnClickListener {
            val newNrRat = doc.nrRating.toInt().plus(1)
            val newRat = doc.rating.toFloat().plus(docRatingBar.rating)
            fbRes.addDocRating(doc.uid.toString(),newRat.toString(),newNrRat.toString())
            val myFragment = ShowDoctorsFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.detach(myFragment)?.attach(myFragment)?.commit()
        }
        registerToVisitButton.setOnClickListener{
            val myFragment = RegisterToVisitFragment(doc)
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }

    }
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        val geocoder = Geocoder(activity?.applicationContext)
        var addressList:List<Address>?=null
        addressList = if (doc.localization.toString().isNotEmpty()){
            geocoder.getFromLocationName(doc.localization,1)
        }else{
            geocoder.getFromLocationName("Polska",1)
        }
        val address = addressList!![0]
        val latLng = LatLng(address.latitude,address.longitude)
        mMap.addMarker(MarkerOptions().position(latLng).title(doc.localization))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,13F))
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15F),2000,null)
    }
}