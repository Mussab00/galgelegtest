package com.example.mussabkamhieh.galgeleg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.jinatonic.confetti.CommonConfetti;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import static com.example.mussabkamhieh.galgeleg.spil.SHARED_PREFERENCES_KEY;

public class vundetFragment extends Fragment implements View.OnClickListener {

    TextView tekst;
    Button igenVundet;
    Button menuVundet;
    SharedPreferences prefs;
    DatabaseReference spillerDB;

    int tries;
    int forkerte;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vundet, container, false);

        Bundle getData = this.getArguments();
        tries = getData.getInt("forsøg");
        forkerte = getData.getInt("forkerte");

        CommonConfetti.rainingConfetti(container, new int[] { Color.BLACK, Color.GRAY, Color.WHITE })
                .oneShot();

        spillerDB = FirebaseDatabase.getInstance().getReference("spillere");
        add();

        tekst = view.findViewById(R.id.vundetinfo);
        tekst.setText("Du har vundet spillet med "+ tries + " forsøg.");

        igenVundet = view.findViewById(R.id.igenVundet);
        menuVundet = view.findViewById(R.id.menuVundet);
        igenVundet.setOnClickListener(this);
        menuVundet.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.igenVundet) {
            getActivity().finish();
            startActivity(new Intent(getContext(), spil.class));
        } else if (v.getId() == R.id.menuVundet) {
            getActivity().finish();
            startActivity(new Intent(getContext(), MainActivity.class));
        }

    }

    private void add() {
        prefs = getActivity().getSharedPreferences(
                SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);
        String navn = prefs.getString(getString(R.string.navn), "");

        if (navn == "") {
            navn = "Ukendt";
        }

        String ID = spillerDB.push().getKey();
        spiller spiller = new spiller(navn, forkerte);

        spillerDB.child(ID).setValue(spiller);
        Toast.makeText(getActivity(), "Spilleren er tilføjet til highscore listen", Toast.LENGTH_SHORT).show();

    }
}
