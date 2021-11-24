package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void contacts(View view) {
        Intent intent = new Intent(this, Contacts.class);
        startActivity(intent);
    }

    public void sms(View view) {
        Intent intent = new Intent(this, SMS.class);
        startActivity(intent);
    }

    public void maps(View view) {
        Intent intent = new Intent(this, Maps.class);
        startActivity(intent);
    }

    public void my_activity(View view) {
        Intent intent = new Intent(this, My_activity_menu.class);
        startActivity(intent);
    }
}