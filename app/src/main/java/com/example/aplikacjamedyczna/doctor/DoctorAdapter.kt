package com.example.aplikacjamedyczna.doctor

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Doctor

class DoctorAdapter(private val listener: OnDoctorItemClick): RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    private val doctorsList = ArrayList<Doctor>()

    @SuppressLint("NotifyDataSetChanged")
    fun setDoctors(list: List<Doctor>){
        doctorsList.clear()
        doctorsList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.doctors_card_view,parent,false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: DoctorViewHolder) {
        val name = holder.itemView.findViewById<TextView>(R.id.nameCardDoctor)
        val surname = holder.itemView.findViewById<TextView>(R.id.surnameCardDoctor)
        val spec = holder.itemView.findViewById<TextView>(R.id.specCardDoctor)
        val rating = holder.itemView.findViewById<RatingBar>(R.id.ratingBar)
        val rat =(doctorsList[holder.adapterPosition].rating)?.toFloat()
        val nrRat = (doctorsList[holder.adapterPosition].nrRating)?.toFloat()
        if (rat != null) {
            rating.rating = rat / nrRat!!
        }
        rating.setIsIndicator(true)
        name.text = doctorsList[holder.adapterPosition].name
        surname.text = doctorsList[holder.adapterPosition].surname
        spec.text = doctorsList[holder.adapterPosition].specialization
    }

    override fun getItemCount(): Int {
        return doctorsList.size
    }

    inner class DoctorViewHolder(view: View):RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener{
                listener.onDoctorClick(doctorsList[adapterPosition], adapterPosition)
                true
            }
        }
    }
}
interface OnDoctorItemClick{
    fun onDoctorClick(doctor:Doctor,position: Int)
}