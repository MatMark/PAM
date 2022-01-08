package com.example.rpictrl;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


public class ConnectWEBActivity extends AppCompatActivity {
    private static final String TAG = "ConnectActivity";
    private Application myapp;

    private TextView textViewAddress;
    private Button btnStartConnection;
    private Button btnBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_connect);
        myapp = (Application) getApplication();

        btnStartConnection = (Button) findViewById(R.id.btnBluetooth);
        btnBack = (Button) findViewById(R.id.btnBack);

        textViewAddress = (TextView) findViewById(R.id.textViewAddress);

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
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: called.");
        super.onDestroy();
    }

    public void startConnection() {
        String address = textViewAddress.getText().toString();
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, address,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getBaseContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getBaseContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(stringRequest);
    }

    public void endConnection() {
        // TODO
    }

    @Override
    public void onResume() {
        super.onResume();
        endConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

}