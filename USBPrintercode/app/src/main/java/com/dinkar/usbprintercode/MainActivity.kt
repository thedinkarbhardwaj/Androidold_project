package com.dinkar.usbprintercode

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.util.DisplayMetrics
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dantsu.escposprinter.EscPosPrinter
import com.dantsu.escposprinter.connection.usb.UsbConnection
import com.dantsu.escposprinter.connection.usb.UsbPrintersConnections
import com.dantsu.escposprinter.textparser.PrinterTextParserImg
import java.util.*


class MainActivity : AppCompatActivity() {

     var ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun printbtn(view: View) {

        printUsb()
    }


        var usbReceiver: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent) {
                val action = intent.action
                if (ACTION_USB_PERMISSION.equals(action)) {
                    synchronized(this@MainActivity) {
                        val usbManager = getSystemService(Context.USB_SERVICE) as UsbManager
                        val usbDevice =
                            intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice?
                        if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                            if (usbManager != null && usbDevice != null) {
                                // YOUR PRINT CODE HERE
                                printusbcode(usbManager,usbDevice)
                            }
                        }
                    }
                }
            }
        }


    private fun printusbcode(usbManager: UsbManager, usbDevice: UsbDevice) {
        val printer = EscPosPrinter(UsbConnection(usbManager, usbDevice), 203, 48f, 32)
        printer
            .printFormattedText(
               ""
            )
    }


    fun printUsb() {
        val usbConnection = UsbPrintersConnections.selectFirstConnected(this)
        val usbManager = this.getSystemService(USB_SERVICE) as UsbManager
        if (usbConnection == null || usbManager == null) {
            AlertDialog.Builder(this)
                .setTitle("USB Connection")
                .setMessage("No USB printer found.")
                .show()
            return
        }
        val permissionIntent = PendingIntent.getBroadcast(
            this,
            0,
            Intent(MainActivity().ACTION_USB_PERMISSION),
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) PendingIntent.FLAG_MUTABLE else 0
        )
        val filter = IntentFilter(MainActivity().ACTION_USB_PERMISSION)
        registerReceiver(this.usbReceiver, filter)
        usbManager.requestPermission(usbConnection.device, permissionIntent)
    }


}