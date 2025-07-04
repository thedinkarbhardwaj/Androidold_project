package com.example.p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.widget.Toast;

public class WifiDirectBroadCastRexeiver extends BroadcastReceiver {

    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private MainActivity activity;

    public WifiDirectBroadCastRexeiver(WifiP2pManager manager, WifiP2pManager.Channel channel, MainActivity activity) {
        this.manager = manager;
        this.channel = channel;
        this.activity = activity;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)){

            // Check to see if wifi is enabled and otify appropriate activity
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
                // Wi-Fi Direct is enabled
                Toast.makeText(activity, "Wi-Fi Direct is enabled", Toast.LENGTH_SHORT).show();
            } else {
                // Wi-Fi Direct is not enabled
                Toast.makeText(activity, "Wi-Fi Direct is not enabled", Toast.LENGTH_SHORT).show();
            }

        }else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)){

            // Call WifiP2pManager.requestPeers() to get a list of current peers

            if (manager!=null){
                manager.requestPeers(channel,activity.peerListListener);
            }

        }else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)){

            // Responds to new Connection or disconnections
            if (manager!=null){
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                if (networkInfo.isConnected()){
                    manager.requestConnectionInfo(channel,activity.connectionInfoListener);
                }else {
                    activity.connectionStatus.setText("Not Connected wifiDirect class");
                }
            }
        }

    }
}
