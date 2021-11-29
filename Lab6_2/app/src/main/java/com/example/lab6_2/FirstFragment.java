package com.example.lab6_2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class FirstFragment extends Fragment {

    public FirstFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button button4 = (Button) view.findViewById(R.id.button4);
        Button button5 = (Button) view.findViewById(R.id.button5);
        Button button6 = (Button) view.findViewById(R.id.button6);

        button4.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_firstFragment_to_secondFragment, null));
        button5.setOnClickListener(Navigation.createNavigateOnClickListener(
                R.id.action_firstFragment_to_fourthFragment, null));
        button6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (v.getContext().getClass().getSimpleName()) {
                    case "MainActivity":
                        intent = new Intent(v.getContext(), SecondActivity.class);
                        break;
                    case "SecondActivity":
                        intent = new Intent(v.getContext(), ThirdActivity.class);
                        break;
                    case "ThirdActivity":
                        intent = new Intent(v.getContext(), MainActivity.class);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + v.getContext().getClass().getSimpleName());
                }
                startActivity(intent);
            }
        });

    }
}