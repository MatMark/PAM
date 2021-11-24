package com.example.lab2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private Button calculatedBtn;
    private EditText valueInput;
    private EditText tipValue;
    private TextView calculatedValue;
    private SeekBar foodRate;
    private RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        valueInput = (EditText) findViewById(R.id.valueInput);
        tipValue = (EditText) findViewById(R.id.tipValue);
        foodRate = (SeekBar) findViewById(R.id.foodRate);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        calculatedBtn = (Button) findViewById(R.id.calculatedBtn);
        calculatedValue = (TextView) findViewById(R.id.calculatedValue);

        context = getApplicationContext();
    }

    public void calculate(View view) {
        Log.i("user_action", "calculatedBtn clicked");
        if (!valueInput.getText().toString().equals("") && !tipValue.getText().toString().equals("")) {
            float value = Float.parseFloat(valueInput.getText().toString());
            float tip = Float.parseFloat(tipValue.getText().toString());
            int food = foodRate.getProgress();
            int service = ratingBar.getProgress();
            calculatedValue.setText(String.format("%.2f sum\n%.2f tip\n%d\\5 food\n%d\\5 service", sum(), value * tip * 0.01f, food, service));
            Toast toast = Toast.makeText(getApplicationContext(),
                    String.format("%.2f sum\n%.2f tip\n%d\\5 food\n%d\\5 service", sum(), value * tip * 0.01f, food, service),
                    Toast.LENGTH_SHORT);
            toast.show();
        } else {
            Log.e("input", "Input value is empty");
            calculatedValue.setText(R.string.error);
        }
    }

    private float sum() {
        float sum = Float.parseFloat(valueInput.getText().toString());
        int val = 10;
        float tip = Float.parseFloat(tipValue.getText().toString());
        int foodBonus = (foodRate.getProgress() - 3) * val;
        int serviceBonus = (ratingBar.getProgress() - 3) * val;

        if (foodBonus + serviceBonus < 0 && Math.abs(foodBonus + serviceBonus) > tip) {
            return sum;
        } else {
            return sum + (sum * tip * 0.01f) + foodBonus + serviceBonus;
        }
    }
}