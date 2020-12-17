package com.example.googlemaps.Activities;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.FragmentManager;

import com.example.googlemaps.Animation.AnimationClick;
import com.example.googlemaps.Fragments.NyttRomFragment;
import com.example.googlemaps.Lists.RomAdapter;
import com.example.googlemaps.Model.Rom;
import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.HusRepository;
import com.example.googlemaps.ServerRepository.RomRepository;

import java.util.ArrayList;

public class RomActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    public int husId, etasjerInt;
    TextView husNavn;
    public Spinner spin;
    public ArrayList<Rom> romList, romListFilter;
    String hNavn;
    ImageView back, addRoom, sletthus;
    public ListView listView;
    AdapterView.OnItemSelectedListener floorL;
    public RomAdapter adapter;
    PopupMenu.OnMenuItemClickListener listener;
    public ArrayList<String> etasjer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rom);

        husId = getIntent().getIntExtra("Id", 0);
        hNavn = getIntent().getStringExtra("HusNavn");
        etasjerInt = getIntent().getIntExtra("Etasjer", 0);


        RomRepository task = new RomRepository();
        String getString = "http://student.cs.hioa.no/~s331361/Apputvk3/hentRom.php/?HusId=" + husId;
        romList = task.hentRom(getString);

        husNavn = findViewById(R.id.husNavn);
        husNavn.setText(hNavn.split(",")[0]);

        back = findViewById(R.id.back);

        sletthus = findViewById(R.id.sletthus);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        addRoom = findViewById(R.id.addRoom);

        addRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationClick.AnimateView(getApplicationContext(), view);
                FragmentManager fm = getSupportFragmentManager();
                NyttRomFragment editNameDialogFragment = NyttRomFragment.newInstance("Rom");
                editNameDialogFragment.show(fm, "addroom_fragment");
            }
        });
        listView = findViewById(R.id.listview);

        if (romList != null) {
            ArrayList<Rom> arr = new ArrayList<Rom>();
            adapter = new RomAdapter(this, arr);

            listView.setAdapter(adapter);
            adapter.addAll(romList);


        } else {
            ArrayList<Rom> arr = new ArrayList<Rom>();
            adapter = new RomAdapter(this, arr);
            TextView emptyList = findViewById(R.id.emptyList);

            listView.setEmptyView(emptyList);
            emptyList.setText("Fant ikke rom i dette huset");
        }


        etasjer = new ArrayList<>();
        etasjer.add("Vis alle etasjer");
        for (int i = 1; i <= etasjerInt; i++) {
            etasjer.add(i + ". Etasje");
        }

        spin = findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner, etasjer);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter2);


        floorL = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (etasjer.get(i) == "Vis alle etasjer") {
                    romListFilter = new ArrayList<>();
                    if (romList != null) {
                        for (Rom rom : romList) {
                            romListFilter.add(rom);
                        }
                    }
                    adapter.clear();
                    adapter.addAll(romListFilter);
                } else {
                    String etasjenr = etasjer.get(i).split("\\.")[0];
                    int nr = Integer.parseInt(etasjenr);
                    if (romList != null) {
                        romListFilter = new ArrayList<>();
                        for (Rom rom : romList) {
                            if (rom.getEtasjenr() == nr) {
                                romListFilter.add(rom);
                            }
                        }
                        if (romListFilter.isEmpty()) {
                            adapter.clear();
                            TextView emptyList = findViewById(R.id.emptyList);
                            listView.setEmptyView(emptyList);
                            emptyList.setText("Fant ikke rom i denne etasjen");

                        } else {
                            adapter.clear();
                            adapter.addAll(romListFilter);
                        }

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        };


        spin.setOnItemSelectedListener(floorL);


    }

    public void showMenu(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.husmenu);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.slett) {
            HusRepository task = new HusRepository();
            String req = "http://student.cs.hioa.no/~s331361/Apputvk3/slettHus.php/?Id=" + husId;
            boolean godkjent = task.slettHus(req);
            if (godkjent) {
                Toast.makeText(getApplicationContext(), "Slettet hus: " + hNavn, Toast.LENGTH_SHORT).show();
                finish();
                return true;
            } else {
                System.out.println("Ikke slettet");
                Toast.makeText(getApplicationContext(), "Klarte ikke å slette huset", Toast.LENGTH_SHORT).show();
                return false;

            }
        }
        return false;
    }
}