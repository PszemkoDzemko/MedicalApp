package com.example.aplikacjamedyczna.visits

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.MainActivity
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Visits
import com.example.aplikacjamedyczna.doctor.DoctorDescriptionFragment
import com.google.firebase.firestore.FirebaseFirestore

class NewVisitAdapter(viewLifecycleOwner: LifecycleOwner,acti: FragmentActivity?):RecyclerView.Adapter<NewVisitAdapter.VisitViewHolder>()  {

    private val activity = acti
    private val respository = FirebaseRepository()
    private val visitList = ArrayList<Visits>()
    private val lifecycle = viewLifecycleOwner
    private val database = FirebaseFirestore.getInstance()
    @SuppressLint("NotifyDataSetChanged")
    fun setVisits(list: List<Visits>){
        visitList.clear()
        visitList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewVisitAdapter.VisitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.new_visits_card_view,parent,false)
        return VisitViewHolder(view)
    }

    override fun onBindViewHolder(holder: NewVisitAdapter.VisitViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: VisitViewHolder) {
        val docName = holder.itemView.findViewById<TextView>(R.id.newVisitDocNameCardView)
        val docSurname = holder.itemView.findViewById<TextView>(R.id.newVisitDocSurnameCardView)
        val docSpec = holder.itemView.findViewById<TextView>(R.id.newVisitDocSpecCardView)
        val time = holder.itemView.findViewById<TextView>(R.id.newVisitTimeCardView)
        val data = holder.itemView.findViewById<TextView>(R.id.newVisitDateCardView)
        val doneButton = holder.itemView.findViewById<Button>(R.id.newVisitDoneButton)
        val deleteButton = holder.itemView.findViewById<Button>(R.id.newVisitDeleteButton)
        val idDoc = visitList[holder.adapterPosition].id_doc.toString()
        val doc = respository.getDoctorDataById(idDoc)
        doc.observe(lifecycle) { list ->
            docName.text = list.name
            docSurname.text = list.surname
            docSpec.text = list.specialization
        }
        time.text = visitList[holder.adapterPosition].hour
        data.text = visitList[holder.adapterPosition].data
        doneButton.setOnClickListener {
            database.collection("visits").document(visitList[holder.adapterPosition].id.toString()).update("done",true)
            val myFragment = NewVisitsFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }
        deleteButton.setOnClickListener {
            database.collection("visits").document(visitList[holder.adapterPosition].id.toString()).delete()
            val myFragment = NewVisitsFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }
    }

    override fun getItemCount(): Int {
        return visitList.size
    }

    inner class VisitViewHolder(view:View):RecyclerView.ViewHolder(view){
    }
}
