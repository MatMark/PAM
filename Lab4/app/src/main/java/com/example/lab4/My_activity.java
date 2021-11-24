package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class My_activity extends AppCompatActivity {

    private int index;
    private TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        Bundle extras = getIntent().getExtras();
        index = extras.getInt("index");
        data = (TextView) findViewById(R.id.data);
        data.setText(String.valueOf(index));

    }

    @Override
    public void finish() {
        Intent i = new Intent();

        Bundle bundle = new Bundle();

        String res = "";
        if(index == 235605){
            res = "Student: Mateusz Markowski\n" +
                    "Ocena: 5.5\n";
        } else {
            res = "Student: John Doe\n" +
                    "Ocena: 5.0\n";
        }

        bundle.putString("return", res);
        i.putExtras(bundle);

        setResult(RESULT_OK, i);
        super.finish();
    }

    public void end(View view) {
        finish();
    }
}