package com.example.s331361mappe1;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.Locale;


public class StatsActivity extends AppCompatActivity {

    public TextView right;
    public TextView wrong;
    public Button slettBtn;
    public static final String SHARED_PREFS = "shared-prefs";
    public SharedPreferences pref;
    public int rightInt;
    public int wrongInt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String spraak = prefs.getString("spraak_Key", "no");
        if (!(spraak.equals(Locale.getDefault().getLanguage()))) {
            settland(spraak);
        }


        right = findViewById(R.id.antRight);
        wrong = findViewById(R.id.antWrong);
        slettBtn = findViewById(R.id.slettBtn);
        pref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        rightInt = pref.getInt("right", 0);
        wrongInt = pref.getInt("wrong", 0);

        slettBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pref.edit().putInt("right", 0).putInt("wrong", 0).apply();
                loadData();
                recreate();
            }
        });
        //load statics
        loadData();
    }


    public void loadData() {
        right.setText(String.valueOf(rightInt));
        wrong.setText(String.valueOf(wrongInt));
    }


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
