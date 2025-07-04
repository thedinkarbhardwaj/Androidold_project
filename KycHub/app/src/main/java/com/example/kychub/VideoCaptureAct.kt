package com.example.kychub

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.MediaController
import android.widget.VideoView

class VideoCaptureAct : AppCompatActivity() {

    var videoRequestCode = 100
    var videoview:VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_capture)

        var videoRecordBtn = findViewById<Button>(R.id.videoRecordbtn)
         videoview = findViewById<VideoView>(R.id.videoview)

        videoRecordBtn.setOnClickListener{

            var intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            startActivityForResult(intent,videoRequestCode)

        }

        var mediaController = MediaController(this)
        mediaController.setAnchorView(videoview)
        videoview?.setMediaController(mediaController)


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == videoRequestCode && resultCode == RESULT_OK ){

            var videoUri = data?.data

            videoview?.setVideoURI(videoUri)
            videoview?.start()
        }
    }
}