package com.example.rpictrl;

import android.os.Bundle;

import android.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class ColorPickerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private Application myapp;

    private String name;
    private String params;

    private Button button;

    private SeekBar rgb_r;
    private SeekBar rgb_g;
    private SeekBar rgb_b;

    private EditText editTextRed;
    private EditText editTextGreen;
    private EditText editTextBlue;

    private TextView titleTextView;

    public static ColorPickerFragment newInstance(String name, String params) {
        ColorPickerFragment fragment = new ColorPickerFragment();
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
        View v = inflater.inflate(R.layout.fragment_color_picker, container, false);

        JSONObject json = null;
        try {
            json = new JSONObject(myapp.interfaces);
            String interface_type = json.getString("type");

            rgb_r = (SeekBar) v.findViewById(R.id.red_bar);
            rgb_g = (SeekBar) v.findViewById(R.id.green_bar);
            rgb_b = (SeekBar) v.findViewById(R.id.blue_bar);

            editTextRed = (EditText) v.findViewById(R.id.editTextRed);
            editTextGreen = (EditText) v.findViewById(R.id.editTextGreen);
            editTextBlue = (EditText) v.findViewById(R.id.editTextBlue);

            titleTextView = (TextView) v.findViewById(R.id.titleTextView2);
            button = (Button) v.findViewById(R.id.rgb);
            titleTextView.setText(name);

            rgb_r.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (!editTextRed.getText().toString().equals(String.valueOf(progress))) {
                        editTextRed.setText(String.valueOf(progress));
                    }
                }
            });

            rgb_g.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (!editTextGreen.getText().toString().equals(String.valueOf(progress))) {
                        editTextGreen.setText(String.valueOf(progress));
                    }
                }
            });

            rgb_b.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (!editTextBlue.getText().toString().equals(String.valueOf(progress))) {
                        editTextBlue.setText(String.valueOf(progress));
                    }
                }
            });

            editTextRed.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() != 0) {
                        if (Integer.parseInt(s.toString()) > 100) {
                            editTextRed.setText("100");
                            return;
                        }
                        rgb_r.setProgress(Integer.parseInt(s.toString()));
                    }
                }
            });

            editTextGreen.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() != 0) {
                        if (Integer.parseInt(s.toString()) > 100) {
                            editTextGreen.setText("100");
                            return;
                        }
                        rgb_g.setProgress(Integer.parseInt(s.toString()));
                    }
                }
            });

            editTextBlue.addTextChangedListener(new TextWatcher() {

                @Override
                public void afterTextChanged(Editable s) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start,
                                              int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start,
                                          int before, int count) {
                    if (s.length() != 0) {
                        if (Integer.parseInt(s.toString()) > 100) {
                            editTextBlue.setText("100");
                            return;
                        }
                        rgb_b.setProgress(Integer.parseInt(s.toString()));
                    }
                }
            });


            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    JSONObject json = new JSONObject();
                    try {
                        json.put("name", name);
                        JSONObject params = new JSONObject();
                        params.put("red", rgb_r.getProgress());
                        params.put("green", rgb_g.getProgress());
                        params.put("blue", rgb_b.getProgress());
                        json.put("params", params);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    if (interface_type.equals("bluetooth")) {
                        myapp.mBluetoothConnection.sendJSON(json);
                    } else {
                        RequestQueue queue = Volley.newRequestQueue(view.getContext());
                        JsonObjectRequest request_json = new JsonObjectRequest(myapp.webAddress.concat("/").concat(name), json,
                                new Response.Listener<JSONObject>() {
                                    @Override
                                    public void onResponse(JSONObject response) {
                                        //Process os success response
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                VolleyLog.e("Error: ", error.getMessage());
                            }
                        });
                        queue.add(request_json);
                    }
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return v;
    }
}