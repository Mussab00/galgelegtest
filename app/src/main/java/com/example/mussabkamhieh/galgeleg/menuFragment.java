package com.example.mussabkamhieh.galgeleg;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class menuFragment extends Fragment implements View.OnClickListener {

    Button startSpil;
    Button omSpil;
    TextView navnTest;

    private SharedPreferences prefs;
    public static final String SHARED_PREFERENCES_KEY = "com.example.mussabkamhieh.galgeleg";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);


        startSpil = view.findViewById(R.id.startSpil);
        omSpil = view.findViewById(R.id.omSpillet);
        navnTest = view.findViewById(R.id.navnTest);

        startSpil.setOnClickListener(this);
        omSpil.setOnClickListener(this);


        prefs = getActivity().getSharedPreferences(
                SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String navn = prefs.getString(getString(R.string.navn), "");

        if (navn != "") {
            navnTest.setText("Velkommen " + navn);
        }


        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.startSpil) {
            startActivity(new Intent(getContext(), spil.class));
        } else if (v.getId() == R.id.omSpillet) {
            startActivity(new Intent(getContext(), omSpil.class));
        }

    }
}