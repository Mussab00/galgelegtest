package com.example.mussabkamhieh.galgeleg;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class hovedmenu extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hovedmenu);

        Button StartGame = findViewById(R.id.StartGame);
        Button Highscore = findViewById(R.id.Highscore);
        Button Help = findViewById(R.id.Help);

        StartGame.setOnClickListener(this);
        Highscore.setOnClickListener(this);
        Help.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.StartGame) {
            Intent intent = new Intent(hovedmenu.this, spil.class);
            startActivity(intent);
        } else if (v.getId() == R.id.Highscore){
            Intent intent = new Intent(hovedmenu.this, highscore.class);
            startActivity(intent);
        } else if (v.getId() == R.id.Help) {
            Intent intent = new Intent(hovedmenu.this, help.class);
            startActivity(intent);
        }
    }
}
