package com.example.s331361.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.s331361.Activities.EditContactsActivity;
import com.example.s331361.Activities.ShowContactsActivity;
import com.example.s331361.Animation.ClickHandler;
import com.example.s331361.DBHandler;
import com.example.s331361.Activities.EditMeetingsActivity;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.Model.Moote;
import com.example.s331361.R;

import java.security.PublicKey;
import java.util.ArrayList;

public class AdapterMeetings extends ArrayAdapter<Kontakt> {
    private Activity activity;
    public ArrayList<Moote> lMeeting;
    private static LayoutInflater inflater = null;
    DBHandler db;

    public AdapterMeetings(Activity activity, int textViewResourceId) {
        super(activity, textViewResourceId);
        try {
            this.activity = activity;
            db = new DBHandler(getContext());
            this.lMeeting = (ArrayList<Moote>) db.finnAlleMooter();


            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lMeeting.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public ImageView edit;
        public ImageView delete;
        public ImageView showContacts;
        public TextView display_type;
        public TextView display_place;
        public TextView display_time;

        public long id;
    }

    @SuppressLint("SetTextI18n")
    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_item_meetings, null);
                holder = new ViewHolder();

                holder.display_type = (TextView) vi.findViewById(R.id.type);
                holder.display_place = (TextView) vi.findViewById(R.id.place);
                holder.edit = (ImageView) vi.findViewById(R.id.edit);
                holder.delete = (ImageView) vi.findViewById(R.id.delete);
                holder.display_time = (TextView) vi.findViewById(R.id.time);
                holder.showContacts = vi.findViewById(R.id.showContacts);

                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }


            holder.display_type.setText("Type: " + lMeeting.get(position).get_Type());
            holder.display_place.setText("Sted: " + lMeeting.get(position).get_Sted());
            holder.display_time.setText("Dato: " + lMeeting.get(position).get_Tidspunkt());
            holder.id = lMeeting.get(position).get_ID();
            ClickHandler.animateClick(holder.delete, getContext());
            ClickHandler.animateClick(holder.edit, getContext());
            ClickHandler.animateClick(holder.showContacts, getContext());

            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Editing handler
                    Intent intent = new Intent(getContext(), EditMeetingsActivity.class);
                    intent.putExtra("key", holder.id);
                    activity.startActivity(intent);
                }
            });


            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.slettMoote(lMeeting.get(position).get_ID());
                    Toast.makeText(activity.getApplicationContext(), "Slettet møte", Toast.LENGTH_SHORT).show();
                    lMeeting = (ArrayList<Moote>) db.finnAlleMooter();
                    notifyDataSetChanged();
                }
            });

            holder.showContacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(activity, ShowContactsActivity.class);
                    intent.putExtra("key",holder.id);
                    activity.startActivity(intent);
                }
            });


        } catch (Exception e) {
            e.printStackTrace();

        }
        return vi;
    }


}