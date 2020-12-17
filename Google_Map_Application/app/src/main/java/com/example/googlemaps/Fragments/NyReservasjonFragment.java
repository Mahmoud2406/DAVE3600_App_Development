package com.example.googlemaps.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.googlemaps.Activities.ReservasjonerActivity;
import com.example.googlemaps.Model.Reservasjon;
import com.example.googlemaps.Model.Rom;
import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.ReservasjonRepository;
import com.example.googlemaps.ServerRepository.RomRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
// ...

public class NyReservasjonFragment extends DialogFragment {

    protected Spinner romSpin, fraSpin, tilSpin;
    protected DatePicker dato;
    protected TextView startRomnummer, feil;
    protected EditText sNavn;
    protected ArrayList<String> romNavn, beskrivelse;
    protected ArrayList<String> kapasitet, fraArr, tilArr;
    protected ArrayList<Integer> romId;

    protected AdapterView.OnItemSelectedListener romL;
    protected ImageView back, save;
    protected String hNavn;
    int husId;
    boolean ledig;
    ArrayList<Rom> romList;
    int etasjerInt;
    int romIdInt;

    public NyReservasjonFragment() {
    }

    public static NyReservasjonFragment newInstance(String title) {
        NyReservasjonFragment frag = new NyReservasjonFragment();
        Bundle args = new Bundle();
        args.putString("Reservasjon", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_legg_til_reservasjon, container);
        return root;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fraSpin = view.findViewById(R.id.fraspin);
        tilSpin = view.findViewById(R.id.tilspin);
        romSpin = view.findViewById(R.id.romSpin);
        sNavn = view.findViewById(R.id.sNavn);
        husId = getActivity().getIntent().getIntExtra("Id", 0);
        etasjerInt = getActivity().getIntent().getIntExtra("Etasjer", 0);
        hNavn = getActivity().getIntent().getStringExtra("HusNavn");

        ledig = true;
        dato = view.findViewById(R.id.dato);
        feil = view.findViewById(R.id.txtFeil);
        back = view.findViewById(R.id.btnBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment prev = getFragmentManager().findFragmentByTag("fragment_legg_til_reservasjon");
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
                System.out.println(check() + "---------------------------------------------------------kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
                if (check()) {
                    GregorianCalendar calendarBeg = new GregorianCalendar(dato.getYear(),
                            dato.getMonth(), dato.getDayOfMonth());
                    SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
                    Date begin = calendarBeg.getTime();
                    String strDate = dateFormatter.format(begin);

                    String fra = (String) fraSpin.getSelectedItem();
                    fra = fra.replaceAll(":", "%3A");
                    String til = (String) tilSpin.getSelectedItem();
                    til = til.replaceAll(":", "%3A");
                    int romId = romIdInt;
                    String navn = sNavn.getText().toString();
                    ReservasjonRepository task = new ReservasjonRepository();


                    if (isFullname(navn) && !navn.isEmpty()) {


                        String leggtilString = "http://student.cs.hioa.no/~s331361/Apputvk3/lagreReservasjoner.php/?Dato=" +
                                strDate +
                                "&Tidfra=" +
                                fra +
                                "&Tidtil=" +
                                til +
                                "&Romid=" +
                                romId +
                                "&Studentnavn=" +
                                navn.replaceAll(" ", "%20");

                        System.out.println(leggtilString);
                        boolean godkjent = task.leggTilReservasjoner(leggtilString);


                        if (godkjent) {
                            Fragment prev = getFragmentManager().findFragmentByTag("fragment_legg_til_reservasjon");
                            if (prev != null) {
                                ReservasjonerActivity act = (ReservasjonerActivity) getActivity();

                                ArrayList<Reservasjon> list = task.hentReservasjoner();
                                act.ReservasjonList = list;
                                act.adapter.clear();
                                act.roomspin.setSelection(0);
                                act.listView.setAdapter(act.adapter);
                                act.adapter.addAll(act.ReservasjonList);
                                DialogFragment df = (DialogFragment) prev;
                                df.dismiss();
                            }
                            Toast toast = Toast.makeText(getContext(),
                                    "Lagret reservasjon!",
                                    Toast.LENGTH_SHORT);

                            toast.show();
                        } else {
                            feil.setText("Kunne ikke lagre det nye rommet");
                        }


                    } else {
                        feil.setText("Du må skrive inn gyldige tegn på navn");
                    }
                }
            }
        });

        // ---------------------------------------------------- Dato --------------------------------------------------- //
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

// set current date into datepicker
        dato.init(year, month, day, null);

