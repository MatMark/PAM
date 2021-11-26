package com.example.lab6;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class SecondFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";

    private String mParam1;

    public SecondFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            Log.i("TEST", mParam1);
        } else {
            Log.i("TEST", "Empty");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_second, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button button = (Button) view.findViewById(R.id.buttonLastFragment);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle test = new Bundle();
                test.putString("param1", mParam1);

                try {
                    int next = Integer.parseInt(mParam1);
                    switch (next) {
                        case 2:
                            Navigation.findNavController(view).navigate(R.id.secondFragment, test);
                            break;
                        case 3:
                            Navigation.findNavController(view).navigate(R.id.thirdFragment, test);
                            break;
                        case 4:
                            Navigation.findNavController(view).navigate(R.id.fourthFragment, test);
                            break;
                        default:
                            Navigation.findNavController(view).navigate(R.id.firstFragment, test);
                    }
                } catch (NumberFormatException e) {
                    Navigation.findNavController(view).navigate(R.id.firstFragment, test);
                }

            }
        });
    }
}