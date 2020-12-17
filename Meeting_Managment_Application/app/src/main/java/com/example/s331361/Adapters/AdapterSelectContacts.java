package com.example.s331361.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.s331361.Animation.ClickHandler;
import com.example.s331361.DBHandler;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.R;

import java.util.ArrayList;
import java.util.List;

import static com.amulyakhare.textdrawable.util.ColorGenerator.MATERIAL;

public class AdapterSelectContacts extends ArrayAdapter<Kontakt> {
    private Activity activity;
    public ArrayList<Kontakt> lPerson;
    private static LayoutInflater inflater = null;
    DBHandler db;
    TextView antallKontakter;
    public AdapterSelectContacts(Activity activity, int textViewResourceId) {
        super(activity, textViewResourceId);
        try {
            this.activity = activity;
            db = new DBHandler(getContext());
            this.lPerson = (ArrayList<Kontakt>) db.finnAlleKontakter();

            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        } catch (Exception e) {

        }
    }

    public int getCount() {
        return lPerson.size();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder {
        public CheckBox display_checkbox;
        public TextView display_name;
        public TextView display_number;
        public ImageView display_icon;
        public long id;


    }
    public void selectAll(boolean isSelect) {
        for (Kontakt person : lPerson) {
            person.setIsselected(isSelect);
        }
    }



    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_selectcontacts, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.name);
                holder.display_number = (TextView) vi.findViewById(R.id.number);
                holder.display_checkbox = (CheckBox) vi.findViewById(R.id.checkContact);
                holder.display_icon = (ImageView) vi.findViewById(R.id.image_view);




                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            String iconLetters = lPerson.get(position).get_Navn().substring(0,2).toUpperCase();

            holder.display_checkbox.setChecked(lPerson.get(position).isselected());
            holder.display_checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (lPerson.get(position).isselected()) {
                        lPerson.get(position).setIsselected(false);
                    } else {
                        lPerson.get(position).setIsselected(true);
                    }
                }
            });

            TextDrawable drawable = TextDrawable.builder()
                    .beginConfig()
                    .width(60)
                    .height(60)
                    .endConfig()
                    .buildRound(iconLetters, MATERIAL.getColor(iconLetters));
            holder.display_icon.setImageDrawable(drawable);
            holder.display_name.setText(lPerson.get(position).get_Navn());
            holder.display_number.setText(lPerson.get(position).get_Telefon());
            holder.id =  lPerson.get(position).get_ID();


        } catch (Exception e) {


        }
        return vi;
    }


}