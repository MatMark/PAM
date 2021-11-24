package com.example.lab3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mozilla.javascript.Scriptable;

public class MainActivity extends AppCompatActivity {

    private Context context;

    private Button btn0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button btn4;
    private Button btn5;
    private Button btn6;
    private Button btn7;
    private Button btn8;
    private Button btn9;

    private Button btnDiv;
    private Button btnMul;
    private Button btnSub;
    private Button btnAdd;
    private Button btnEqual;
    private Button btnComa;
    private Button btnBack;
    private Button btnClear;

    private Scriptable scope;
    private org.mozilla.javascript.Context rhino;
    private Object result;
    private boolean end = false;

    private TextView textView;
    private TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        history = (TextView) findViewById(R.id.history);

        btn0 = (Button) findViewById(R.id.btn0);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
        btn5 = (Button) findViewById(R.id.btn5);
        btn6 = (Button) findViewById(R.id.btn6);
        btn7 = (Button) findViewById(R.id.btn7);
        btn8 = (Button) findViewById(R.id.btn8);
        btn9 = (Button) findViewById(R.id.btn9);

        btnDiv = (Button) findViewById(R.id.btnDiv);
        btnMul = (Button) findViewById(R.id.btnMul);
        btnSub = (Button) findViewById(R.id.btnSub);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEqual = (Button) findViewById(R.id.btnEqual);
        btnComa = (Button) findViewById(R.id.btnComa);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnClear = (Button) findViewById(R.id.btnClear);

        rhino = org.mozilla.javascript.Context.enter();
        rhino.setOptimizationLevel(-1);
        scope = rhino.initStandardObjects();

        context = getApplicationContext();
    }

    public void num(View view) {
        if (end) {
            end = false;
            textView.setText("");
        }
        String name = view.getResources().getResourceEntryName(view.getId());
        Log.i("user_action", name.substring(name.length() - 1));

        textView.append(name.substring(name.length() - 1));
    }

    public void operation(View view) {
        String name = view.getResources().getResourceEntryName(view.getId());
        String currentText = textView.getText().toString();
        Log.i("user_action", name);
        if (end) {
            end = false;
            textView.setText("");
        }
        switch (name) {
            case "btnDiv":
                textView.append("/");
                break;
            case "btnMul":
                textView.append("*");
                break;
            case "btnSub":
                textView.append("-");
                break;
            case "btnAdd":
                textView.append("+");
                break;
            case "btnComa":
                textView.append(".");
                break;
            case "btnBack":
                if (currentText.length() > 0 && !currentText.equals("Error")) {
                    textView.setText(currentText.substring(0, currentText.length() - 1));
                }
                break;
            case "btnClear":
                textView.setText("");
                break;
            case "btnEqual":
                if (currentText.length() > 0 && !currentText.equals("Error")) {
                    try {
                        result = rhino.evaluateString(scope, textView.getText().toString(), "<cmd>", 1, null);
                        history.append(textView.getText().toString() + " = " + result.toString() + "\n");
                        textView.setText(result.toString());
                        end = true;
                    } catch (Exception e) {
                        textView.setText("Error");
                        end = true;
                    }
                }
                break;
        }
    }
}