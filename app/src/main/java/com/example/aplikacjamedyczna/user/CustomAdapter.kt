package com.example.aplikacjamedyczna.user

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.R


class CustomAdapter(doc_id: ArrayList<String>, doc_name: ArrayList<String>,doc_surname:ArrayList<String>, doc_specialization: ArrayList<String>): RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    val doctor_id:ArrayList<String> = doc_id
    val doctor_name:ArrayList<String> = doc_name
    val doctor_surname:ArrayList<String> = doc_surname
    val doctor_specialization:ArrayList<String> = doc_specialization


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameDoctor:TextView  = itemView.findViewById(R.id.nameCardDoctor)
        var surnameDoctor:TextView  = itemView.findViewById(R.id.surnameCardDoctor)
        var specializtionDoctor:TextView = itemView.findViewById(R.id.specCardDoctor)
        var idDoctor:TextView = itemView.findViewById(R.id.descCardDoctor)
        var mainLayout:LinearLayout = itemView.findViewById(R.id.mainLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        val view:View = inflater.inflate(R.layout.doctors_card_view,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.idDoctor.text=doctor_id[position]
        holder.nameDoctor.text=doctor_name[position]
        holder.surnameDoctor.text=doctor_surname[position]
        holder.specializtionDoctor.text=doctor_specialization[position]
        holder.mainLayout.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return doctor_id.size
    }
}