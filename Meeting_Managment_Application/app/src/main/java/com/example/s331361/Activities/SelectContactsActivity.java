package com.example.s331361.Activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.s331361.Adapters.AdapterSelectContacts;
import com.example.s331361.DBHandler;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.R;

import java.util.ArrayList;
import java.util.List;

public class SelectContactsActivity extends AppCompatActivity {
    DBHandler db;
    ListView lv;
    ArrayList<Kontakt> list;
    AdapterSelectContacts adbContact;
    CheckBox velgalle;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DBHandler(this);
        setContentView(R.layout.activity_select_contacts);


        final Button addContacts = findViewById(R.id.save);

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //lagre selected contacts
                List<Long> kontaktListChecked = new ArrayList<>();
                for (Kontakt kontakt : adbContact.lPerson) {
                    if(kontakt.isselected()) {
                        kontaktListChecked.add(kontakt.get_ID());
                    }
                }
                if(!kontaktListChecked.isEmpty()) {
                    Intent intent = new Intent();
                    intent.putExtra("kontakter", (ArrayList<Long>) kontaktListChecked);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }
        });




        velgalle = findViewById(R.id.velgalle);
        lv = findViewById(R.id.selectContacts);
        adbContact = new AdapterSelectContacts(this, 0);

        velgalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(velgalle.isChecked()) {
                    adbContact.selectAll(true);
                    lv.setAdapter(adbContact);
                }
                else {
                    adbContact.selectAll(false);
                    lv.setAdapter(adbContact);
                }
            }
        });

        lv.setAdapter(adbContact);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            }
        });
    }

    private boolean allowRefresh = false;

    @Override
    public void onResume() {
        adbContact.notifyDataSetChanged();
        super.onResume();
        if(allowRefresh){
            allowRefresh = false;
            list = (ArrayList<Kontakt>) db.finnAlleKontakter();
            adbContact = new AdapterSelectContacts(this, 0);
            lv.setAdapter(adbContact);
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                }
            });

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (!allowRefresh)
            allowRefresh = true;
    }

}