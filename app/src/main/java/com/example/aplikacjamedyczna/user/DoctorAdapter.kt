package com.example.aplikacjamedyczna.user

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.DoctorDescriptionFragment
import com.example.aplikacjamedyczna.R


class DoctorAdapter: RecyclerView.Adapter<DoctorAdapter.MyViewHolder>(), Filterable {
    private lateinit var doctor_id:ArrayList<String>
    private lateinit var doctor_idFiltered:ArrayList<String>
    private lateinit var doctor_name:ArrayList<String>
    private lateinit var doctor_nameFiltered:ArrayList<String>
    private lateinit var doctor_surname:ArrayList<String>
    private lateinit var doctor_surnameFiltered:ArrayList<String>
    private lateinit var doctor_specialization:ArrayList<String>
    private lateinit var doctor_specializationFiltered:ArrayList<String>
    private lateinit var view:View

    fun setDoctorList(doc_id:ArrayList<String>, doc_name:ArrayList<String>, doc_surname: ArrayList<String>, doc_specialization: ArrayList<String>) {
        doctor_id = doc_id
        doctor_idFiltered = doc_id
        doctor_name = doc_name
        doctor_nameFiltered  = doc_name
        doctor_surname = doc_surname
        doctor_surnameFiltered = doc_surname
        doctor_specialization = doc_specialization
        doctor_specializationFiltered = doc_specialization
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var nameDoctor:TextView  = itemView.findViewById(R.id.nameCardDoctor)
        var surnameDoctor:TextView  = itemView.findViewById(R.id.surnameCardDoctor)
        var specializtionDoctor:TextView = itemView.findViewById(R.id.specCardDoctor)
        var idDoctor:TextView = itemView.findViewById(R.id.descCardDoctor)
        var mainLayout:LinearLayout = itemView.findViewById(R.id.mainLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater:LayoutInflater= LayoutInflater.from(parent.context)
        view = inflater.inflate(R.layout.doctors_card_view,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.idDoctor.text=doctor_id[position]
        holder.nameDoctor.text=doctor_name[position]
        holder.surnameDoctor.text=doctor_surname[position]
        holder.specializtionDoctor.text=doctor_specialization[position]
        holder.mainLayout.setOnClickListener {
            view.context
            Intent()
                    val activity = view.context as AppCompatActivity
                    val myFragment = DoctorDescriptionFragment(doctor_id[position])
                    activity.supportFragmentManager.beginTransaction().replace(R.id.flFragment, myFragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return doctor_id.size
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            var filterResult = Filter.FilterResults()
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                if (charSequence.isEmpty()){
                    doctor_nameFiltered = doctor_name
                }else{
                    var doctorModal = ArrayList<String>()
                    for(item in doctor_name){
                        if(item.contains(charSequence.toString())) {
                            doctorModal.add(item)
                            //Log.e("Tag","$doctorModal")
                        }
                        filterResult.count = doctorModal.size
                        filterResult.values = doctorModal
                    }
                }
                return filterResult
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
               // doctor_nameFiltered = p1!!.values as ArrayList<String>
                //Log.e("size","${filterResult.count}")
                //Log.e("doctor","$doctor_nameFiltered")
                //notifyDataSetChanged()
                //chuj wie co tu trzeba zrobić żeby to wyszukiwanie zmieniało te cardview
                //w pizde jebane jedyne co robi to błędy napierdala
                //kurwi się to wszystko jak nie powiem czyja stara zapierdlaa a lal alalalaladA
                //pewnie można zrobić nową klase do niej przekazywać nowe parametry i nią wywoływać
                //tą klasę wyżej ale nie wiem na ilę by to działało i nie chcę mi się tego pisać bo pewnie by nie dziłało
            }
        }
    }
}