package com.eric.pre_downloader.connection;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;


/**
 * Created by Prestein on 6/6/2017.
 */

public class ConnectionDetector {
    private  Context context;
    public ConnectionDetector(Context context) {
        this.context=context;
    }
    public static boolean isInternetAvailable(Context context) {

            String host = "www.google.com";
            int port = 80;
            Socket socket = new Socket();

            try {
                socket.connect(new InetSocketAddress(host, port), 2000);
                socket.close();
                return true;
            } catch (IOException e) {
                try {
                    socket.close();
                } catch (IOException es) {}
                return false;
            }
    }
}
