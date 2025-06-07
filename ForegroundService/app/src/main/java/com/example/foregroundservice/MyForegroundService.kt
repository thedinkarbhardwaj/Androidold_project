package com.example.foregroundservice

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_IMMUTABLE
import android.app.Service
import android.content.Intent
import android.content.pm.ServiceInfo
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.ServiceCompat

class MyForegroundService : Service() {

    private var mediaPlayer: MediaPlayer? = null

    var notification: Notification?=null

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()


    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
//        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
//            .setContentTitle("Foreground Service")
//            .setContentText("Running in the background")
//            .setSmallIcon(android.R.drawable.ic_dialog_info)
//            .setPriority(NotificationCompat.PRIORITY_LOW)
//            .build()
//
//        startForeground(1, notification)
//
//        // Perform the background task
//        doBackgroundWork()

//        mediaPlayer = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI) // Replace with your audio file
//        mediaPlayer?.isLooping = true // Loop the audio if required
//        mediaPlayer?.start()


            prepareForegroundNotification()


            return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun doBackgroundWork() {
        // Add your background task logic here
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Foreground Service Channel",
                NotificationManager.IMPORTANCE_LOW
            )
            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // Clean up resources if needed


//        mediaPlayer?.stop()
//        mediaPlayer?.release()
//        mediaPlayer = null
    }



    fun prepareForegroundNotification() {

        Log.d("LocUpdteServices", "Entered prepareForegroundNotification")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                "12",
                "Location Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = getSystemService(
                NotificationManager::class.java
            )
            manager.createNotificationChannel(serviceChannel)
        }
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            SERVICE_LOCATION_REQUEST_CODE,
            notificationIntent, FLAG_IMMUTABLE
        )

        try {
            notification = NotificationCompat.Builder(this, "12")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setSilent(true)
                .setPriority(NotificationManager.IMPORTANCE_DEFAULT)//IMPORTANCE_NONE
                .build()
            startForeground(LOCATION_SERVICE_NOTIF_ID, notification)
        }catch (e:Exception)
        {
            Log.e("error: ", ""+e.printStackTrace())
            // startForeground(Constants.LOCATION_SERVICE_NOTIF_ID, notification)
        }

        Log.d("LocUpdteServices", "Exiting prepareForegroundNotification")

        val notificationId = 12 // Use a non-zero unique ID for the notification.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            notification?.let {
                ServiceCompat.startForeground(
                    this, // Pass your Service instance
                    notificationId, // Unique notification ID
                    it, // Notification object
                    ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION // Service type
                )
            }
        } else {
            startForeground(notificationId, notification)
        }


    }


    companion object {
        const val CHANNEL_ID = "ForegroundServiceChannel"
        val SERVICE_LOCATION_REQUEST_CODE: Int=12
        val LOCATION_SERVICE_NOTIF_ID: Int=22


    }
}
