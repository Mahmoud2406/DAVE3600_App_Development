package com.example.s331361mappe1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import com.example.s331361mappe1.animation_handler.ClickAnimation;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    SharedPreferences prefs;
    SharedPreferences.OnSharedPreferenceChangeListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bruker en Listener for å sjekke om språk blir endret
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        prefs.registerOnSharedPreferenceChangeListener(listener);
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                String spraak = sharedPreferences.getString("spraak_Key", "no");
                if (!(spraak.equals(Locale.getDefault().getLanguage()))) {
                    settland(spraak);
                }
            }
        };

        //Sjekker språk manuelt oncreate
        String spraak = prefs.getString("spraak_Key", "no");
        if (!(spraak.equals(Locale.getDefault().getLanguage()))) {
            settland(spraak);
        }



        //Knapp for playActivity
        ImageView play = findViewById(R.id.btnPlay);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickAnimation.animateClick(v, getApplicationContext());
                Intent toStart = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(toStart);
            }
        });

        //Knapp for preferanseActivity
        ImageView settings = findViewById(R.id.btnSettings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickAnimation.animateClick(v, getApplicationContext());
                Intent toStart = new Intent(MainActivity.this, PreferanserActivity.class);
                startActivity(toStart);
            }
        });

        //Knapp for statsActivity
        ImageView stats = findViewById(R.id.btnStats);
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClickAnimation.animateClick(v, getApplicationContext());
                Intent toStart = new Intent(MainActivity.this, StatsActivity.class);
                startActivity(toStart);
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        //Aktiverer lyttteren ved onResume for å sjekke om prefs objektet har endret seg
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //Aktiverer lyttteren ved onResume for å sjekke om prefs objektet har endret seg
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(listener);
    }


    // Setter språk med landkode som innparameter
    public void settland(String landskode) {
        Locale locale = new Locale(landskode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        recreate();

    }
}