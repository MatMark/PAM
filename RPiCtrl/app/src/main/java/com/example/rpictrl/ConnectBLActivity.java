package com.example.rpictrl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;
import java.util.UUID;

public class ConnectBLActivity extends AppCompatActivity {
    private static final String TAG = "ConnectActivity";
    private Application myapp;

    private Button btnStartConnection;
    private Button btnBack;

    private BluetoothAdapter mBluetoothAdapter;

    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothDevice mBTDevice;

    public ArrayList<BluetoothDevice> mBTDevices = new ArrayList<>();

    public DeviceListAdapter mDeviceListAdapter;

    private ListView lvNewDevices;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bl_connect);
        myapp = (Application) getApplication();

        Button btnONOFF = (Button) findViewById(R.id.btnONOFF);
        lvNewDevices = (ListView) findViewById(R.id.lvNewDevices);
        mBTDevices = new ArrayList<>();

        btnStartConnection = (Button) findViewById(R.id.btnBluetooth);
        btnBack = (Button) findViewById(R.id.btnBack);

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        lvNewDevices.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position,
                                    long id) {
                Log.d(TAG, "onItemClick: You Clicked on a device.");
                String deviceName = mBTDevices.get(position).getName();
                String deviceAddress = mBTDevices.get(position).getAddress();

                Log.d(TAG, "onItemClick: deviceName = " + deviceName);
                Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);

                mBTDevice = mBTDevices.get(position);
                myapp.mBluetoothConnection = new BluetoothConnectionService(ConnectBLActivity.this);
                for (int i = 0; i < lvNewDevices.getChildCount(); i++) {
                    lvNewDevices.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                }
                view.setBackgroundColor(getResources().getColor(R.color.fourth_dark));
            }
        });


        btnONOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: enabling/disabling bluetooth.");
                enableDisableBT();
            }
        });

        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startConnection();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        showPairedDevices();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
    }

    public void startConnection() {
        if (mBTDevice != null) {
            startBTConnection(mBTDevice, MY_UUID);
        } else {
            Toast.makeText(this, "You must chose device first", Toast.LENGTH_SHORT).show();
        }
    }

    public void endConnection() {
        if (myapp.mBluetoothConnection != null && myapp.mBluetoothConnection.isConnected()) {
            endBTConnection();
            myapp.interfaces = null;
        }
    }

    public void startBTConnection(BluetoothDevice device, UUID uuid) {
        Log.d(TAG, "startBTConnection: Initializing RFCOM Bluetooth Connection.");

        myapp.mBluetoothConnection.startClient(device, uuid);
    }

    public void endBTConnection() {
        Log.d(TAG, "endBTConnection: Closing RFCOM Bluetooth Connection.");

        myapp.mBluetoothConnection.endClient();
    }


    public void enableDisableBT() {
        if (mBluetoothAdapter == null) {
            Log.d(TAG, "enableDisableBT: Does not have BT capabilities.");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: enabling BT.");
            Intent enableBTIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(enableBTIntent);

        }
        if (mBluetoothAdapter.isEnabled()) {
            Log.d(TAG, "enableDisableBT: disabling BT.");
            mBluetoothAdapter.disable();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : pairedDevices) {
            mBTDevices.add(device);
            Log.d(TAG, "onReceive: " + device.getName() + ": " + device.getAddress());
            mDeviceListAdapter = new DeviceListAdapter(this, R.layout.device_adapter_view, mBTDevices);
            lvNewDevices.setAdapter(mDeviceListAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        endConnection();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(messageReceiver, new IntentFilter("my-message"));
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("my-text");
            if (text.contains("bluetooth")) {
                myapp.interfaces = text;
                startActivity(new Intent(context, MenuActivity.class));
            } else {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onPause();
    }

}