package com.example.lab5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class SMSReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs;
        String format = bundle.getString("format");
        Object[] pdus = (Object[]) bundle.get("pdus");

        msgs = new SmsMessage[pdus.length];
        for (int i = 0; i < msgs.length; i++) {
            msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i], format);
            respond(context, msgs[i].getOriginatingAddress(), msgs[i].getMessageBody());
        }

    }

    private void respond(Context context, String number, String value) {
        Toast toast = Toast.makeText(context.getApplicationContext(),
                String.format("%s: %s", number, value),
                Toast.LENGTH_SHORT);
        toast.show();
        try {
            int intValue = Integer.parseInt(value);
            if (intValue < 10) {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage
                        (number, null, String.valueOf(intValue + 1),
                                null, null);
            }
        } catch (NumberFormatException e) {
            Log.e("Error", "Input String cannot be parsed to Integer.");
        }

    }
}
