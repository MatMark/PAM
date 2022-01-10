package com.example.rpictrl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ActionActivity extends AppCompatActivity {
    private static final String TAG = "ActionActivity";

    private Application myapp;
    private JSONObject action;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);
        layout = (LinearLayout) findViewById(R.id.layout);
        myapp = (Application) getApplication();

        Bundle extras = getIntent().getExtras();
        String actionText = extras.getString("action");
        try {
            action = new JSONObject(actionText);
            String name = action.getString("name");
            JSONObject info = action.getJSONObject("info");
            JSONObject params = info.getJSONObject("params");
            String type = info.getString("type");

            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            switch (type) {
                case "button":
                    ButtonFragment buttonFragment = ButtonFragment.newInstance(name, params.toString());
                    fragmentTransaction.add(R.id.layout, buttonFragment);
                    fragmentTransaction.commit();
                    break;
                case "colorpicker":
                    ColorPickerFragment colorPickerFragment = ColorPickerFragment.newInstance(name, params.toString());
                    fragmentTransaction.add(R.id.layout, colorPickerFragment);
                    fragmentTransaction.commit();
                    break;
                case "sensor":
                    SensorFragment sensorFragment = SensorFragment.newInstance(name, params.toString());
                    fragmentTransaction.add(R.id.layout, sensorFragment);
                    fragmentTransaction.commit();
                    break;
                default:
                    break;
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(messageReceiver, new IntentFilter("my-message"));
    }

    private BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String text = intent.getStringExtra("my-text");
            TextView valueTextView = (TextView) findViewById(R.id.valueTextView);
            valueTextView.setText(text);
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onPause();
    }
}