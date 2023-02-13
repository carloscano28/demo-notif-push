package com.example.demonotifpush.servicios

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.demonotifpush.MainActivity
import com.example.demonotifpush.NotificationActivity
import com.example.demonotifpush.R
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val CHANNEL_NAME= "Notificaciones urgentes"
    private val CHANNEL_DESCRIPTION= " ACA LLEGAN LAS NOTIFICACIONES URGENTES"
    private val CHANNEL_ID= "CHANNEL_PROMOCIONES"
    lateinit var manager : NotificationManager


    override fun onNewToken(token: String) {
        //super.onNewToken(token)
        Log.d("token", "token = $token")
    }


    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        //super.onMessageReceived(remoteMessage)
        manager= getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        //Log.d("mensaje", "FROM: ${remoteMessage.from}")


        remoteMessage.notification?.let {
            createNotificationChannel()

            if (remoteMessage.data.isNotEmpty()) {

                createNotification(it.title.toString(), it.body.toString(), remoteMessage.data)
                Log.d ("mensaje", "data ${remoteMessage.data}")


            }else{
                createNotification(it.title.toString(), it.body.toString())
            }

        }

    }


    fun createNotificationChannel() {

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val name        = CHANNEL_NAME
            val descrpText  = CHANNEL_DESCRIPTION
            val importancia = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importancia).apply {
                description = descrpText
            }

            manager.createNotificationChannel(channel)
        }
    }


    fun createNotification(title:String, contenido: String, datos: Map<String,String>? = null){

        val intent = Intent(this, NotificationActivity::class.java)

        if (datos!=null){
            if(datos.size>0){

                datos.forEach {
                    intent.putExtra(it.key, it.value)
                }

            }

        }

        intent.putExtra("titulo" , title)
        intent.putExtra("contenido", contenido)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent= PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        val builder= NotificationCompat.Builder(this,CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(title)
            .setContentText(contenido)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        manager.notify(0,builder.build())

    }

}