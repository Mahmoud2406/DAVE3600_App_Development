package com.example.s331361.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.s331361.DBHandler;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.R;

import java.util.regex.Pattern;

public class EditContactsActivity extends AppCompatActivity {
    long id;
    EditText innNavn;
    EditText innTelefon;
    DBHandler db;
    TextView feilnavn, feiltelefon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contacts);

        innNavn = findViewById(R.id.innNavn);
        innTelefon = findViewById(R.id.innTelefon);

        Intent intent = getIntent();
        int kontakt_ID = (int) intent.getExtras().getLong("key");
        db = new DBHandler(this);
        Kontakt kontakt = db.finnKontakt(kontakt_ID);

        id = kontakt.get_ID();
        innNavn.setText(kontakt.get_Navn());
        innTelefon.setText(kontakt.get_Telefon());

        feilnavn = findViewById(R.id.feilnavn);
        feiltelefon = findViewById(R.id.feiltelefon);

        Button addContact = findViewById(R.id.addContact);
        addContact.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                    boolean innNavnRegex = Pattern.matches("[a-zA-Z ]{4,26}", innNavn.getText().toString());
                    boolean innTelefonRegex = Pattern.matches("[49][0-9]{7}", innTelefon.getText().toString());

                    if (innNavnRegex == false) {
                        feilnavn.setText("Skriv inn riktig format for navn: (Unngå tall),(Minimum 4 tegn)");
                    } else {
                        feilnavn.setText("");
                    }
                    if (innTelefonRegex == false) {
                        feiltelefon.setText("Nummeret må inneholde 8 siffer og starte med 4 eller 9");
                    } else {
                        feiltelefon.setText("");
                    }

                    if (innNavnRegex && innTelefonRegex) {
                        Kontakt kontakt = new Kontakt(id, innNavn.getText().toString(), innTelefon.getText().toString());
                        db.oppdaterKontakt(kontakt);
                        innNavn.setText("");
                        innTelefon.setText("");
                        Toast.makeText(getApplicationContext(), "Oppdatert kontakt", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    }
                }

        });
    }
}