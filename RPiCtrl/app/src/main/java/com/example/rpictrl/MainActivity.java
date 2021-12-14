package com.example.rpictrl;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private Application myapp;

    private Button btnStartConnection;
    private Button red;
    private Button blue;
    private Button rgb;

    private SeekBar rgb_r;
    private SeekBar rgb_g;
    private SeekBar rgb_b;

    private EditText editTextRed;
    private EditText editTextGreen;
    private EditText editTextBlue;

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
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
//        unregisterReceiver(mBroadcastReceiver1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myapp = (Application) getApplication();

        btnStartConnection = (Button) findViewById(R.id.btnBluetooth);
        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        rgb = (Button) findViewById(R.id.rgb);

        rgb_r = (SeekBar) findViewById(R.id.red_bar);
        rgb_g = (SeekBar) findViewById(R.id.green_bar);
        rgb_b = (SeekBar) findViewById(R.id.blue_bar);

        editTextRed = (EditText) findViewById(R.id.editTextRed);
        editTextGreen = (EditText) findViewById(R.id.editTextGreen);
        editTextBlue = (EditText) findViewById(R.id.editTextBlue);

        btnStartConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ConnectActivity.class);
                startActivity(intent);
            }
        });

        red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json = new JSONObject();
                try {
                    json.put("name", "test1");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendJSON(json);
            }
        });

        blue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json = new JSONObject();
                try {
                    json.put("name", "test2");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendJSON(json);
            }
        });

        rgb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                JSONObject json = new JSONObject();
                try {
                    json.put("name", "test3");
                    json.put("red", rgb_r.getProgress());
                    json.put("green", rgb_g.getProgress());
                    json.put("blue", rgb_b.getProgress());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                sendJSON(json);
            }
        });

        rgb_r.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!editTextRed.getText().toString().equals(String.valueOf(progress))) {
                    editTextRed.setText(String.valueOf(progress));
                }
            }
        });

        rgb_g.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!editTextGreen.getText().toString().equals(String.valueOf(progress))) {
                    editTextGreen.setText(String.valueOf(progress));
                }
            }
        });

        rgb_b.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (!editTextBlue.getText().toString().equals(String.valueOf(progress))) {
                    editTextBlue.setText(String.valueOf(progress));
                }
            }
        });

        editTextRed.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    if (Integer.parseInt(s.toString()) > 100) {
                        editTextRed.setText("100");
                        return;
                    }
                    rgb_r.setProgress(Integer.parseInt(s.toString()));
                }
            }
        });

        editTextGreen.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    if (Integer.parseInt(s.toString()) > 100) {
                        editTextGreen.setText("100");
                        return;
                    }
                    rgb_g.setProgress(Integer.parseInt(s.toString()));
                }
            }
        });

        editTextBlue.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (s.length() != 0) {
                    if (Integer.parseInt(s.toString()) > 100) {
                        editTextBlue.setText("100");
                        return;
                    }
                    rgb_b.setProgress(Integer.parseInt(s.toString()));
                }
            }
        });
    }

    private void sendJSON(JSONObject json) {
        if (myapp.mBluetoothConnection != null && myapp.mBluetoothConnection.isConnected()) {
            String jsonString = json.toString();
            byte[] bytes = jsonString.getBytes();
            Log.d(TAG, jsonString);
            myapp.mBluetoothConnection.write(bytes);
        } else {
            Toast.makeText(this, "Can't connect", Toast.LENGTH_SHORT).show();
        }
    }

}