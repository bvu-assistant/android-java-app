package com.bvu.assistant.receiver;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkStatusChecker {

    public static String checkNetworkStatus(Context context) {
        String status = "";

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        status = networkInfo == null? "No network available":
                networkInfo.getType() == ConnectivityManager.TYPE_WIFI? "Wifi enabled":
                        networkInfo.getType() == ConnectivityManager.TYPE_MOBILE? "Mobile enabled": "Unknown internet enabled";

        return status;
    }

}
