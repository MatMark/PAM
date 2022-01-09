package com.example.rpictrl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

public class MenuActivity extends AppCompatActivity {
    private static final String TAG = "MenuActivity";

    private Application myapp;
    private LinearLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        myapp = (Application) getApplication();
        layout = (LinearLayout) findViewById(R.id.layout);
        JSONObject json = null;
        try {
            json = new JSONObject(myapp.interfaces);
            JSONArray actions = json.getJSONArray("actions");

            for(int i = 0; i < actions.length(); i++)
            {
                JSONObject action = actions.getJSONObject(i);
                String name = action.getString("name");

                Button button = new Button(this);
                button.setText(name);
                button.setBackgroundColor(getResources().getColor(R.color.fourth_dark));
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(30, 30, 30, 0);
                button.setLayoutParams(layoutParams);
                button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), ActionActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("action", action.toString());
                    intent.putExtras(bundle);

                    startActivity(intent);
                }
            });
                layout.addView(button);

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
            Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(messageReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}