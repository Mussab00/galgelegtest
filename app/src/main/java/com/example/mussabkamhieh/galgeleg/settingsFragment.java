package com.example.mussabkamhieh.galgeleg;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.example.mussabkamhieh.galgeleg.spil.SHARED_PREFERENCES_KEY;


public class settingsFragment extends Fragment {


    public static final String SHARED_PREFS = "sharedPrefs";

    ToggleButton tglBtn;
    SharedPreferences prefs;
    Button gemData;
    EditText spillerNavn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        tglBtn = view.findViewById(R.id.tglBtn);
        gemData = view.findViewById(R.id.gemData);
        spillerNavn = view.findViewById(R.id.spillerNavn);

        final SharedPreferences.Editor editor = prefs.edit();

        gemData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String navn = spillerNavn.getText().toString();
                editor.putString(getString(R.string.navn), navn);
                editor.apply();

                Toast.makeText(getActivity(), "Data gemt!", Toast.LENGTH_SHORT).show();
            }
        });


        tglBtn.setChecked(prefs.getBoolean(SHARED_PREFS, false));
        tglBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            private static final String TAG = "SettingsFragment";

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Log.d(TAG, "onCheckedChanged isChecked");
                    editor.putBoolean(SHARED_PREFS, true);
                } else {
                    Log.d(TAG, "onCheckedChanged is not Checked");
                    editor.putBoolean(SHARED_PREFS, false);
                }
                editor.apply();
            }
        });

        String navn = prefs.getString(getString(R.string.navn), "");
        spillerNavn.setText(navn);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prefs = getActivity().getSharedPreferences(
                SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

    }
}