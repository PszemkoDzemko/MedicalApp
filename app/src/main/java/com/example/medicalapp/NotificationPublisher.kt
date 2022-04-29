package com.example.medicalapp

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import kotlin.random.Random

class NotificationPublisher: BroadcastReceiver() {
    private val titleExtra = "titleExtra"
    private val messageExtra = "messageExtra"
    override fun onReceive(context: Context, intent: Intent) {
        val notification = NotificationCompat.Builder(context,"channel1")
            .setSmallIcon(R.mipmap.ic_launcher_foreground)
            .setContentText(intent.getStringExtra(messageExtra))
            .setContentTitle(intent.getStringExtra(titleExtra))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setGroup("Visit")
            .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(Random.nextInt(),notification)
    }
}