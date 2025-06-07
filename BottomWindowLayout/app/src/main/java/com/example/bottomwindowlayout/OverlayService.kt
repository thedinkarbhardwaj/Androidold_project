package com.example.bottomwindowlayout

import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button

class OverlayService : Service() {

    private var bottomBarView: View? = null

    override fun onCreate() {
        super.onCreate()
        showBottomBarOverlay()
    }

    private fun showBottomBarOverlay() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager

        // Inflate the layout
        val inflater = LayoutInflater.from(this)
        bottomBarView = inflater.inflate(R.layout.overlay_bottom_bar, null)

        // Set layout params for the overlay
        val params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            else
                WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        )
        params.gravity = Gravity.BOTTOM

        // Add the view to the window
        windowManager.addView(bottomBarView, params)

        // Handle interactions (e.g., dismiss button)
        bottomBarView?.findViewById<Button>(R.id.dismiss_button)?.setOnClickListener {
            stopSelf() // Stop the service and remove the overlay
            removeBottomBarOverlay()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
      //  removeBottomBarOverlay()
    }

    private fun removeBottomBarOverlay() {
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        bottomBarView?.let {
            windowManager.removeView(it)
            bottomBarView = null
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}
