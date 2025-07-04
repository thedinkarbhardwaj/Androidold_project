package com.example.loginactivitydebugging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var a = 10

        if (a == 10){
            Toast.makeText(this,"Correct",Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(this,"wrong",Toast.LENGTH_SHORT).show()
        }
    }
}