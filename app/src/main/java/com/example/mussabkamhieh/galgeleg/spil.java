package com.example.mussabkamhieh.galgeleg;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static java.security.AccessController.getContext;

public class spil extends AppCompatActivity implements View.OnClickListener {

    GalgeLogik logik = new GalgeLogik();
    InputMethodManager input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spil);

        TextView info = findViewById(R.id.info);
        Button spilKnap = findViewById(R.id.spilKnap);

        input = (InputMethodManager)
        getSystemService(Context.INPUT_METHOD_SERVICE);


        info.setText("Gæt ordet: "+logik.getSynligtOrd() +
                "\n\nIndtast dit gæt og tryk 'Spil'.\n");
        String velkomst = getIntent().getStringExtra("velkomst");
        if (velkomst!=null) info.append(velkomst);


        spilKnap.setOnClickListener(this);

        logik.logStatus();
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
            input.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),InputMethodManager.HIDE_NOT_ALWAYS);
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
            bundle.putInt("forsøg", logik.getBrugteBogstaver().size());
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
        fragmentTransaction.replace(R.id.overall, fragment);
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.commit();
    }
}
