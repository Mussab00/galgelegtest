package com.example.mussabkamhieh.galgeleg;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class vundetFragment extends Fragment implements View.OnClickListener {

    TextView tekst;
    Button igenVundet;
    Button menuVundet;
    int tries;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vundet, container, false);

        Bundle getData = this.getArguments();
        tries = getData.getInt("forsøg");

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
}
