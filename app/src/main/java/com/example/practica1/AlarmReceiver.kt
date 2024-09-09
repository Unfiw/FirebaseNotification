package com.example.practica1

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("AlarmReceiver", "Alarm received!")

        context?.let {
            val registroIntent = Intent(it, RegistroActivity::class.java)
            registroIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP

            val notificationManager = it.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val channelId = "alarm_channel"

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                val channel = NotificationChannel(channelId, "Alarm Notifications", NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }

            val pendingIntent = PendingIntent.getActivity(it, 0, registroIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)

            val notification = NotificationCompat.Builder(it, channelId)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Ir a registrar Medidas")
                .setContentText("Ven yaaa!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            notificationManager.notify(1, notification)
            Log.d("AlarmReceiver", "Notification sent")
        } ?: run {
            Log.e("AlarmReceiver", "Context is null, cannot send notification")
        }
    }
}
