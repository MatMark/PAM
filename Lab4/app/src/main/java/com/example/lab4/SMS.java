package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SMS extends AppCompatActivity {

    private EditText phoneNumber;
    private EditText smsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
    }

    public void send(View view) {
        phoneNumber = (EditText) findViewById(R.id.editTextPhone);
        smsText = (EditText) findViewById(R.id.editTextTextMultiLine);
        String number = phoneNumber.getText().toString();
        String text = smsText.getText().toString();

        Uri uri = Uri.parse("smsto:".concat(number));
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", text);
        startActivity(intent);
    }
}