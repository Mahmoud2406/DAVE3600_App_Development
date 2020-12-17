package com.example.s331361.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.s331361.Animation.ClickHandler;
import com.example.s331361.DBHandler;
import com.example.s331361.Activities.EditContactsActivity;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.R;

import java.util.ArrayList;

import static com.amulyakhare.textdrawable.util.ColorGenerator.MATERIAL;

public class AdapterContacts extends ArrayAdapter<Kontakt> {
    private Activity activity;
    private ArrayList<Kontakt> lPerson;
    private static LayoutInflater inflater = null;
    DBHandler db;
    TextView antallKontakter;
    public AdapterContacts(Activity activity, int textViewResourceId) {
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
        public ImageView edit;
        public ImageView delete;
        public TextView display_name;
        public TextView display_number;
        public ImageView display_icon;
        public LinearLayout display_cardColor;
        public long id;


    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        final ViewHolder holder;
        try {
            if (convertView == null) {
                vi = inflater.inflate(R.layout.list_item_contacts, null);
                holder = new ViewHolder();

                holder.display_name = (TextView) vi.findViewById(R.id.name);
                holder.display_number = (TextView) vi.findViewById(R.id.number);
                holder.edit = (ImageView) vi.findViewById(R.id.edit);
                holder.delete = (ImageView) vi.findViewById(R.id.delete);
                holder.display_icon = (ImageView) vi.findViewById(R.id.image_view);

                holder.display_cardColor = (LinearLayout) vi.findViewById(R.id.cardview);





                vi.setTag(holder);
            } else {
                holder = (ViewHolder) vi.getTag();
            }

            String iconLetters = lPerson.get(position).get_Navn().substring(0,2).toUpperCase();



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
            ClickHandler.animateClick(holder.delete,getContext());
            ClickHandler.animateClick(holder.edit,getContext());
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Editing handler
                    Intent intent = new Intent(activity, EditContactsActivity.class);
                    intent.putExtra("key",holder.id);
                    activity.startActivity(intent);
                }
            });



            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.slettKontakt(lPerson.get(position).get_ID());
                    Toast.makeText(activity.getApplicationContext(),"Slettet kontakt",Toast.LENGTH_SHORT).show();
                    lPerson = (ArrayList<Kontakt>) db.finnAlleKontakter();
                    holder.delete.getDrawable().clearColorFilter();
                    notifyDataSetChanged();
                }
            });


        } catch (Exception e) {


        }
        return vi;
    }


}