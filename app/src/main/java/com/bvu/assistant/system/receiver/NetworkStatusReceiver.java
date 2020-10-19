package com.bvu.assistant.system.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.bvu.assistant.utils.NetworkStatusChecker;

public class NetworkStatusReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Toast.makeText(context, NetworkStatusChecker.checkNetworkStatus(context), Toast.LENGTH_LONG).show();

    }
}
