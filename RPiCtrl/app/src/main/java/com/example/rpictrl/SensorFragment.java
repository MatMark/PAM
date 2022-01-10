package com.example.rpictrl;

import android.os.Bundle;

import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SensorFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Application myapp;

    private String name;
    private String params;

    private Button button;
    private TextView titleTextView;
    private TextView valueTextView;

    public static SensorFragment newInstance(String name, String params) {
        SensorFragment fragment = new SensorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        args.putString(ARG_PARAM2, params);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
            params = getArguments().getString(ARG_PARAM2);
        }
        myapp = (Application) this.getActivity().getApplication();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sensor, container, false);

        try {
            JSONObject json = new JSONObject(myapp.interfaces);
            String interface_type = json.getString("type");
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            valueTextView = (TextView) v.findViewById(R.id.valueTextView);
            button = (Button) v.findViewById(R.id.button);
            titleTextView.setText(name);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("name", name);
                        JSONObject params = new JSONObject();
                        json.put("params", params);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (interface_type.equals("bluetooth")) {
                        myapp.mBluetoothConnection.sendJSON(json);
                    } else {
                        RequestQueue queue = Volley.newRequestQueue(view.getContext());
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, myapp.webAddress.concat("/").concat(name),
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        valueTextView.setText(response);
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(view.getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        });
                        queue.add(stringRequest);
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return v;
    }

}