package com.example.roomdb

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var databse: ContactDatabse
    lateinit var datt: List<ContactEntity>
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var textview = findViewById<TextView>(R.id.textview)

        databse = Room.databaseBuilder(applicationContext,
            ContactDatabse::class.java,
            "contactDB").build()

        var data = ContactEntity("Dinkar","4334553454")

          GlobalScope.launch {
              databse.contactDaoo().insert(data)
              delay(1000)
              datt = databse.contactDaoo().getAllContact()
              Log.d("DinkarDatabase",datt.get(0).name.toString())

          }

        textview.setText(datt.get(0).name.toString())

    }
}