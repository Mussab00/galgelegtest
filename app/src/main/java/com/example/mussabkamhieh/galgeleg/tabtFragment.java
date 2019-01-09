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

public class tabtFragment extends Fragment implements View.OnClickListener {

    TextView tekst;
    Button igenTabt;
    Button menuTabt;
    String rigtigOrd;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabt, container, false);

        Bundle getData = this.getArguments();
        rigtigOrd = getData.getString("ordet");

        tekst = view.findViewById(R.id.tabtinfo);
        tekst.setText("Ordet var \"" + rigtigOrd + "\"." );

        igenTabt = view.findViewById(R.id.igenTabt);
        menuTabt = view.findViewById(R.id.menuTabt);
        igenTabt.setOnClickListener(this);
        menuTabt.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.igenTabt) {
            getActivity().finish();
            startActivity(new Intent(getContext(), spil.class));
        } else if (v.getId() == R.id.menuTabt) {
            getActivity().finish();
            startActivity(new Intent(getContext(), MainActivity.class));
        }

    }
}
