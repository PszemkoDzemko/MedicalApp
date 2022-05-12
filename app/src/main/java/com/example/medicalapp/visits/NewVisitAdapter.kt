package com.example.medicalapp.visits

import android.annotation.SuppressLint
import android.app.*
import android.content.Context.ALARM_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.medicalapp.FirebaseRepository
import com.example.medicalapp.NotificationPublisher
import com.example.medicalapp.R
import com.example.medicalapp.data.Visits
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import androidx.appcompat.app.AlertDialog
import kotlin.collections.ArrayList
import kotlin.random.Random

class NewVisitAdapter(viewLifecycleOwner: LifecycleOwner, act: FragmentActivity?): RecyclerView.Adapter<NewVisitAdapter.VisitViewHolder>()  {

    private val activity = act
    private val repository = FirebaseRepository()
    private val visitList = ArrayList<Visits>()
    private val lifecycle = viewLifecycleOwner
    private var doctorName:String = ""
    private val database = FirebaseFirestore.getInstance()
    @SuppressLint("NotifyDataSetChanged")
    fun setVisits(list: List<Visits>){
        visitList.clear()
        visitList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewVisitAdapter.VisitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.new_visit_card_view,parent,false)
        return VisitViewHolder(view)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NewVisitAdapter.VisitViewHolder, position: Int) {
        bindData(holder)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun bindData(holder: VisitViewHolder) {
        val idDoc = visitList[holder.adapterPosition].id_doc.toString()
        val doc = repository.getDoctorDataById(idDoc)
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val hour = myCalendar.get(Calendar.HOUR_OF_DAY)
        val minute = myCalendar.get(Calendar.MINUTE)
        var myHour: Int
        var myMinute: Int
        var myYear: Int
        var myMonth: Int
        var myDay: Int
        var allData: String
        var allTime: String
        val nowDate:String
        val nowMonth = month + 1
        nowDate = if(nowMonth<10){
            if(day<10){
                "$year-0$nowMonth-0$day"
            }else {
                "$year-0$nowMonth-$day"
            }
        }else if(day<10){
            "$year-$nowMonth-0$day"
        }else{
            "$year-$nowMonth-$day"
        }
        doc.observe(lifecycle) { list ->
            holder.docName.text = list.name
            holder.docSurname.text = list.surname
            holder.docSpec.text = list.specialization
        }
        holder.time.text = visitList[holder.adapterPosition].hour
        holder.data.text = visitList[holder.adapterPosition].data
        holder.notificationButton.setOnClickListener {
            createNotificationChannel(holder.itemView)
            val intent = Intent(activity!!.applicationContext,NotificationPublisher::class.java)
            intent.putExtra("titleExtra","Wizyta u ${holder.docName.text} ${holder.docSurname.text}")
            intent.putExtra("messageExtra","Masz wizytę u ${holder.docSpec.text} o godzinie ${holder.time.text}")
            val pendingIntent = PendingIntent.getBroadcast(
                activity.applicationContext,
                Random.nextInt(),
                intent,
                PendingIntent.FLAG_IMMUTABLE)
            val timeNow = System.currentTimeMillis()
            val alarmManager = holder.itemView.context.getSystemService(ALARM_SERVICE) as AlarmManager
            val setNotificationTime = LayoutInflater.from(holder.itemView.context).inflate(R.layout.set_notification_time,null)
            val notification30Min: RadioButton = setNotificationTime.findViewById(R.id.notificationSet30Min)
            val notification1Hour: RadioButton = setNotificationTime.findViewById(R.id.notificationSet1Hour)
            val notification3Hour: RadioButton = setNotificationTime.findViewById(R.id.notificationSet3Hour)
            val notification4Hour: RadioButton = setNotificationTime.findViewById(R.id.notificationSet4Hour)
            AlertDialog.Builder(holder.itemView.context)
                .setView(setNotificationTime)
                .setPositiveButton("OK"){
                        dialog, _ ->
                    when {
                        notification30Min.isChecked -> {
                            alarmManager.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                timeNow+1800000,
                                pendingIntent
                            )
                        }
                        notification1Hour.isChecked -> {
                            alarmManager.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                timeNow+3600000,
                                pendingIntent
                            )
                        }
                        notification3Hour.isChecked -> {
                            alarmManager.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                timeNow+10800000,
                                pendingIntent
                            )
                        }
                        notification4Hour.isChecked -> {
                            alarmManager.setExactAndAllowWhileIdle(
                                AlarmManager.RTC_WAKEUP,
                                timeNow+30000,
                                pendingIntent
                            )
                        }
                    }
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel){
                    dialog,_ ->
                    dialog.dismiss()
                }
                .setTitle(R.string.choose_when_notify)
                .create()
                .show()
        }
        holder.deleteButton.setOnClickListener {
            val sureToDelete = LayoutInflater.from(holder.itemView.context).inflate(R.layout.sure_to_delete,null)
            AlertDialog.Builder(holder.itemView.context)
                .setView(sureToDelete)
                .setPositiveButton("OK"){
                        dialog, _ ->
                    database.collection("visits").document(visitList[holder.adapterPosition].id.toString()).delete()
                    val myFragment = NewVisitFragment()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                    dialog.dismiss()
                }
                .setNegativeButton(R.string.cancel){
                        dialog,_ ->
                    dialog.dismiss()
                }
                .create()
                .show()
        }
        holder.dataChangeButton.setOnClickListener {
            activity?.let { it1 ->
                DatePickerDialog(
                    it1,
                    { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                        myYear = selectedYear
                        myMonth = selectedMonth+1
                        myDay = selectedDayOfMonth
                        allData = "$myYear-$myMonth-$myDay"
                        database.collection("visits").document(visitList[holder.adapterPosition].id.toString()).update("data",allData)
                        val myFragment = NewVisitFragment()
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
        holder.timeChangeButton.setOnClickListener {
            TimePickerDialog(
                holder.itemView.context,
                { _, selectedHour, selectedMin->
                    myHour = selectedHour
                    myMinute = selectedMin
                    allTime = "$myHour:$myMinute"
                    database.collection("visits").document(visitList[holder.adapterPosition].id.toString()).update("hour",allTime)
                    val myFragment = NewVisitFragment()
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.flFragment, myFragment)
                        ?.addToBackStack(null)
                        ?.commit()
                }
                ,hour,minute,true).show()
        }

    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(view: View){
        val name= "Notification Channel"
        val desc = "A Description of the Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("channel1",name,importance)
        channel.description = desc
        val notificationManager = view.context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }


    override fun getItemCount(): Int {
        return visitList.size
    }

    inner class VisitViewHolder(view: View): RecyclerView.ViewHolder(view){
        val docName: TextView = view.findViewById(R.id.newVisitDocNameCardView)
        val docSurname:TextView = view.findViewById(R.id.newVisitDocSurnameCardView)
        val docSpec:TextView = view.findViewById(R.id.newVisitDocSpecCardView)
        val time:TextView = view.findViewById(R.id.newVisitTimeCardView)
        val data:TextView = view.findViewById(R.id.newVisitDateCardView)
        val deleteButton:Button = view.findViewById(R.id.newVisitDeleteCardViewButton)
        val timeChangeButton:Button = view.findViewById(R.id.newVisitTimeCardViewButton)
        val dataChangeButton:Button = view.findViewById(R.id.newVisitDateCardViewButton)
        val notificationButton:Button = view.findViewById(R.id.notificationSetButton)
    }

}
