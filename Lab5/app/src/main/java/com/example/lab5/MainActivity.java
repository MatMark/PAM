package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText smsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    1);
        }
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECEIVE_SMS},
                    2);
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    3);
        }

        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_SMS) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_SMS},
                    3);
        }


        IntentFilter filterPowerConnected = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        PowerConnectedReceiver pcr = new PowerConnectedReceiver();
        this.registerReceiver(pcr, filterPowerConnected);

        IntentFilter filterPowerDisconnected = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
        PowerDisconnectedReceiver pcd = new PowerDisconnectedReceiver();
        this.registerReceiver(pcd, filterPowerDisconnected);

        IntentFilter filterBatteryLow = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        PowerConnectedReceiver bl = new PowerConnectedReceiver();
        this.registerReceiver(bl, filterBatteryLow);

        IntentFilter filterBatteryOkay = new IntentFilter(Intent.ACTION_BATTERY_OKAY);
        PowerConnectedReceiver bo = new PowerConnectedReceiver();
        this.registerReceiver(bo, filterBatteryOkay);

//        IntentFilter filterBattery = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
//        BatteryReceiver batteryReceiver = new BatteryReceiver();
//        this.registerReceiver(batteryReceiver, filterBattery);

        IntentFilter filterSMS = new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION);
        SMSReceiver smsReceiver = new SMSReceiver();
        this.registerReceiver(smsReceiver, filterSMS);

        setContentView(R.layout.activity_main);
    }

    public void sendSMS(View view) {
        phoneNumber = (EditText) findViewById(R.id.editTextPhone);
        smsText = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView);
        String number = phoneNumber.getText().toString();
        String text = smsText.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage
                (number, null, text,
                        null, null);
        phoneNumber.setText("");
        smsText.setText("");
        Toast toast = Toast.makeText(this.getApplicationContext(),
                "Wysłano wiadomość",
                Toast.LENGTH_SHORT);
        toast.show();
    }
}