package com.example.googlemaps.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.googlemaps.Activities.RomActivity;
import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.RomRepository;

import java.util.ArrayList;
// ...

public class NyttRomFragment extends DialogFragment {

    protected Spinner capSpin, beskSpin, etasjeSpin;
    protected TextView startRomnummer, feil;
    protected EditText romnummer;
    protected ArrayList<String> etasjer, beskrivelse;
    protected ArrayList<String> kapasitet;

    protected AdapterView.OnItemSelectedListener etasje;
    protected ImageView back, save;
    protected String hNavn;
    int husId;
    int etasjerInt;

    public NyttRomFragment() {
    }

    public static NyttRomFragment newInstance(String title) {
        NyttRomFragment frag = new NyttRomFragment();
        Bundle args = new Bundle();
        args.putString("Rom", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_addroom, container);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        capSpin = view.findViewById(R.id.kapspin);
        beskSpin = view.findViewById(R.id.beskrivelsespin);
        etasjeSpin = view.findViewById(R.id.etasjespin);

        romnummer = view.findViewById(R.id.romnummer);
        startRomnummer = view.findViewById(R.id.startRomnummer);

        feil = view.findViewById(R.id.txtFeil);
        back = view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment prev = getFragmentManager().findFragmentByTag("addroom_fragment");
                if (prev != null) {
                    DialogFragment df = (DialogFragment) prev;
                    df.dismiss();
                }
            }
        });

        save = view.findViewById(R.id.btnSave);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ////////////////////////////////////////
                if (romnummer.getText().toString().isEmpty()) {
                    feil.setText("Du må fylle inn romnummeret");
                }
                if (romnummer.getText().toString().length() < 2) {
                    feil.setText("Du må fylle inn 2 siffer på romnummeret");
                } else {
                    String rom1 = startRomnummer.getText().toString();
                    String rom2 = romnummer.getText().toString();

                    String romFullStr = rom1 + rom2;
                    System.out.println(romFullStr);
                    String beskrivelseStr = (String) beskSpin.getSelectedItem();
                    String etasjeStr = (String) etasjeSpin.getSelectedItem();
                    etasjeStr = etasjeStr.split("\\.")[0];
                    String kapasitetStr = (String) capSpin.getSelectedItem();
                    int kapasitetInt = Integer.parseInt(kapasitetStr.split(" ")[1]);
                    RomRepository task = new RomRepository();


                    String req = "http://student.cs.hioa.no/~s331361/Apputvk3/lagreRom.php/?"
                            + "Etasje=" + etasjeStr + "&Romnr=" + romFullStr + "&Kapasitet=" + kapasitetInt + "&Beskrivelse=" + beskrivelseStr + "&HusId=" + husId;
                    boolean godkjent = task.leggTilRom(req);

                    if (godkjent) {
                        RomActivity hus = (RomActivity) getActivity();
                        String getString = "http://student.cs.hioa.no/~s331361/Apputvk3/hentRom.php/?HusId=" + husId;
                        hus.romList = task.hentRom(getString);
                        hus.adapter.clear();
                        hus.spin.setSelection(0);
                        hus.listView.setAdapter(hus.adapter);
                        hus.adapter.addAll(hus.romList);

                        Fragment prev = getFragmentManager().findFragmentByTag("addroom_fragment");
                        if (prev != null) {
                            DialogFragment df = (DialogFragment) prev;
                            df.dismiss();
                        }
                    } else {
                        feil.setText("Kunne ikke lagre det nye rommet");
                    }
                }
            }
        });

        husId = getActivity().getIntent().getIntExtra("Id", 0);
        etasjerInt = getActivity().getIntent().getIntExtra("Etasjer", 0);
        hNavn = getActivity().getIntent().getStringExtra("HusNavn");

        // ---------------------------------------------------- Etasjer spin --------------------------------------------------- //
        etasjer = new ArrayList<>();
        for (int i = 1; i <= etasjerInt; i++) {
            etasjer.add(i + ". Etasje");
        }
        ArrayAdapter<String> etsjerADB = new ArrayAdapter<String>(getContext(), R.layout.spinner, etasjer);
        etsjerADB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        etasjeSpin.setAdapter(etsjerADB);


        etasje = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String et = (String) etasjeSpin.getSelectedItem();
                et = et.split("\\.")[0];
                char husStart = hNavn.split(" ")[0].charAt(0);
                startRomnummer.setText(husStart + et);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        etasjeSpin.setOnItemSelectedListener(etasje);
        // ---------------------------------------------------- Kapasitet spin --------------------------------------------------- //
        kapasitet = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            kapasitet.add("Kapasitet " + i);
        }
        ArrayAdapter<String> kapADB = new ArrayAdapter<String>(getContext(), R.layout.spinner, kapasitet);
        kapADB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        capSpin.setAdapter(kapADB);
        // ---------------------------------------------------- Beskrivelse spin --------------------------------------------------- //
        beskrivelse = new ArrayList<>();
        beskrivelse.add("Grupperom");
        beskrivelse.add("Undervisningsrom");
        beskrivelse.add("Auditorium");
        beskrivelse.add("Møterom");
        beskrivelse.add("Leserom");
        beskrivelse.add("Datarom");
        beskrivelse.add("Stillerom");
        ArrayAdapter<String> beskADB = new ArrayAdapter<String>(getContext(), R.layout.spinner, beskrivelse);
        beskADB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beskSpin.setAdapter(beskADB);

    }

}