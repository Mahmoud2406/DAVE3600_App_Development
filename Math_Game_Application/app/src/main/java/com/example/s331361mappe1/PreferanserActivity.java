package com.example.s331361mappe1;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import java.util.Locale;

import static androidx.preference.PreferenceManager.getDefaultSharedPreferences;

public class PreferanserActivity extends PreferenceActivity  implements SharedPreferences.OnSharedPreferenceChangeListener {
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        prefs = getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        String spraak = prefs.getString("spraak_Key", "no");
        settlandOnCreate(spraak);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new PrefsFragment()).commit();

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals("spraak_Key")){
            String spraak = sharedPreferences.getString("spraak_Key", "no");
            settland(spraak);
        }
    }

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        prefs.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
       prefs.unregisterOnSharedPreferenceChangeListener(this);
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

    public void settlandOnCreate(String landskode) {
        Locale locale = new Locale(landskode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }

}