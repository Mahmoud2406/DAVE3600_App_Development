package com.example.s331361.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import android.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.s331361.Adapters.AdapterContacts;
import com.example.s331361.Activities.AddContactsActivity;
import com.example.s331361.DBHandler;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.R;

import java.util.ArrayList;

public class ContactsFragment extends Fragment {
    DBHandler db;
    ListView lv;
    ArrayList<Kontakt> list;
    AdapterContacts adbContact;
    TextView antallKontakter;


    public ContactsFragment() {
        // Required empty public constructor
    }

    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        db = new DBHandler(getContext());
        adbContact = new AdapterContacts(getActivity(), 0);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        View RootView = inflater.inflate(R.layout.fragment_contacts, container, false);

        ImageView addContacts = RootView.findViewById(R.id.addContacts);

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddContactsActivity.class);
                startActivity(intent);
            }
        });

        antallKontakter = RootView.findViewById(R.id.antallkontakter);
        lv = RootView.findViewById(R.id.contactList);

        lv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                antallKontakter.setText(adbContact.getCount()+" kontakter");
            }
        });

        antallKontakter.setText(adbContact.getCount()+" kontakter");
        lv.setAdapter(adbContact);


        return RootView;
    }

    private boolean allowRefresh = false;

    @Override
    public void onResume() {
        adbContact.notifyDataSetChanged();
        super.onResume();
        if(allowRefresh){
            allowRefresh = false;
            antallKontakter.setText(db.finnAntallKontakter()+" kontakter");
            list = (ArrayList<Kontakt>) db.finnAlleKontakter();
            adbContact = new AdapterContacts(getActivity(), 0);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



    }
}