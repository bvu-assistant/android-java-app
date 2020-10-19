package com.bvu.assistant.utils;

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

    public static boolean isNetworkConnected(Context context) {
        boolean status = false;

        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();


        status = networkInfo == null? false:
                networkInfo.getType() == ConnectivityManager.TYPE_WIFI? true:
                        networkInfo.getType() == ConnectivityManager.TYPE_MOBILE? true: false;

        return status;
    }

}
