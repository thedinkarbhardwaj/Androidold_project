package com.example.p2p

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.net.NetworkInfo
import android.net.Uri
import android.net.wifi.SupplicantState
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDevice
import android.net.wifi.p2p.WifiP2pManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.p2p.Sender_Receiver.Sender.Receiver.ReceiverAct
import com.example.p2p.Sender_Receiver.Sender.SenderAct
import com.example.p2p.databinding.ActivityP2pBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.InetAddress
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Executors

class P2p : AppCompatActivity() {
    private lateinit var binding: ActivityP2pBinding
    private lateinit var connectionStatus: TextView
    private lateinit var messageTextview: TextView
    private lateinit var aSwitch: Button
    private lateinit var discoverButton: Button
    private lateinit var sendButton: Button
    private lateinit var listview: ListView
    private lateinit var typeMsg: EditText

    private lateinit var manager: WifiP2pManager
    private lateinit var channel: WifiP2pManager.Channel
    private lateinit var receiver: WifiDirectBroadcastReceiver
    private lateinit var intentFilter: IntentFilter

    private var peers = ArrayList<WifiP2pDevice>()
    private lateinit var deviceNameArray: Array<String>
    private lateinit var deviceArray: Array<WifiP2pDevice>

    public var socket: Socket? = null

    private lateinit var serverClass: ServerClass
    private lateinit var clientClass: ClientClass

