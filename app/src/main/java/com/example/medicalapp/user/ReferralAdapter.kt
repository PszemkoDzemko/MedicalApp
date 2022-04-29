package com.example.medicalapp.user

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalapp.FirebaseRepository
import com.example.medicalapp.R
import com.example.medicalapp.data.Referral
import com.example.medicalapp.visits.NewVisitFragment
import com.google.firebase.firestore.FirebaseFirestore

class ReferralAdapter(viewLifecycleOwner: LifecycleOwner,act: FragmentActivity?): RecyclerView.Adapter<ReferralAdapter.ReferralViewHolder>() {

    private val activity = act
    private val repository = FirebaseRepository()
    private val prescriptionList = ArrayList<Referral>()
    private val lifecycle = viewLifecycleOwner
    private val database = FirebaseFirestore.getInstance()
    @SuppressLint("NotifyDataSetChanged")
    fun setPrescription(list:List<Referral>){
        prescriptionList.clear()
        prescriptionList.addAll(list)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReferralViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.referral_card_view,parent,false)
        return ReferralViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReferralViewHolder, position: Int) {
        bindData(holder)
    }

    private fun bindData(holder: ReferralViewHolder) {
        val patNS = holder.itemView.findViewById<TextView>(R.id.referralPatNSCardViewTextView)
        val docNS = holder.itemView.findViewById<TextView>(R.id.referralDocNSCardViewTextView)
        val reason = holder.itemView.findViewById<TextView>(R.id.referralReasonCardViewTextView)
        val data = holder.itemView.findViewById<TextView>(R.id.referralDataCardViewTextView)
        val info = holder.itemView.findViewById<TextView>(R.id.referralInfoCardViewTextView)
        val doneButton = holder.itemView.findViewById<Button>(R.id.doneRefferalCardViewbutton)
        val iddoc = prescriptionList[holder.adapterPosition].id_doc.toString()
        val doc = repository.getDoctorDataById(iddoc)
        doc.observe(lifecycle) { list ->
            docNS.text = list.name+" "+list.surname
        }
        val pac = repository.getUserData()
        pac.observe(lifecycle){list->
            patNS.text = list.name+" "+list.surname
        }
        doneButton.setOnClickListener {
            database.collection("referral").document(prescriptionList[holder.adapterPosition].id.toString()).update("done",true)
            val myFragment = NewVisitFragment()
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                ?.addToBackStack(null)
                ?.commit()
        }
        reason.text = prescriptionList[holder.adapterPosition].reason
        data.text = prescriptionList[holder.adapterPosition].data
        info.text = prescriptionList[holder.adapterPosition].info
    }


    override fun getItemCount(): Int {
        return prescriptionList.size
    }

    inner class ReferralViewHolder(view: View): RecyclerView.ViewHolder(view) {
    }
}
