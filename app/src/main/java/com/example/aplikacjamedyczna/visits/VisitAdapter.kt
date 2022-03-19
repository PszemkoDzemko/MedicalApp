package com.example.aplikacjamedyczna.visits

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.R

class VisitAdapter:RecyclerView.Adapter<VisitAdapter.MyViewHolder>()  {
    private lateinit var visitId:ArrayList<String>
    private lateinit var visitDoctorName:ArrayList<String>
    private lateinit var visitDoctorSurname:ArrayList<String>
    private lateinit var visitDoctorSpec:ArrayList<String>
    private lateinit var visitDate:ArrayList<String>
    private lateinit var visitTime:ArrayList<String>
    private lateinit var view:View

    fun setNewVisit(
        visId: ArrayList<String>,
        visDocName: ArrayList<String>,
        visDocSurname: ArrayList<String>,
        visDoctorSpec: ArrayList<String>,
        visDate: ArrayList<String>,
        visTime: ArrayList<String>,){
        visitId=visId
        visitDoctorName=visDocName
        visitDoctorSurname=visDocSurname
        visitDoctorSpec=visDoctorSpec
        visitDate=visDate
        visitTime=visTime
    }
    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var visitCardViewLabel:TextView =  itemView.findViewById(R.id.visitCardViewLabel)
        var visitDocNameCardView:TextView = itemView.findViewById(R.id.visitDocNameCardView)
        var visitDocSurnameCardView:TextView = itemView.findViewById(R.id.visitDocSurnameCardView)
        var visitDocSpecCardView:TextView = itemView.findViewById(R.id.visitDocSpecCardView)
        var visitDateCardView:TextView = itemView.findViewById(R.id.visitDateCardView)
        var visitTimeCardView:TextView = itemView.findViewById(R.id.visitTimeCardView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.visits_card_view,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.visitCardViewLabel.text = visitId[position]
        holder.visitDocNameCardView.text = visitDoctorName[position]
        holder.visitDocSurnameCardView.text = visitDoctorSurname[position]
        holder.visitDocSpecCardView.text = visitDoctorSpec[position]
        holder.visitDateCardView.text = visitDate[position]
        holder.visitTimeCardView.text = visitTime[position]
    }

    override fun getItemCount(): Int {
        return visitId.size
    }

}
