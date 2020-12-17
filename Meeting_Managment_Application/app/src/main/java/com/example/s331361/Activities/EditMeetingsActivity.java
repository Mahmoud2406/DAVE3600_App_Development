package com.example.s331361.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.s331361.DBHandler;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.Model.Moote;
import com.example.s331361.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class EditMeetingsActivity extends AppCompatActivity {
    long id;
    EditText innType;
    EditText innSted;
    DatePicker datePicker;
    TimePicker timePicker;
    DBHandler db;
    TextView feiltype, feilsted,feilKontaktListe;
    EditText antall;
    ImageView editList;
    ArrayList<Kontakt> kontakter;
    ArrayList<Long> nykontakterid;
    ArrayList<Long> kontakterid;
    int moote_ID;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if(resultCode == RESULT_OK) {
                nykontakterid = (ArrayList<Long>) data.getSerializableExtra("kontakter");
                antall.setText("Antall lagt til: " + nykontakterid.size());
            }
            System.out.println(RESULT_OK);

        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meetings);

        timePicker = findViewById(R.id.timePicker);
        datePicker = findViewById(R.id.datePicker);
        feilKontaktListe = findViewById(R.id.feilKontaktListe);
        antall = findViewById(R.id.antall);
        innSted = findViewById(R.id.innSted);
        innType = findViewById(R.id.innType);
        editList = findViewById(R.id.editList);
        feilsted = findViewById(R.id.feilsted);
        feiltype = findViewById(R.id.feiltype);

        Intent intent = getIntent();
        moote_ID = (int) intent.getExtras().getLong("key");
        db = new DBHandler(this);
        Moote moote = db.finnMoote(moote_ID);
        kontakter = db.finnDeltagere(moote_ID);


        kontakterid = new ArrayList<>();
        for (Kontakt kontakt : kontakter) {
            kontakterid.add(kontakt.get_ID());
        }
            antall.setText("Antall lagt til: " + kontakterid.size());


        editList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),SelectContactsActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        id = moote.get_ID();
        innType.setText(moote.get_Type());
        innSted.setText(moote.get_Sted());
        String tidspunkt = moote.get_Tidspunkt();

        String[] str = tidspunkt.split(" ");
        String[] dato = str[0].split("\\.");
        String[] tid = str[1].split(":");

        int datoInt0 = Integer.parseInt(dato[0]);
        int datoInt1 = Integer.parseInt(dato[1]);
        int datoInt2 = Integer.parseInt(dato[2]);

        System.out.println(datoInt0 + " " + datoInt1 + " " + datoInt2);

        datePicker.init(datoInt2,datoInt1-1,datoInt0,null);

        timePicker.setCurrentHour(Integer.parseInt(tid[0]));
        timePicker.setCurrentMinute(Integer.parseInt(tid[1]));

        Button editMeeting = findViewById(R.id.editMeeting);
        editMeeting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                boolean innTypeRegex = Pattern.matches("[a-zæøåA-ZÆØÅ ]{4,26}", innType.getText().toString());
                boolean innStedRegex = Pattern.matches("[a-zæøåA-ZÆØÅ0-9 ]{4,26}", innSted.getText().toString());

                if (innTypeRegex == false) {
                    feiltype.setText("Skriv inn riktig format for navn: (Unngå tall),(Minimum 4 tegn)");
                } else {
                    feiltype.setText("");
                }
                if (innStedRegex == false) {
                    feilsted.setText("Skriv inn riktig format for navn: (Unngå tall),(Minimum 4 tegn)");
                } else {
                    feilsted.setText("");
                }

                if (innStedRegex && innTypeRegex) {
                    int year = datePicker.getYear();
                    int month = datePicker.getMonth()+1;
                    int day = datePicker.getDayOfMonth();

                    int hour = timePicker.getCurrentHour();
                    int minute = timePicker.getCurrentMinute();
                    String datoTidFormat = day + "." + month + "." + year + " " + hour + ":" + minute;

                    String sted = innSted.getText().toString();
                    String type = innType.getText().toString();

                    Moote mote = new Moote(moote_ID,datoTidFormat,sted,type);

                    if(nykontakterid!=null){
                        db.oppdaterMoote(mote,nykontakterid);
                    }
                    else {
                        db.oppdaterMoote(mote,kontakterid);
                    }
                    Toast.makeText(getApplicationContext(), "Redigert Møte", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

        });
    }
}