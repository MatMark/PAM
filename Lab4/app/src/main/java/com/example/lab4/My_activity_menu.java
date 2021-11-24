package com.example.lab4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class My_activity_menu extends AppCompatActivity {
    private EditText index;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_menu);
    }

    public void check(View view) {
        Intent i = new Intent(this, My_activity.class);

        index = (EditText) findViewById(R.id.index);
        if(!index.getText().toString().equals("")) {
        int index_number = Integer.parseInt(index.getText().toString());
            Bundle bundle = new Bundle();
            bundle.putInt("index", index_number);
            i.putExtras(bundle);

            startActivityForResult(i, 2137);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 2137) {
            if (data.hasExtra("return")) {
                result = (TextView) findViewById(R.id.result);
                result.setText(data.getExtras().getString("return"));
            }
        }
    }
}