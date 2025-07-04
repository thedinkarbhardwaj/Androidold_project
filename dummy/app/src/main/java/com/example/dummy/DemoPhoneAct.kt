package com.example.dummy

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.regex.Matcher
import java.util.regex.Pattern


class DemoPhoneAct : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_phone)

        var btn = findViewById<Button>(R.id.btnLogin)
        var edittext = findViewById<EditText>(R.id.et_phone)


        btn.setOnClickListener {

            val phoneNumber = edittext.text.toString()
            if (isValidPhoneNumber(phoneNumber)) {

               Log.d("Valid","Valid")

            } else {
                edittext.setError("Not valid")
            }

        }

    }

    fun isValidCustomPhoneNumber(phoneNumber: String?): Boolean {
        // Define your custom regular expression for phone number validation
        val regex = "^(?!([0-9])\\1*$)[0-9]{10,13}$"
        val pattern: Pattern = Pattern.compile(regex)
        val matcher: Matcher = pattern.matcher(phoneNumber)
        return matcher.matches()
    }


    fun isValidPhoneNumber(phoneNumber: String?): Boolean {
        val pattern = Patterns.PHONE
        val matcher = pattern.matcher(phoneNumber)
        return matcher.matches()
    }
}