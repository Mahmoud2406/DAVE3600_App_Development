package com.example.googlemaps.Activities;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import com.example.googlemaps.Animation.AnimationClick;
import com.example.googlemaps.Fragments.MapsFragment;
import com.example.googlemaps.Model.Hus;
import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.HusRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ImageView endreHus,  endreReservasjon;
    public ArrayList<Hus> hus = null;
    ArrayList<String> huBeskrivelse;
    public Spinner spin;
    LinearLayout info,info2,parent,vKnapp,hKnapp, maps_background;
    Fragment fragment;
    ImageView close,clos2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        fragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

//---------------------fill Hus Spinner----------------------//
        HusRepository task = new HusRepository();
        hus = task.hentAlleHus();

        if (hus != null) {
            huBeskrivelse = new ArrayList<>();
            huBeskrivelse.add("Velg hus");
            for (int i = 0; i < hus.size(); i++) {
                String beskrivelse = hus.get(i).getGateadresse();
                String str = beskrivelse.split(",")[0];
                huBeskrivelse.add(str);
            }

            spin = findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, huBeskrivelse);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(adapter);
            spin.setOnItemSelectedListener(this);
        }


        //-------------------------------------------
        endreHus = findViewById(R.id.endreHus);
        endreReservasjon = findViewById(R.id.endreReservasjon);

        parent = findViewById(R.id.parent);
        info2 = findViewById(R.id.info2);
        clos2 = findViewById(R.id.closeinfo2);
        info = findViewById(R.id.infolayout);
        close = findViewById(R.id.slettInfo);

        maps_background = findViewById(R.id.maps_back);
        vKnapp = findViewById(R.id.vKnapp);
        hKnapp = findViewById(R.id.hKnapp);
        clos2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info2.setVisibility(View.INVISIBLE);
                parent.removeView(info2);
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                info.setVisibility(View.INVISIBLE);
                parent.removeView(info);
            }
        });

        endreHus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationClick.AnimateView(getApplicationContext(),v);
                Intent i = new Intent(getApplicationContext(), RomActivity.class);
                i.putExtra("Id", hus.get(spin.getSelectedItemPosition()-1).getId());
                i.putExtra("HusNavn", hus.get(spin.getSelectedItemPosition()-1).getGateadresse());
                i.putExtra("Etasjer", hus.get(spin.getSelectedItemPosition()-1).getEtasjer());
                startActivityForResult(i, 100);

            }
        });

        endreReservasjon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationClick.AnimateView(getApplicationContext(),v);
                Intent i = new Intent(getApplicationContext(), ReservasjonerActivity.class);
                i.putExtra("Id", hus.get(spin.getSelectedItemPosition()-1).getId());
                i.putExtra("HusNavn", hus.get(spin.getSelectedItemPosition()-1).getGateadresse());
                i.putExtra("Etasjer", hus.get(spin.getSelectedItemPosition()-1).getEtasjer());
                startActivityForResult(i, 200);

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        if(position == 0) {
            MapsFragment fragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            fragment.unactivate();
            vKnapp.setVisibility(View.INVISIBLE);
            hKnapp.setVisibility(View.INVISIBLE);
            maps_background.setForeground(null);
        }
        else {
            MapsFragment fragment = (MapsFragment) getSupportFragmentManager().findFragmentById(R.id.container);
            fragment.activateMarker(hus.get(position-1).getId());
            vKnapp.setVisibility(View.VISIBLE);
            hKnapp.setVisibility(View.VISIBLE);
            maps_background.setForeground( ContextCompat.getDrawable(getApplicationContext(), R.drawable.googlemaps_gradient));

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO - Custom Code
    }




    @Override
    protected void onResume() {
        super.onResume();

        HusRepository task = new HusRepository();
        hus = task.hentAlleHus();
        if (hus != null) {

            huBeskrivelse = new ArrayList<>();
            huBeskrivelse.add("Velg hus");
            for (int i = 0; i < hus.size(); i++) {
                String beskrivelse = hus.get(i).getGateadresse();
                String str = beskrivelse.split(",")[0];
                huBeskrivelse.add(str);
            }

            spin = findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner, huBeskrivelse);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spin.setAdapter(adapter);
            spin.setOnItemSelectedListener(this);
        }
        fragment = new MapsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

    }


    public void setSelected(int id) {
        if(id == -1) {
            spin.setSelection(0);
            vKnapp.setVisibility(View.INVISIBLE);
            hKnapp.setVisibility(View.INVISIBLE);
            maps_background.setForeground(null);
        }
        else {
            for (int i = 0; i < hus.size(); i++) {
                if(hus.get(i).getId() == id) {
                    spin.setSelection(i+1);
                    break;
                }
            }
            vKnapp.setVisibility(View.VISIBLE);
            hKnapp.setVisibility(View.VISIBLE);
            maps_background.setForeground( ContextCompat.getDrawable(getApplicationContext(), R.drawable.googlemaps_gradient));
        }

    }
}

