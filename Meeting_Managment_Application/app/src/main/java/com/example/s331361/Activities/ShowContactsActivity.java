package com.example.s331361.Activities;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.s331361.Adapters.AdapterContacts;
import com.example.s331361.Adapters.AdapterSelectContacts;
import com.example.s331361.Adapters.AdapterShowContacts;
import com.example.s331361.DBHandler;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.R;

import java.util.ArrayList;
import java.util.List;

public class ShowContactsActivity extends AppCompatActivity {

    DBHandler db;
    ListView lv;
    ArrayList<Kontakt> list;
    AdapterShowContacts adbContact;
    TextView antallKontakter;


    public ShowContactsActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_contacts);

        Intent intent = getIntent();
        int moote_ID = (int) intent.getExtras().getLong("key");

        adbContact = new AdapterShowContacts(this, 0,moote_ID );

        ImageView back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        lv = findViewById(R.id.showContacts);

        lv.setAdapter(adbContact);

    }




}