package com.example.medicalapp.doctor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalapp.R
import com.example.medicalapp.data.Doctor

class DoctorAdapter(private val listener: OnDoctorItemClick): RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>(),
    Filterable {

    private var doctorsList = ArrayList<Doctor>()
    private val doctorsListFilter = ArrayList<Doctor>()

    fun setDoctors(list: List<Doctor>){
        doctorsList.clear()
        doctorsListFilter.clear()
        doctorsList.addAll(list)
        doctorsListFilter.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.doctors_card_view,parent,false)
        return DoctorViewHolder(view)
    }

    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        val name = holder.itemView.findViewById<TextView>(R.id.nameCardDoctor)
        val surname = holder.itemView.findViewById<TextView>(R.id.surnameCardDoctor)
        val spec = holder.itemView.findViewById<TextView>(R.id.specCardDoctor)
        val rating = holder.itemView.findViewById<RatingBar>(R.id.ratingBar)
        val localization = holder.itemView.findViewById<TextView>(R.id.localizationCardDoctor)
        val rat =(doctorsList[holder.adapterPosition].rating)?.toFloat()
        val nrRat = (doctorsList[holder.adapterPosition].nrRating)?.toFloat()
        if (rat != null) {
            rating.rating = rat / nrRat!!
        }
        rating.setIsIndicator(true)
        name.text = doctorsList[holder.adapterPosition].name
        surname.text = doctorsList[holder.adapterPosition].surname
        spec.text = doctorsList[holder.adapterPosition].specialization
        localization.text = doctorsList[holder.adapterPosition].localization
    }

    override fun getItemCount(): Int {
        return doctorsList.size
    }

    inner class DoctorViewHolder(view: View): RecyclerView.ViewHolder(view) {
        init {
            view.setOnClickListener{
                listener.onDoctorClick(doctorsList[adapterPosition], adapterPosition)
            }
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val filterResult = FilterResults()
                if(charSequence.length<0){
                    filterResult.count = doctorsListFilter.size
                    filterResult.values = doctorsListFilter
                }else{
                    val searchChr = charSequence.toString().lowercase()

                    val data = ArrayList<Doctor>()
                    for(doc in doctorsListFilter){
                        if(doc.specialization!!.lowercase().contains(searchChr) || doc.name!!.lowercase().contains(searchChr)||doc.surname!!.lowercase().contains(searchChr)||doc.localization!!.lowercase().contains(searchChr)){
                            data.add(doc)
                        }
                    }
                    filterResult.count = data.size
                    filterResult.values = data
                }
                return filterResult
            }

            override fun publishResults(constrain: CharSequence?, p1: FilterResults?) {
                doctorsList = p1!!.values as ArrayList<Doctor>
                notifyDataSetChanged()
            }

        }
    }
}
interface OnDoctorItemClick{
    fun onDoctorClick(doctor:Doctor,position: Int)
}