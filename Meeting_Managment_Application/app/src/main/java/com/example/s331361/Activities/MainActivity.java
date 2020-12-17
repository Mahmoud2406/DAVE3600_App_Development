package com.example.s331361.Activities;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.s331361.DBHandler;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.R;
import com.example.s331361.fragments.ContactsFragment;
import com.example.s331361.fragments.MeetingsFragment;
import com.example.s331361.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    BottomNavigationView nav;
    static public DBHandler db;
    Fragment fragment;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        prefs.registerOnSharedPreferenceChangeListener(this);

        Intent intent = new Intent();
        intent.setAction("dagligmaatepaaminnelse");
        sendBroadcast(intent);

        db = new DBHandler(this);
        sendSms();
        nav = findViewById(R.id.bottomnav);
        nav.setSelectedItemId(R.id.mote);

        if (savedInstanceState != null) {
            //Restore the fragment's instance
            fragment = getFragmentManager().getFragment(savedInstanceState, "fragment");

            int id = savedInstanceState.getInt("opened_fragment");
            nav.setSelectedItemId(id);
            restoreMenu(id);
            nav.setOnNavigationItemSelectedListener(bottomNavMethod);
        }
        if (savedInstanceState == null) {
            fragment = new MeetingsFragment();
            getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
            nav.setOnNavigationItemSelectedListener(bottomNavMethod);
        }




    }

    public void sendSms() {
        int MY_PERMISSIONS_REQUEST_SEND_SMS = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int MY_PHONE_STATE_PERMISSION = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE);

        if (MY_PERMISSIONS_REQUEST_SEND_SMS == PackageManager.PERMISSION_GRANTED &&
                MY_PHONE_STATE_PERMISSION ==
                        PackageManager.PERMISSION_GRANTED) {
            prefs.edit().putBoolean("sendSms",true).commit();
        }
        else{  ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.READ_PHONE_STATE}, 0);

          if (MY_PERMISSIONS_REQUEST_SEND_SMS != PackageManager.PERMISSION_GRANTED &&
                    MY_PHONE_STATE_PERMISSION !=
                            PackageManager.PERMISSION_GRANTED) {
              prefs.edit().putBoolean("sendSms",false).commit();
          }
        }
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        getFragmentManager().putFragment(outState, "fragment", fragment);
        outState.putInt("opened_fragment", nav.getSelectedItemId());
    }


    @Override
    protected void onResume() {
        System.out.println("On resume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()) {

                        case R.id.kontakter:
                            fragment = new ContactsFragment();
                            break;

                        case R.id.innstillinger:
                            fragment = new SettingsFragment();
                            break;
                        case R.id.mote:
                            fragment = new MeetingsFragment();
                            break;
                        default:
                            fragment = new MeetingsFragment();
                            break;
                    }
                    getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                    return true;
                }
            };


    public boolean restoreMenu(@NonNull int menuItem) {
        switch (menuItem) {
            case R.id.kontakter:
                fragment = new ContactsFragment();
                break;
            case R.id.innstillinger:
                fragment = new SettingsFragment();
                break;
            case R.id.mote:
                fragment = new MeetingsFragment();
                break;
        }
        getFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        boolean sendSms = sharedPreferences.getBoolean("sendSms", true);
        if(sendSms){
            sendSms();
        }
        System.out.println("Changed prefs");
            Intent intent = new Intent();
            intent.setAction("dagligmaatepaaminnelse");
            sendBroadcast(intent);
    }
}
