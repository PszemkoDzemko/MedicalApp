package com.example.aplikacjamedyczna.visits

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.aplikacjamedyczna.FirebaseRepository
import com.example.aplikacjamedyczna.R
import com.example.aplikacjamedyczna.data.Visits
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class NewVisitAdapter(viewLifecycleOwner: LifecycleOwner, act: FragmentActivity?):RecyclerView.Adapter<NewVisitAdapter.VisitViewHolder>()  {

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
        val doneButton = holder.itemView.findViewById<Button>(R.id.newVisitDoneCardViewButton)
        val deleteButton = holder.itemView.findViewById<Button>(R.id.newVisitDeleteCardViewButton)
        val timeChangeButton = holder.itemView.findViewById<Button>(R.id.newVisitTimeCardViewButton)
        val dataChangeButton = holder.itemView.findViewById<Button>(R.id.newVisitDateCardViewButton)
        val idDoc = visitList[holder.adapterPosition].id_doc.toString()
        val doc = repository.getDoctorDataById(idDoc)
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val hour = myCalendar.get(Calendar.HOUR_OF_DAY)
        val minute = myCalendar.get(Calendar.MINUTE)
        var myHour = 0
        var myMinute = 0
        var myYear = 0
        var myMonth = 0
        var myDay = 0
        var allData = ""
        var allTime = ""
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
        dataChangeButton.setOnClickListener {

            val dpd = activity?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                        myYear = selectedYear
                        myMonth = selectedMonth+1
                        myDay = selectedDayOfMonth
                        allData = "$myYear/$myMonth/$myDay"
                        database.collection("visits").document(visitList[holder.adapterPosition].id.toString()).update("data",allData)
                        val myFragment = NewVisitsFragment()
                        activity.supportFragmentManager.beginTransaction()
                            .replace(R.id.flFragment, myFragment)
                            .addToBackStack(null)
                            .commit()
                    },
                    year,
                    month,
                    day
                ).show()
            }
        }
        timeChangeButton.setOnClickListener {
            val tpd = TimePickerDialog(
                activity,
                { view, selectedHour, selectedMin->
                    myHour = selectedHour
                    myMinute = selectedMin
                    allTime = "$myHour:$myMinute"
                    database.collection("visits").document(visitList[holder.adapterPosition].id.toString()).update("hour",allTime)
                    val myFragment = NewVisitsFragment()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                }
                ,hour,minute,true).show()
        }

    }

    override fun getItemCount(): Int {
        return visitList.size
    }

    inner class VisitViewHolder(view:View):RecyclerView.ViewHolder(view){
    }
}
