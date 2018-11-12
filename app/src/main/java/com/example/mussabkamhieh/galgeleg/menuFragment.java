package com.example.mussabkamhieh.galgeleg;

import android.app.Activity;
import android.content.Intent;
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

    GalgeLogik logik = new GalgeLogik();
    Button startSpil;
    Button omSpil;
    TextView loading;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        loading = view.findViewById(R.id.loading);
        startSpil = view.findViewById(R.id.startSpil);
        omSpil = view.findViewById(R.id.omSpillet);
        startSpil.setOnClickListener(this);
        omSpil.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.startSpil) {
            loading.setText("Henter ord fra DRs server....");
            new AsyncTask() {
                @Override
                protected Object doInBackground(Object... arg0) {
                    try {
                        logik.hentOrdFraDr();
                        return "Ordene blev korrekt hentet fra DR's server";
                    } catch (Exception e) {
                        e.printStackTrace();
                        return "Ordene blev ikke hentet korrekt: "+e;
                    }
                }

                @Override
                protected void onPostExecute(Object resultat) {
                    loading.setText("resultat: \n" + resultat);
                }
            }.execute();
            startActivity(new Intent(getContext(), spil.class));
        } else if (v.getId() == R.id.omSpillet) {
            startActivity(new Intent(getContext(), omSpil.class));
        }

    }
}