        // ---------------------------------------------------- Rom spin --------------------------------------------------- //
        RomRepository task = new RomRepository();
        String getString = "http://student.cs.hioa.no/~s331361/Apputvk3/hentRom.php/?HusId=" + husId;
        romList = task.hentRom(getString);
        if (romList != null) {
            romNavn = new ArrayList<>();
            romId = new ArrayList<>();
            for (Rom rom : romList) {
                romNavn.add(rom.getRomnummer());
                romId.add(rom.getId());
            }
        }

        ArrayAdapter<String> romNavnADB = new ArrayAdapter<String>(getContext(), R.layout.spinner, romNavn);
        romNavnADB.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        romSpin.setAdapter(romNavnADB);

        romL = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                romIdInt = romId.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };

        romSpin.setOnItemSelectedListener(romL);
        // ---------------------------------------------------- Fra spin --------------------------------------------------- //
        fraArr = new ArrayList<>();
        for (int i = 10; i <= 22; i += 1) {
            fraArr.add(i + ":00");
        }
        final ArrayAdapter<String> fraAdp = new ArrayAdapter<String>(getContext(), R.layout.spinner, fraArr);
        fraAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fraSpin.setAdapter(fraAdp);
        AdapterView.OnItemSelectedListener fraL = new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < fraArr.size() - 1) {
                    ArrayList<String> al2 = new ArrayList<String>(tilArr.subList(i + 1, tilArr.size()));
                    ArrayAdapter<String> tilAdp = new ArrayAdapter<String>(getContext(), R.layout.spinner, al2);
                    tilAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tilSpin.setAdapter(tilAdp);
                }
                if (i == fraArr.size() - 1) {
                    ArrayList<String> tilArr2 = new ArrayList<>();
                    tilArr2.add(tilArr.get(tilArr.size() - 1));
                    ArrayList<String> al2 = new ArrayList<String>(tilArr2);
                    ArrayAdapter<String> tilAdp = new ArrayAdapter<String>(getContext(), R.layout.spinner, al2);
                    tilAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    tilSpin.setAdapter(tilAdp);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        };
        fraSpin.setOnItemSelectedListener(fraL);
        // ---------------------------------------------------- Til spin --------------------------------------------------- //
        tilArr = new ArrayList<>();
        for (int i = 10; i <= 23; i += 1) {
            tilArr.add(i + ":00");
        }
        ArrayAdapter<String> tilAdp = new ArrayAdapter<String>(getContext(), R.layout.spinner, tilArr);
        tilAdp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        tilSpin.setAdapter(tilAdp);


    }

    public static boolean isFullname(String str) {
        return str.matches("^[a-zæøåA-ZÆØÅ ]*$");
    }

    public boolean check() {

        GregorianCalendar calendarBeg = new GregorianCalendar(dato.getYear(),
                dato.getMonth(), dato.getDayOfMonth());
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        Date begin = calendarBeg.getTime();
        String strDate = dateFormatter.format(begin);

        String fra = (String) fraSpin.getSelectedItem();
        int fraInteger = Integer.parseInt(fra.split(":")[0]);
        String til = (String) tilSpin.getSelectedItem();
        int tilInteger = Integer.parseInt(til.split(":")[0]);

        boolean sammedag = false;

        ArrayList<Reservasjon> ReservasjonList;
        ReservasjonRepository task = new ReservasjonRepository();
        ReservasjonList = task.hentReservasjoner();

        if (ReservasjonList != null) {
            ArrayList<String> romReservasjoner = new ArrayList<>();

            ArrayList<String> romReservasjonerAltdetAndre = new ArrayList<>();
            for (Reservasjon reservasjon : ReservasjonList) {
                if (reservasjon.getRomId() == romIdInt) {
                    romReservasjoner.add(reservasjon.getTidFra() + "." + reservasjon.getTidTil() + "_" + reservasjon.getDato());
                }
            }

            for (String s : romReservasjoner) {
                String from = s.split("_")[0].split("\\.")[0].split(":")[0];
                String to = s.split("_")[0].split("\\.")[1].split(":")[0];
                int fraomInt = Integer.parseInt(from);
                int toInt = Integer.parseInt(to);

                if (!((fraInteger >= fraomInt && fraInteger < toInt) || (tilInteger > fraomInt && tilInteger <= toInt) || (fraInteger == fraomInt && tilInteger == toInt))) {
                    ledig = true;
                } else {
                    String dato = s.split("_")[1];
                    if (strDate.equals(dato)) {
                        ledig = false;
                        break;
                    } else {
                        ledig = true;
                    }
                }

            }
            if (!ledig) {
                Toast toast = Toast.makeText(getContext(),
                        "Det er ikke ledig",
                        Toast.LENGTH_SHORT);

                toast.show();
            }

        } else {
            ledig = true;
        }

        return ledig;
    }

}