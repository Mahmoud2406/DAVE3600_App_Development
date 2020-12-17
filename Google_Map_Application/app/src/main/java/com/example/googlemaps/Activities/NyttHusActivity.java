package com.example.googlemaps.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.HusRepository;
import com.example.googlemaps.util.GetLocationTask;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class NyttHusActivity extends AppCompatActivity {

    Double longtitude;
    Double latitude;
    EditText beskrivelse;
    TextView husAdresse;
    TextView melding, feil;
    ImageView back;
    public Spinner spin;
    ImageView leggtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nytthus);

        Intent intent = getIntent();
        ArrayList<String> nums = new ArrayList<>();
        for (int i = 1; i <= 14; i++) {
            nums.add(i + " Etasjer");
        }

        beskrivelse = findViewById(R.id.beskrivelse);

        spin = findViewById(R.id.etasjer);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner, nums);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter2);
        melding = findViewById(R.id.gps);
        leggtil = findViewById(R.id.leggtil);
        husAdresse = findViewById(R.id.adresse);
        back = findViewById(R.id.back);
        husAdresse.setText(intent.getStringExtra("adresse"));
        latitude = intent.getDoubleExtra("lat", 0);
        longtitude = intent.getDoubleExtra("long", 0);
        feil = findViewById(R.id.feil);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        melding.setText("Latitude: " + latitude + "\nLongtitude: " + longtitude);
        leggtil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (beskrivelse.getText().toString().isEmpty()
                        || husAdresse.getText().toString().isEmpty()) {

                    feil.setText("Fyll inn gyldig beskrivelse");

                } else {
                    HusRepository task = new HusRepository();

                    String leggtilString = "http://student.cs.hioa.no/~s331361/Apputvk3/lagrehus.php?Beskrivelse=" +
                            beskrivelse.getText().toString().replaceAll(" ", "%20")
                            + "&Gateadresse=" + husAdresse.getText().toString().replaceAll(" ", "%20")
                            + "&Gps=" + latitude + "," + longtitude + "&Etasjer="
                            + spin.getSelectedItem().toString().split(" ")[0];

                    task.leggTilHus(leggtilString);
                    finish();

                }

            }
        });
    }

    //Henter koordinater String fr
    public String hentkoordinater(String Husaddresse) {
        GetLocationTask hentadresser = new GetLocationTask(Husaddresse);

        try {
            return hentadresser.execute().get();
        } catch (ExecutionException e) {
            return "Not Valid Adress";
        } catch (InterruptedException e) {
            return "Not Valid Adress";
        }
    }


}