package com.example.videoview2

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView

class MainActivity : AppCompatActivity() {

    // on below line we are creating a variable.
    lateinit var videoView: VideoView
   // val videoUrl = "https://www.learningcontainer.com/wp-content/uploads/2020/05/sample-mp4-file.mp4"
   // val videoUrl = "https://media.geeksforgeeks.org/wp-content/uploads/20201217192146/Screenrecorder-2020-12-17-19-17-36-828.mp4?_=1%201"
   // val videoUrl = "https:\\/\\/phpstack-102119-3423473.cloudwaysapps.com\\/storage\\/supportvideo\\/\\/1681303466mp4"
    val videoUrl = "https://www.youtube.com/watch?v=qAHMCZBwYo4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // on below line we are initializing our variables.
        videoView = findViewById(R.id.idVideoView)

        // Uri object to refer the
        // resource from the videoUrl
        val uri = Uri.parse(videoUrl)

        // sets the resource from the
        // videoUrl to the videoView
        videoView.setVideoURI(uri)

        // creating object of
        // media controller class
        val mediaController = MediaController(this)

        // sets the anchor view
        // anchor view for the videoView
        mediaController.setAnchorView(videoView)

        // sets the media player to the videoView
        mediaController.setMediaPlayer(videoView)

        // sets the media controller to the videoView
        videoView.setMediaController(mediaController);

        // starts the video
        videoView.start();
    }
}