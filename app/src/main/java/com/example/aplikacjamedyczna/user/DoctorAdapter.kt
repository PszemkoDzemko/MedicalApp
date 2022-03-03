package com.example.aplikacjamedyczna.user

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.doctor.DoctorArrayList
import java.util.*
import kotlin.collections.ArrayList


class DoctorAdapter: RecyclerView.Adapter<DoctorAdapter.MyViewHolder>(), Filterable {
    private lateinit var doctorArrayList: DoctorArrayList
    private lateinit var doctorArrayListFiltered: DoctorArrayList
    private lateinit var view:View

    fun setNewDoctorList(docArrayList: DoctorArrayList){
        doctorArrayList = docArrayList
        doctorArrayListFiltered = docArrayList
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
        holder.idDoctor.text=doctorArrayList.id[position]
        holder.nameDoctor.text=doctorArrayList.name[position]
        holder.surnameDoctor.text=doctorArrayList.surname[position]
        holder.specializtionDoctor.text=doctorArrayList.specialization[position]
        //Log.e("holder", doctor_idFiltered[position])
        holder.mainLayout.setOnClickListener {
            view.context
            Intent()
                    val activity = view.context as AppCompatActivity
                    val myFragment = DoctorDescriptionFragment(doctorArrayList.id[position])
                    activity.supportFragmentManager.beginTransaction().replace(R.id.flFragment, myFragment).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int {
        return doctorArrayList.name.size
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            var filterResult = FilterResults()
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                if (charSequence.isEmpty()){
                    filterResult.values = doctorArrayList.name
                }else{
                    val doctorModal = ArrayList<String>()
                    for(item in doctorArrayList.name){
                        if(item.lowercase(Locale.getDefault()).contains(charSequence.toString())||item.contains(charSequence.toString())) {
                            doctorModal.add(item)
                        }
                        filterResult.count = doctorModal.size
                        filterResult.values = doctorModal
                    }
                }
                return filterResult
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                doctorArrayListFiltered.name = p1!!.values as ArrayList<String>
                //Log.e("size","${filterResult.count}")
                //Log.e("doctor","$doctorArrayList")
                notifyDataSetChanged()
                //chuj wie co tu trzeba zrobić żeby to wyszukiwanie zmieniało te cardview
                //w pizde jebane jedyne co robi to błędy napierdala
                //kurwi się to wszystko jak nie powiem czyja stara zapierdlaa a lal alalalaladA
                //pewnie można zrobić nową klase do niej przekazywać nowe parametry i nią wywoływać
                //tą klasę wyżej ale nie wiem na ilę by to działało i nie chcę mi się tego pisać bo pewnie by nie dziłało
            }
        }
    }
}