    private var isHost: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityP2pBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initialWork()
        allListener()
    }

    private fun allListener() {


        binding.senderAct.setOnClickListener {
            val intent = Intent(this,SenderAct::class.java)
            startActivity(intent)
        }

        binding.receiverAct.setOnClickListener {
            val intent = Intent(this,ReceiverAct::class.java)
            startActivity(intent)
        }


        binding.onOff.setOnClickListener {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            startActivityForResult(intent, 1)
        }

        binding.btnDiscover.setOnClickListener {
            if (isWifiDirectEnabled()) {
                manager.discoverPeers(channel, object : WifiP2pManager.ActionListener {
                    override fun onSuccess() {
                        connectionStatus.text = "Discovery Started"
                    }

                    override fun onFailure(reason: Int) {
                        connectionStatus.text = "Discovery Not Started - $reason"
                    }
                })
            } else {
                connectionStatus.text = "Wi-Fi Direct is not enabled."
            }
        }

        listview.setOnItemClickListener { _, view, position, _ ->
            val device = deviceArray[position]
            val config = WifiP2pConfig()
            config.deviceAddress = device.deviceAddress
            manager.connect(channel, config, object : WifiP2pManager.ActionListener {
                override fun onSuccess() {
                    connectionStatus.text = "Connected : ${device.deviceAddress} ${device.deviceName}"
                }

                override fun onFailure(reason: Int) {
                    connectionStatus.text = "Not Connected"
                }
            })
        }

        binding.sendButton.setOnClickListener {
            val msg = binding.editTextTypeMsg.text.toString()

            lifecycleScope.launch(Dispatchers.IO) {
                if (msg.isNotEmpty()) {
                    if (isHost) {
                        serverClass.write(msg.toByteArray())
                    } else {
                        clientClass.write(msg.toByteArray())
                    }
                }
            }
        }
    }

    private fun initialWork() {
        connectionStatus = binding.connectionStatus
        messageTextview = binding.msgTextview
        aSwitch = binding.onOff
        discoverButton = binding.btnDiscover
        listview = binding.listView
        typeMsg = binding.editTextTypeMsg
        sendButton = binding.sendButton

        manager = getSystemService(Context.WIFI_P2P_SERVICE) as WifiP2pManager
        channel = manager.initialize(this, mainLooper, null)
        receiver = WifiDirectBroadcastReceiver(manager, channel, this)
        intentFilter = IntentFilter().apply {
            addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION)
            addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION)
        }
    }

    private val peerListListener = WifiP2pManager.PeerListListener { wifiP2pDeviceList ->
        if (wifiP2pDeviceList.deviceList != peers) {
            peers.clear()
            peers.addAll(wifiP2pDeviceList.deviceList)

            deviceNameArray = Array(wifiP2pDeviceList.deviceList.size) { "" }
            deviceArray = wifiP2pDeviceList.deviceList.toTypedArray()


            for ((index, device) in wifiP2pDeviceList.deviceList.withIndex()) {
                deviceNameArray[index] = device.deviceName
                deviceArray[index] = device
            }

            val adapter = ArrayAdapter(applicationContext, android.R.layout.simple_list_item_1, deviceNameArray)
            listview.adapter = adapter

            if (peers.size == 0) {
                connectionStatus.text = "No Device Found"
            }
        }
    }

    private val connectionInfoListener = WifiP2pManager.ConnectionInfoListener { info ->
        val groupOwnerAddress = info.groupOwnerAddress
        if (info.groupFormed && info.isGroupOwner) {
            connectionStatus.text = "Host"
            isHost = true

            serverClass = ServerClass()
            serverClass.start()
        } else if (info.groupFormed) {
            connectionStatus.text = "Client"
            isHost = false

            clientClass = ClientClass(groupOwnerAddress)
            clientClass.start()
        }
    }

    override fun onResume() {
        super.onResume()
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }

    inner class ServerClass : Thread() {
        private lateinit var serverSocket: ServerSocket
        private lateinit var inputStream: InputStream
        private lateinit var outputStream: OutputStream

        fun write(bytes: ByteArray) {
            try {
                outputStream.write(bytes)
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }

        override fun run() {
            try {
                serverSocket = ServerSocket(8888)
                socket = serverSocket.accept()
                inputStream = socket?.getInputStream()!!
                outputStream = socket?.getOutputStream()!!

                val handler = Handler(Looper.getMainLooper())

                Executors.newSingleThreadExecutor().execute {
                    val buffer = ByteArray(1024)
                    var bytes: Int

                    while (socket != null) {
                        try {
                            bytes = inputStream.read(buffer)

                            if (bytes > 0) {
                                handler.post {
                                    val tempMsg = String(buffer, 0, bytes)
                                    messageTextview.text = tempMsg
                                }
                            }
                        } catch (e: IOException) {
                            throw RuntimeException(e)
                        }
                    }
                }
            } catch (e: IOException) {
                throw RuntimeException(e)
            }
        }
    }

    inner class ClientClass(hostAddress: InetAddress) : Thread() {
        var hostAdd: String
        private var inputStream: InputStream? = null
        private var outputStream: OutputStream? = null

        init {
            hostAdd = hostAddress.hostAddress
            socket = Socket()
        }

        fun write(bytes: ByteArray?) {
            try {
                outputStream!!.write(bytes)
            } catch (e: IOException) {
                throw java.lang.RuntimeException(e)
            }
        }

        override fun run() {
            try {
                socket?.connect(InetSocketAddress(hostAdd, 8888), 500)
                inputStream = socket?.getInputStream()
                outputStream = socket?.getOutputStream()
            } catch (e: IOException) {
                throw java.lang.RuntimeException(e)
            }
            val executor = Executors.newSingleThreadExecutor()
            val handler = Handler(Looper.getMainLooper())
            executor.execute {
                val buffer = ByteArray(1024)
                var bytes: Int
                while (socket != null) {
                    try {
                        bytes = inputStream!!.read(buffer)
                        if (bytes > 0) {
                            val finalBytes = bytes
                            handler.post {
                                val tempMsg = String(buffer, 0, finalBytes)
                                messageTextview.setText(tempMsg)
                            }
                        }
                    } catch (e: IOException) {
                        throw java.lang.RuntimeException(e)
                    }
                }
            }
        }
    }


    private fun isWifiEnabled(): Boolean {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        return wifiManager?.isWifiEnabled == true
    }

    private fun isWifiDirectEnabled(): Boolean {
        val wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager?
        val wifiInfo: WifiInfo? = wifiManager?.connectionInfo
        return wifiInfo != null && wifiInfo.supplicantState == SupplicantState.COMPLETED
    }

    class WifiDirectBroadcastReceiver(
        private val manager: WifiP2pManager,
        private val channel: WifiP2pManager.Channel,
        private val activity: P2p
    ) : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action!!

            when (action) {
                WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION -> {
                    val state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1)
                    if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                        Toast.makeText(activity, "Wi-Fi Direct is enabled", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(activity, "Wi-Fi Direct is not enabled", Toast.LENGTH_SHORT).show()
                    }
                }
                WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION -> {
                    manager.requestPeers(channel, activity.peerListListener)
                }
                WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION -> {
                    val networkInfo: NetworkInfo? = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO)
                    if (networkInfo?.isConnected == true) {
                        manager.requestConnectionInfo(channel, activity.connectionInfoListener)
                    } else {
                        activity.connectionStatus.text = "Not Connected wifiDirect class"
                    }
                }
            }
        }
    }
}


