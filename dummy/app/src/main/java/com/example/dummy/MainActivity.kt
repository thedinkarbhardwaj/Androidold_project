package com.example.dummy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.room.Room
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var database:ContactDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = ContactDatabase.getDatabase(applicationContext)

        database.contactDao().getcontact().observe(this@MainActivity) {
            Log.d("GetData", it.toString())
        }

//        var pdfView = findViewById<PdfRendererView>(R.id.pdfView)
//        pdfView.initWithUrl(
//            url = "https://phpstack-102119-3888765.cloudwaysapps.com/public/uploads/lesson_img_vid/pdf_1702636927.pdf",
//            lifecycleCoroutineScope = lifecycleScope,
//            lifecycle = lifecycle
//        )

//        PdfViewerActivity.launchPdfFromUrl(
//            context = this,
//            pdfUrl = "https://phpstack-102119-3888765.cloudwaysapps.com/public/uploads/lesson_img_vid/pdf_1702636927.pdf",
//            pdfTitle = "PDF Title",
//            saveTo = saveTo.ASK_EVERYTIME,
//            enableDownload = true
//        )

        GlobalScope.launch {
           // database.contactDao().insert(Contact(0,"Dinkar Bhardwaj","Descccccc"))

        }
    }
}