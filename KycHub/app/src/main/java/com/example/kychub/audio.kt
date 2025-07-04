package com.example.kychub

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.IOException

class audio : AppCompatActivity() {

    var MICRPPHONE_PERMISSION_CODE = 200

    var mediaRecorder:MediaRecorder? = null

    var mediaPlayer:MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_audio)

        if (isMicrophonepresent()){
            getMicroPermission()
        }
    }

    fun btnRecord(view: View) {


        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
      //  mediaRecorder?.setOutputFile(outputFile)
        mediaRecorder?.setOutputFile(getRecordingFilePath())
        mediaRecorder?.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)


        try {
            mediaRecorder?.prepare()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        mediaRecorder?.start()

        Toast.makeText(this,"Rcord is start",Toast.LENGTH_LONG).show()


    }


    fun btnStop(view: View) {

        mediaRecorder?.stop()
        mediaRecorder?.release()
        mediaRecorder = null

        Toast.makeText(this,"Rcord is Stop",Toast.LENGTH_LONG).show()


    }
    fun btnPlay(view: View) {

        mediaPlayer = MediaPlayer()
        mediaPlayer?.setDataSource(getRecordingFilePath())
        mediaPlayer?.prepare()
        mediaPlayer?.start()

    }


    fun isMicrophonepresent():Boolean{
        if (this.packageManager.hasSystemFeature(PackageManager.FEATURE_MICROPHONE)){
            return true
        }else{
            return false

        }
    }

    fun getMicroPermission(){

        if (ContextCompat.checkSelfPermission(this,Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_DENIED){

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.RECORD_AUDIO),
                MICRPPHONE_PERMISSION_CODE
            )
        }
    }


    private fun getRecordingFilePath(): String {

        var contextWrapper = ContextWrapper(applicationContext)
        var musicDireectory = contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC)
        var file = File(musicDireectory,"testRecordingFile"+".mp3")

        return file.path

    }


}


