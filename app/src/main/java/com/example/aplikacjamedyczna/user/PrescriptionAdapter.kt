package com.example.aplikacjamedyczna.user

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
import com.example.aplikacjamedyczna.data.Prescription
import com.google.firebase.firestore.FirebaseFirestore

class PrescriptionAdapter(viewLifecycleOwner: LifecycleOwner, act: FragmentActivity?): RecyclerView.Adapter<PrescriptionAdapter.PrescriptionViewHolder>() {

    private val repository = FirebaseRepository()
    private val prescriptionList = ArrayList<Prescription>()
    private val lifecycle = viewLifecycleOwner

    @SuppressLint("NotifyDataSetChanged")
    fun setPrescription(list:List<Prescription>){
        prescriptionList.clear()
        prescriptionList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PrescriptionViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.prescription_card_view,parent,false)
        return PrescriptionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PrescriptionViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: PrescriptionViewHolder) {
        val patNS = holder.itemView.findViewById<TextView>(R.id.prescPatNSCardViewTextView)
        val docNS = holder.itemView.findViewById<TextView>(R.id.prescDocNSCardViewTextView)
        val code = holder.itemView.findViewById<TextView>(R.id.prescCodeCardViewTextView)
        val data = holder.itemView.findViewById<TextView>(R.id.prescDataCardViewTextView)
        val details = holder.itemView.findViewById<TextView>(R.id.prescDetailsCardViewTextView)
        val iddoc = prescriptionList[holder.adapterPosition].id_doc.toString()
        val doc = repository.getDoctorDataById(iddoc)
        doc.observe(lifecycle) { list ->
            docNS.text = list.name+" "+list.surname
        }
        val pac = repository.getUserData()
        pac.observe(lifecycle){list->
            patNS.text = list.name+" "+list.surname
        }
        code.text = prescriptionList[holder.adapterPosition].code
        data.text = prescriptionList[holder.adapterPosition].data
        details.text = prescriptionList[holder.adapterPosition].details
    }


    override fun getItemCount(): Int {
        return prescriptionList.size
    }

    inner class PrescriptionViewHolder(view: View):RecyclerView.ViewHolder(view) {
    }
}
