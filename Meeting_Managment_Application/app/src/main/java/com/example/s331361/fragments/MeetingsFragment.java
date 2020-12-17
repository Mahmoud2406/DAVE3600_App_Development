package com.example.s331361.fragments;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.s331361.Adapters.AdapterMeetings;
import com.example.s331361.Activities.AddMeetingsActivity;
import com.example.s331361.DBHandler;
import com.example.s331361.R;
import android.content.Intent;
import androidx.annotation.Nullable;

import android.widget.ListView;

import com.example.s331361.Model.Kontakt;

import java.util.ArrayList;

public class MeetingsFragment extends Fragment {
    DBHandler db;
    ListView lv;
    ArrayList<Kontakt> list;
    AdapterMeetings adbMeetings;
    public TextView antallMooter;


    public MeetingsFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = new DBHandler(getContext());
        adbMeetings = new AdapterMeetings(getActivity(), 0);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View RootView = inflater.inflate(R.layout.fragment_meetings, container, false);

        ImageView addContacts = RootView.findViewById(R.id.addMeeting);

        addContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddMeetingsActivity.class);
                startActivity(intent);
            }
        });



        antallMooter = RootView.findViewById(R.id.antallmooter);
        lv = RootView.findViewById(R.id.meetinglist);

        lv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                //antallMooter.setText(adbMeetings.getCount()+R.string.mooter);
            }
        });


       // antallMooter.setText(adbMeetings.getCount()+R.string.mooter);
        lv.setAdapter(adbMeetings);
        return RootView;
    }

    private boolean allowRefresh = false;

    @Override
    public void onResume() {
        adbMeetings.notifyDataSetChanged();
        super.onResume();
        if(allowRefresh){
            allowRefresh = false;
            list = (ArrayList<Kontakt>) db.finnAlleKontakter();
            adbMeetings = new AdapterMeetings(getActivity(), 0);
        //    antallMooter.setText(adbMeetings.lMeeting.size()+R.string.mooter);
            lv.setAdapter(adbMeetings);
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