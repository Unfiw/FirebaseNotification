package com.example.practica1

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import android.Manifest
import android.app.AlarmManager
import android.os.SystemClock
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class PrincipalActivity : AppCompatActivity() {

    private lateinit var alarmMgr: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private lateinit var txtUsuario: EditText
    private lateinit var txtCorreo: EditText
    private lateinit var txtPhone: EditText
    private lateinit var btnRegistrar: Button

    private val REQUEST_CODE_POST_NOTIFICATION = 1001



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_principal)

        txtUsuario = findViewById(R.id.txtUsuario)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtPhone = findViewById(R.id.txtPhone)
        btnRegistrar = findViewById(R.id.button2)

        alarmMgr = getSystemService(ALARM_SERVICE) as AlarmManager

        val intent = Intent(this, AlarmReceiver::class.java)
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Measurement Notifications"
            val descriptionText = "Notifications about your measurements"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("MEASUREMENT_CHANNEL", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }

        checkNotificationPermission()

        btnRegistrar.setOnClickListener {
            setAlarm()
        }
    }

    private fun setAlarm() {
        alarmMgr.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 10 * 1000,
            pendingIntent
        )
        Toast.makeText(this, "la alarma sonar en 10 segundos a apartir de ahora", Toast.LENGTH_SHORT).show()
    }

    private fun checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS)) {
                    AlertDialog.Builder(this)
                        .setTitle("Permiso necesario")
                        .setMessage("La aplicación necesita permiso para enviar notificaciones.")
                        .setPositiveButton("Aceptar") { _, _ ->
                            requestNotificationPermission()
                        }
                        .setNegativeButton("Cancelar", null)
                        .show()
                } else {
                    requestNotificationPermission()
                }
            }
        }
    }

    private fun requestNotificationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.POST_NOTIFICATIONS),
            REQUEST_CODE_POST_NOTIFICATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_POST_NOTIFICATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permiso de notificaciones concedido", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permiso de notificaciones denegado", Toast.LENGTH_SHORT).show()
            }
        }
    }
}