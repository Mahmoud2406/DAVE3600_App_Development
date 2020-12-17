package com.example.googlemaps.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.googlemaps.Animation.AnimationClick;
import com.example.googlemaps.Fragments.NyReservasjonFragment;
import com.example.googlemaps.Lists.ReservasjonerAdapter;
import com.example.googlemaps.Model.Reservasjon;
import com.example.googlemaps.Model.Rom;
import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.ReservasjonRepository;
import com.example.googlemaps.ServerRepository.RomRepository;

import java.util.ArrayList;

public class ReservasjonerActivity extends AppCompatActivity {
    public int husId, etasjerInt;
    TextView husNavn;
    public Spinner roomspin;
    public ArrayList<Reservasjon> ReservasjonList, ReservasjonListFilter;
    ArrayList<Rom> romList;
    String hNavn;
    ImageView back;
    ImageButton addreservasjon;
    public ListView listView;
    AdapterView.OnItemSelectedListener roomL;
    public ReservasjonerAdapter adapter;

    public ArrayList<String> rooms;
    ArrayList<Integer> roomsId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservasjoner);


        husId = getIntent().getIntExtra("Id", 0);
        hNavn = getIntent().getStringExtra("HusNavn");
        etasjerInt = getIntent().getIntExtra("Etasjer", 0);


        RomRepository taskRom = new RomRepository();
        String getString = "http://student.cs.hioa.no/~s331361/Apputvk3/hentRom.php/?HusId=" + husId;
        romList = taskRom.hentRom(getString);


        husNavn = findViewById(R.id.husNavn);
        husNavn.setText(hNavn.split(",")[0]);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addreservasjon = findViewById(R.id.resBtn);


        addreservasjon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AnimationClick.AnimateView(getApplicationContext(), view);
                FragmentManager fm = getSupportFragmentManager();
                NyReservasjonFragment editNameDialogFragment = NyReservasjonFragment.newInstance("Reservasjon");
                editNameDialogFragment.show(fm, "fragment_legg_til_reservasjon");
            }
        });


        //---------------------------------------------------------------- Reservasjon Listview  (Start) ------------------------------------------///

        ReservasjonRepository task = new ReservasjonRepository();
        ReservasjonList = task.hentReservasjoner();
        listView = findViewById(R.id.listview);

        if (ReservasjonList != null) {
            ArrayList<Reservasjon> arr = new ArrayList<Reservasjon>();
            adapter = new ReservasjonerAdapter(this, arr, romList);

            listView.setAdapter(adapter);
            adapter.addAll(ReservasjonList);
            System.out.println(ReservasjonList.toString());


        } else {
            ArrayList<Reservasjon> arr = new ArrayList<Reservasjon>();
            adapter = new ReservasjonerAdapter(this, arr, romList);
            TextView emptyList = findViewById(R.id.emptyList);

            listView.setEmptyView(emptyList);
            emptyList.setText("Fant ikke Reservasjon i dette huset");
        }
        //---------------------------------------------------------------- Reservasjon Listview  (End) ------------------------------------------///


        //---------------------------------------------------------------- Room spinner  (Start) ------------------------------------------///
        RomRepository task2 = new RomRepository();
        String getString2 = "http://student.cs.hioa.no/~s331361/Apputvk3/hentRom.php/?HusId=" + husId;
        romList = task2.hentRom(getString2);

        if (romList != null) {
            rooms = new ArrayList<>();
            rooms.add("Vis alle rom");
            for (Rom rom : romList) {
                rooms.add(rom.getRomnummer());
            }
            roomsId = new ArrayList<>();
            roomsId.add(-99);
            for (Rom rom : romList) {
                roomsId.add(rom.getId());
            }
            roomspin = findViewById(R.id.spinner1);
            ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner, rooms);
            adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            roomspin.setAdapter(adapter2);


            roomL = new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (rooms.get(i) == "Vis alle rom") {

                        ReservasjonListFilter = new ArrayList<>();
                        if (ReservasjonList != null) {
                            for (int j = 0; j < roomsId.size(); j++) {
                                int idInt = roomsId.get(j);
                                for (Reservasjon Reservasjon : ReservasjonList) {
                                    if (Reservasjon.getRomId() == idInt) {
                                        ReservasjonListFilter.add(Reservasjon);
                                    }
                                }
                            }
                        }
                        if (ReservasjonListFilter.isEmpty()) {
                            adapter.clear();
                            TextView emptyList = findViewById(R.id.emptyList);
                            listView.setEmptyView(emptyList);
                            emptyList.setText("Fant ikke reservasjoner for dette huset");

                        } else {
                            adapter.clear();
                            adapter.addAll(ReservasjonListFilter);
                        }
                    } else {
                        int id = roomsId.get(i);
                        if (ReservasjonList != null) {
                            ReservasjonListFilter = new ArrayList<>();
                            for (Reservasjon Reservasjon : ReservasjonList) {
                                if (Reservasjon.getRomId() == id) {
                                    ReservasjonListFilter.add(Reservasjon);
                                }
                            }
                            if (ReservasjonListFilter.isEmpty()) {
                                adapter.clear();
                                TextView emptyList = findViewById(R.id.emptyList);
                                listView.setEmptyView(emptyList);
                                emptyList.setText("Fant ikke reservasjoner for dette rommet");

                            } else {
                                adapter.clear();
                                adapter.addAll(ReservasjonListFilter);
                            }

                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            };


            roomspin.setOnItemSelectedListener(roomL);

            //---------------------------------------------------------------- Room spinner (end) ------------------------------------------///

        } else {
            adapter.clear();
            TextView emptyList = findViewById(R.id.emptyList);
            listView.setEmptyView(emptyList);
            emptyList.setText("Fant ikke Rom i dette huset");
            addreservasjon.setVisibility(View.INVISIBLE);

        }
    }

}