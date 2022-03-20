package com.example.aplikacjamedyczna.visits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Doctor
import com.example.aplikacjamedyczna.data.Visits

class VisitAdapter(viewLifecycleOwner: LifecycleOwner,private val listener: OnVisitItemClick):RecyclerView.Adapter<VisitAdapter.VisitViewHolder>()  {

    private val respository = FirebaseRepository()
    private val visitList = ArrayList<Visits>()
    private val lifecycle = viewLifecycleOwner
    @SuppressLint("NotifyDataSetChanged")
    fun setVisits(list: List<Visits>){
        visitList.clear()
        visitList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitAdapter.VisitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.visits_card_view,parent,false)
        return VisitViewHolder(view)
    }

    override fun onBindViewHolder(holder: VisitAdapter.VisitViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: VisitViewHolder) {
        val docName = holder.itemView.findViewById<TextView>(R.id.visitDocNameCardView)
        val docSurname = holder.itemView.findViewById<TextView>(R.id.visitDocSurnameCardView)
        val docSpec = holder.itemView.findViewById<TextView>(R.id.visitDocSpecCardView)
        val time = holder.itemView.findViewById<TextView>(R.id.visitTimeCardView)
        val data = holder.itemView.findViewById<TextView>(R.id.visitDateCardView)
        val idDoc = visitList[holder.adapterPosition].id_doc.toString()
        val doc = respository.getDoctorDataById(idDoc)
        doc.observe(lifecycle) { list ->
            docName.text = list.name
            docSurname.text = list.surname
            docSpec.text = list.specialization
        }
        time.text = visitList[holder.adapterPosition].hour
        data.text = visitList[holder.adapterPosition].data
    }

    override fun getItemCount(): Int {
        return visitList.size
    }

    inner class VisitViewHolder(view:View):RecyclerView.ViewHolder(view){
        init {
            view.setOnClickListener{
                listener.onVisitClick(visitList[adapterPosition], adapterPosition)
                true
            }
        }
    }
}
interface OnVisitItemClick{
    fun onVisitClick(visits: Visits,position: Int)
}