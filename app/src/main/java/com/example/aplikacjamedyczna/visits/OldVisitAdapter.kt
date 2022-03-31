package com.example.aplikacjamedyczna.visits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Visits
import com.google.firebase.firestore.FirebaseFirestore

class OldVisitAdapter(viewLifecycleOwner: LifecycleOwner, act:FragmentActivity?):RecyclerView.Adapter<OldVisitAdapter.VisitViewHolder>() {

    private val activity = act
    private val repository = FirebaseRepository()
    private val visitList = ArrayList<Visits>()
    private val lifecycle = viewLifecycleOwner
    private val database = FirebaseFirestore.getInstance()
    @SuppressLint("NotifyDataSetChanged")
    fun setVisits(list: List<Visits>){
        visitList.clear()
        visitList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OldVisitAdapter.VisitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.old_visits_card_view,parent,false)
        return VisitViewHolder(view)
    }

    override fun onBindViewHolder(holder: OldVisitAdapter.VisitViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: VisitViewHolder) {
        val docName = holder.itemView.findViewById<TextView>(R.id.oldVisitDocNameCardView)
        val docSurname = holder.itemView.findViewById<TextView>(R.id.oldVisitDocSurnameCardView)
        val docSpec = holder.itemView.findViewById<TextView>(R.id.oldVisitDocSpecCardView)
        val time = holder.itemView.findViewById<TextView>(R.id.oldVisitTimeCardView)
        val data = holder.itemView.findViewById<TextView>(R.id.oldVisitDateCardView)
        val deleteButton = holder.itemView.findViewById<TextView>(R.id.oldVisitDeleteButton)
        val idDoc = visitList[holder.adapterPosition].id_doc.toString()
        val doc = repository.getDoctorDataById(idDoc)
        doc.observe(lifecycle) { list ->
            docName.text = list.name
            docSurname.text = list.surname
            docSpec.text = list.specialization
        }
        time.text = visitList[holder.adapterPosition].hour
        data.text = visitList[holder.adapterPosition].data
        deleteButton.setOnClickListener {
            database.collection("visits").document(visitList[holder.adapterPosition].id.toString()).delete()
            val myFragment = OldVisitFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun getItemCount(): Int {
        return visitList.size
    }

    inner class VisitViewHolder(view: View): RecyclerView.ViewHolder(view){
    }
}