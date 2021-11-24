package com.example.lab5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class PowerDisconnectedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast toast = Toast.makeText(context.getApplicationContext(),
                "Power disconnected",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}
