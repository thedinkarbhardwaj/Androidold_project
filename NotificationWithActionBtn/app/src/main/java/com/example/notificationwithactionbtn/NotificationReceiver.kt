package com.example.notificationwithactionbtn

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == "ACTION_CLICK") {
            Toast.makeText(context, "Action Button Clicked!", Toast.LENGTH_SHORT).show()

        }
    }
}
