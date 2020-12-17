package com.example.s331361.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.s331361.DBHandler;
import com.example.s331361.Model.Moote;
import com.example.s331361.R;

import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;

public class AddMeetingsActivity extends AppCompatActivity {
    EditText innType;
    EditText innSted;
    TextView feilsted, feiltype, feilKontaktListe;
    EditText antall;
    DBHandler db;
    ImageView addListOfContacts;
    ArrayList<Long> kontakterId;
    DatePicker datePicker;
    TimePicker timePicker;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                kontakterId = (ArrayList<Long>) data.getSerializableExtra("kontakter");
                System.out.println(kontakterId.size());
                antall.setText("Antall lagt til: " + kontakterId.size());

            }
            System.out.println(RESULT_OK);

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meetings);
        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);
        feilKontaktListe = findViewById(R.id.feilKontaktListe);
        antall = findViewById(R.id.antall);
        innSted = findViewById(R.id.innSted);
        innType = findViewById(R.id.innType);
        addListOfContacts = findViewById(R.id.addListOfContacts);
        feilsted = findViewById(R.id.feilsted);
        feiltype = findViewById(R.id.feiltype);

        addListOfContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SelectContactsActivity.class);
                startActivityForResult(intent, 1);

            }
        });

        db = new DBHandler(this);
        Button addMeeting = findViewById(R.id.addMeeting);
        addMeeting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean innTypeRegex = Pattern.matches("[a-zæøåA-ZÆØÅ ]{4,26}", innType.getText().toString());
                boolean innStedRegex = Pattern.matches("[a-zæøåA-ZÆØÅ0-9 ]{4,26}", innSted.getText().toString());

                if (innTypeRegex == false) {
                    feiltype.setText("Skriv inn riktig format for type:(Unngå tall),(Minimum 4 tegn)");
                } else {
                    feiltype.setText("");
                }
                if (innStedRegex == false) {
                    feilsted.setText("Skriv inn riktig format for type:(Unngå tall),(Minimum 4 tegn)");
                } else {
                    feilsted.setText("");
                }
                if (kontakterId == null) {
                    feilKontaktListe.setText("Du må velge kontakter som skal bli med på møtet");
                } else {
                    feilKontaktListe.setText("");
                }

                if (innStedRegex && innTypeRegex && kontakterId != null) {


                    int year = datePicker.getYear();
                    int month = datePicker.getMonth() + 1;
                    int day = datePicker.getDayOfMonth();
                    int hour = timePicker.getCurrentHour();
                    int minute = timePicker.getCurrentMinute();
                   String datoTidFormat = day + "." + month + "." + year + " " + hour + ":" + minute;
                    Date dato = new Date(year, month, day, hour, minute);
                    String sted = innSted.getText().toString();
                    String type = innType.getText().toString();

                    Moote mote = new Moote(datoTidFormat, sted, type);

                    db.leggTilMoote(mote, kontakterId);
                    Toast.makeText(getApplicationContext(), "Lagt til Møte", Toast.LENGTH_SHORT).show();
                    finish();


                }
            }
        });
    }

}