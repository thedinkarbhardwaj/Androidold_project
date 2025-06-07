package com.example.overlaybutton

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.core.app.NotificationCompat

class FloatingViewService : Service() {

    private var floatingView: View? = null
    private var windowManager: WindowManager? = null
    private var params: WindowManager.LayoutParams? = null
    private var initialX = 0
    private var initialY = 0
    private var initialTouchX = 0f
    private var initialTouchY = 0f
    private var isDragging = false

    private var touchStartTime: Long = 0

    val CLICK_DRAG_TOLERANCE = 10f


    override fun onCreate() {
        super.onCreate()

        // Inflate the floating button layout
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_button, null)

        // Set up the window parameters for the floating button
        params = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        )

        // Get the window manager service
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager

        // Set a click listener on the floating button
//        val floatingButton: ImageView = floatingView!!.findViewById(R.id.floating_button)
        val floatingView: LinearLayout = floatingView!!.findViewById(R.id.layy)

// Set the click listener on the entire LinearLayout (not just the ImageView)
        floatingView.setOnClickListener {
            // Launch the main activity when the floating view is clicked
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

// Set the onTouchListener for the floating view to make it draggable
        floatingView?.setOnTouchListener { v, event ->
            val params = v.layoutParams as WindowManager.LayoutParams

            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    // Record the initial position of the touch
                    initialX = params.x
                    initialY = params.y
                    initialTouchX = event.rawX
                    initialTouchY = event.rawY
                }
                MotionEvent.ACTION_MOVE -> {
                    // Calculate the new position of the floating view
                    val dx = (event.rawX - initialTouchX).toInt()
                    val dy = (event.rawY - initialTouchY).toInt()

                    // Check if the drag distance is large enough
                    val dragDistance = Math.sqrt((dx * dx + dy * dy).toDouble())
                    if (dragDistance > CLICK_DRAG_TOLERANCE) {
                        // Update the position of the view
                        params.x = initialX + dx
                        params.y = initialY + dy

                        // Update the layout with the new position
                        windowManager?.updateViewLayout(v, params)
                    }
                }
                MotionEvent.ACTION_UP -> {
                    // Check if the drag distance is small enough to be considered a click
                    val upDX = event.rawX - initialTouchX
                    val upDY = event.rawY - initialTouchY
                    val dragDistance = Math.sqrt((upDX * upDX + upDY * upDY).toDouble())
                    if (dragDistance < CLICK_DRAG_TOLERANCE) {
                        // Trigger click
                        v.performClick()
                    }
                }
                else -> return@setOnTouchListener false
            }

            true
        }

// Define the tolerance value for detecting a click vs. a drag



    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val isAppInForeground = intent?.getBooleanExtra("app_in_foreground", false) ?: false

        if (isAppInForeground) {
            // Hide the floating button when the app is in the foreground
            hideFloatingButton()
        } else {
            // Show the floating button when the app is in the background
            showFloatingButton()
        }

        return START_STICKY
    }

    private fun showFloatingButton() {

        try {

            // Check if the floating view is already added
            if (floatingView != null && floatingView!!.parent == null) {
                windowManager?.addView(floatingView, params)
            }

        }catch (e:Exception){

        }
    }

    private fun hideFloatingButton() {
        try {
            // Remove the floating button from the window
            if (floatingView != null && floatingView!!.parent != null) {
                windowManager?.removeView(floatingView)
            }
        }catch (e:Exception){

        }
    }

    override fun onDestroy() {
        super.onDestroy()

        // Remove the floating button from the window when the service is destroyed
        if (floatingView != null && floatingView!!.parent != null) {
            windowManager?.removeView(floatingView)
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}


