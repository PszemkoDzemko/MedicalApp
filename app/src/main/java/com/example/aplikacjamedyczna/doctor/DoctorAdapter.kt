package com.example.aplikacjamedyczna.doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Doctor

class DoctorAdapter(private val listener: OnDoctorItemLongClick): RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    private val doctorsList = ArrayList<Doctor>()

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

        name.text = doctorsList[holder.adapterPosition].name
        surname.text = doctorsList[holder.adapterPosition].surname
        spec.text = doctorsList[holder.adapterPosition].specialization
    }

    override fun getItemCount(): Int {
        return doctorsList.size
    }

    inner class DoctorViewHolder(view: View):RecyclerView.ViewHolder(view) {
        init {
            view.setOnLongClickListener{
                listener.onDoctorLongClick(doctorsList[adapterPosition], adapterPosition)
                true
            }
        }
    }
}
interface OnDoctorItemLongClick{
    fun onDoctorLongClick(doctor:Doctor,position: Int)
}