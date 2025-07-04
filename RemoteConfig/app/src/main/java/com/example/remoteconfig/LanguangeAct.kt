package com.example.remoteconfig

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.Locale


class LanguangeAct : AppCompatActivity() {

    var messageView: TextView? = null
    var btnHindi: Button? = null
    var btnEnglish:Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_languange)

        messageView = findViewById<View>(R.id.textView) as TextView
        btnHindi = findViewById<Button>(R.id.btnHindi)
        btnEnglish = findViewById<Button>(R.id.btnEnglish)

        // setting up on click listener event over the button
        // in order to change the language with the help of
        // LocaleHelper class

        // setting up on click listener event over the button
        // in order to change the language with the help of
        // LocaleHelper class
        btnEnglish?.setOnClickListener(View.OnClickListener {

            languangeLocale("en")
            messageView?.setText(resources.getString(R.string.language))
        })

        btnHindi?.setOnClickListener(View.OnClickListener {

            languangeLocale("hi")

            messageView?.setText(resources.getString(R.string.language))
        })

    }


    fun languangeLocale(lan:String){

        val locale = Locale(lan)
        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale
        resources.updateConfiguration(config, baseContext.resources.displayMetrics)

    }
}