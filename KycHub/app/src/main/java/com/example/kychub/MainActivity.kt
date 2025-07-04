package com.example.kychub


import android.net.Uri
import android.os.Bundle
import android.webkit.PermissionRequest
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var webView = findViewById<WebView>(R.id.webView)

        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true

        // Set up WebViewClient to handle navigation inside WebView
        webView.webViewClient = WebViewClient()

        // Set up WebChromeClient to handle UI-related interactions, including permissions
        webView.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) {
                // Handle permission request (e.g., camera permission)
                request.grant(request.resources)
            }
        }

        // Handle camera access

//         Handle camera access
        webView.webChromeClient = object : WebChromeClient() {
            override fun onShowFileChooser(
                webView: WebView,
                filePathCallback: ValueCallback<Array<Uri>>,
                fileChooserParams: FileChooserParams
            ): Boolean {
               Toast.makeText(this@MainActivity,"Camera open",Toast.LENGTH_LONG).show()
                return true
            }
        }

        webView.evaluateJavascript("navigator.mediaDevices.getUserMedia({ video: true, audio: false })" +
                ".then(function (stream) {" +
                "   // Handle the camera stream" +
                "})" +
                ".catch(function (error) {" +
                "   // Handle the error" +
                "});", null);

        // Load a URL
       // webView.loadUrl("https://hosted.kychub.com/fas/en/startkyc?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJhcmcxXCI6XCJmMDBlZDZkN2RiM2Y5MWU3ZDQzODU5NGFkMDlkZjczZTViMDViNWNiMmE2MmMxNDZhYjYzZjM3NjY2YjNmMjdkXCIsXCJhcmcyXCI6MjI2ODgsXCJhcmczXCI6MTQ4Nn0iLCJzY29wZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfUkVNT1RFIn1dLCJuYmYiOjE3MDQ0NDk1NTEsImlhdCI6MTcwNDQ0OTU1MSwiZXhwIjoxNzA1MDU3OTUxfQ.yHBz6wguZBjH8W62pPBmJEwEhv6KQTNI1muanaORXqqyFXn4aJ29V-lYG8M4N7RkrTd0ZU8-IZnEMfTKFbfgqg&client=im.global")
        webView.loadUrl("https://hosted.kychub.com/fas/en/startkyc?token=eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ7XCJhcmcxXCI6XCJmNzkxM2Q2NmFkZDQyZTFlY2U5NjlkYjdjZWY1NDU3Njk2YWMxYmYxODdlMmU3ODU1YjBiNGQyZjM0NDk3OGIyXCIsXCJhcmcyXCI6MjI3NjksXCJhcmczXCI6MTQ4Nn0iLCJzY29wZXMiOlt7ImF1dGhvcml0eSI6IlJPTEVfUkVNT1RFIn1dLCJuYmYiOjE3MDQ4NjQ1NTcsImlhdCI6MTcwNDg2NDU1NywiZXhwIjoxNzA1NDcyOTU3fQ.6C-ZJbPJxgU7a5HMtFwN_frzVvLOUGzzX3oSnK_mb1GC9PKI0Rh0ArKgojrNtY7V3zxl3Zno0ku567EIqkXVOw")
    }
}