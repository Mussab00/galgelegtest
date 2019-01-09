package com.example.mussabkamhieh.galgeleg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.mussabkamhieh.galgeleg.settingsFragment.SHARED_PREFS;
import static java.security.AccessController.getContext;

public class spil extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SpilActivity";
    public static final String SHARED_PREFERENCES_KEY = "com.example.mussabkamhieh.galgeleg";

    GalgeLogik logik = new GalgeLogik();
    TextView loading;
    ToggleButton tglBtn;

    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spil);

        final TextView loading = findViewById(R.id.loading);

        TextView info = findViewById(R.id.info);
        Button spilKnap = findViewById(R.id.spilKnap);


        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.push();


        info.setText("Gæt ordet: "+logik.getSynligtOrd() +
                "\n\nIndtast dit gæt og tryk 'Spil'.\n");
        String velkomst = getIntent().getStringExtra("velkomst");
        if (velkomst!=null) info.append(velkomst);


        spilKnap.setOnClickListener(this);

        logik.logStatus();
        prefs = getSharedPreferences(
                SHARED_PREFERENCES_KEY, Context.MODE_PRIVATE);

        boolean hentFraDR = prefs.getBoolean(SHARED_PREFS, false);
        Log.d(TAG, "boolean value is: " + hentFraDR);
        if (hentFraDR) {
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
        }
    }


    @Override
    public void onClick(View v) {
        Button spilKnap = findViewById(R.id.spilKnap);
        EditText bogstavFelt = findViewById(R.id.bogstavFelt);

        String bogstav = bogstavFelt.getText().toString();
        if (bogstav.length() != 1) {
            bogstavFelt.setError("Du må kun indtaste ét bogstav");
            return;
        }

        logik.gætBogstav(bogstav);


        bogstavFelt.setText("");
        bogstavFelt.setError(null);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
            spilKnap.animate().rotationBy(3*360).setInterpolator(new DecelerateInterpolator());
//      spilKnap.animate().translationYBy(30).setInterpolator(new BounceInterpolator());
        }
        updateScreen();
    }
    private void updateScreen() {
        ImageView billede = findViewById(R.id.billede);
        TextView info = findViewById(R.id.info);

        switch (logik.getAntalForkerteBogstaver()) {
            case 1: billede.setImageResource(R.drawable.forkert1);
                break;
            case 2: billede.setImageResource(R.drawable.forkert2);
                break;
            case 3: billede.setImageResource(R.drawable.forkert3);
                break;
            case 4: billede.setImageResource(R.drawable.forkert4);
                break;
            case 5: billede.setImageResource(R.drawable.forkert5);
                break;
            case 6: billede.setImageResource(R.drawable.forkert6);
                break;
            case 7: billede.setImageResource(R.drawable.forkert7);
                break;
        }

        info.setText("Gæt ordet: " + logik.getSynligtOrd());
        info.append("\n\nDu har " + logik.getAntalForkerteBogstaver() + " forkerte:" + logik.getBrugteBogstaver());

        Bundle bundle = new Bundle();

        if (logik.erSpilletVundet()) {
            MediaPlayer winSound = MediaPlayer.create(this, R.raw.win);
            winSound.start();
            bundle.putInt("forsøg", logik.getBrugteBogstaver().size());
            bundle.putInt("forkerte", logik.getAntalForkerteBogstaver());
            replaceFragment(new vundetFragment(), bundle);
        }
        if (logik.erSpilletTabt()) {
            bundle.putString("ordet", logik.getOrdet());
            replaceFragment(new tabtFragment(), bundle);
        }

    }

    public void replaceFragment(Fragment fragment, Bundle bundle) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragment.setArguments(bundle);
        findViewById(R.id.overallThree).setVisibility(View.INVISIBLE);
        findViewById(R.id.billede).setVisibility(View.INVISIBLE);
        findViewById(R.id.loading).setVisibility(View.INVISIBLE);
        fragmentTransaction.replace(R.id.overall, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
