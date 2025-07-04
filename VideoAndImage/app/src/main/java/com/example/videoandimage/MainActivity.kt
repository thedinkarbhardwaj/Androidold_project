package com.example.videoandimage

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    var runAutomate = true

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var viewPager = findViewById<ViewPager2>(R.id.viewPager)
        val videoItems = createDummyData()

        val adapter = VideoPagerAdapter(this,videoItems)
        viewPager.adapter = adapter



        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {

                if(runAutomate){
                    val nextPosition = (viewPager.currentItem + 1) % videoItems.size
                    viewPager.currentItem = nextPosition
                }

                // Calculate the next position or page index

                // Schedule the next automatic page change after 5 seconds
                handler.postDelayed(this, 5000)
            }
        }

        // Start the automatic page change after a delay (e.g., in onCreate or onStart)
        handler.postDelayed(runnable, 5000)


        var btn = findViewById<Button>(R.id.stop)
        btn.setOnClickListener {

            if (runAutomate){
                btn.text = "Start"
                runAutomate = false

            }else{
                btn.text = "Stop"
                runAutomate = true


            }

        }
    }

    private fun createDummyData(): List<VideoItem> {
        return listOf(
            VideoItem("Video 1", "video", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4", "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg"),
            VideoItem("Video 2", "img", "https://www.gstatic.com/webp/gallery3/1.sm.png", "https://www.gstatic.com/webp/gallery3/1.sm.png"),
            VideoItem("Img 3", "img", "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg", "https://res.cloudinary.com/demo/image/upload/v1312461204/sample.jpg"),
            // Add more items as needed
        )
    }

    companion object {
        private const val LONG_PRESS_DURATION = 1000L  // Adjust the duration as needed
    }
}