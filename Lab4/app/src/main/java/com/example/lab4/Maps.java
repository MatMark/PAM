package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Maps extends AppCompatActivity {

    private EditText latitude;
    private EditText longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
    }

    public void goToMap(View view) {
        latitude = (EditText) findViewById(R.id.editTextNumberDecimal);
        longitude = (EditText) findViewById(R.id.editTextNumberDecimal2);
        String lat = latitude.getText().toString();
        String lon = longitude.getText().toString();

        Uri gmmIntentUri = Uri.parse("geo:".concat(lat).concat(",").concat(lon));
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
    }
}