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


class CustomAdapter(acti: Activity, con: Context, doc_id: ArrayList<String>, doc_name: ArrayList<String>, doc_specialization: ArrayList<String>): RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {
    val activity:Activity=acti
    val context:Context = con
    val doctor_id:ArrayList<String> = doc_id
    val doctor_name:ArrayList<String> = doc_name
    val doctor_specialization:ArrayList<String> = doc_specialization


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameDoctor:TextView  = itemView.findViewById(R.id.nameCardDoctor)
        var specializtionDoctor:TextView = itemView.findViewById(R.id.specCardDoctor)
        var idDoctor:TextView = itemView.findViewById(R.id.descCardDoctor)
        var mainLayout:LinearLayout = itemView.findViewById(R.id.mainLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater:LayoutInflater= LayoutInflater.from(context)
        val view:View = inflater.inflate(R.layout.doctors_card_view,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.idDoctor.text=doctor_id[position]
        holder.nameDoctor.text=doctor_name[position]
        holder.specializtionDoctor.text=doctor_specialization[position]
        holder.mainLayout.setOnClickListener{
            val intent:Intent = Intent(context,UpdateActivity::class.java)
            intent.putExtra("id",doctor_id[position])
            intent.putExtra("name",doctor_id[position])
            intent.putExtra("specialization",doctor_id[position])
            activity.startActivityForResult(intent,1)
        }
    }

    override fun getItemCount(): Int {
        return doctor_id.size
    }
}