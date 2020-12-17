package com.example.googlemaps.Lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.widget.PopupMenu;

import com.example.googlemaps.Model.Rom;
import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.RomRepository;

import java.util.ArrayList;

public class RomAdapter extends ArrayAdapter<Rom> {

    private final ArrayList<Rom> dataSet;
    Context mContext;
    PopupMenu popup;
    PopupMenu.OnMenuItemClickListener listener;

    public RomAdapter(Context context, ArrayList<Rom> rooms) {
        super(context, 0, rooms);
        this.dataSet = rooms;
        this.mContext = context;
    }


    private static class ViewHolder {
        TextView romNr, kap, besk, etasje;
        ImageView options;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private final int lastPosition = -1;

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Rom dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.roomlist, parent, false);
            viewHolder.romNr = convertView.findViewById(R.id.romnr);
            viewHolder.kap = convertView.findViewById(R.id.kapasitet);
            viewHolder.besk = convertView.findViewById(R.id.besk);
            viewHolder.options = convertView.findViewById(R.id.options);
            viewHolder.etasje = convertView.findViewById(R.id.etasjeNr);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        /*Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;
*/


        viewHolder.options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.roommenu, popup.getMenu());

                listener = new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getItemId() == R.id.slett) {
                            String req = "http://student.cs.hioa.no/~s331361/Apputvk3/slettRom.php/?Id=" + dataModel.getId();
                            RomRepository task = new RomRepository();
                            boolean godkjent = task.slettRom(req);
                            if (godkjent) {
                                System.out.println("Slettet");
                                Toast.makeText(getContext(), "Slettet rom: " + dataModel.getRomnummer() + "", Toast.LENGTH_SHORT).show();
                                dataSet.remove(position);
                                notifyDataSetChanged();
                                return true;
                            } else {
                                System.out.println("Ikke slettet");
                                Toast.makeText(getContext(), "Klarte ikke å slette rom: " + dataModel.getRomnummer() + "", Toast.LENGTH_SHORT).show();
                                return false;

                            }

                        }
                        return false;
                    }
                };

                popup.setOnMenuItemClickListener(listener);
                popup.show();
            }

        });


        viewHolder.etasje.setText(dataModel.getEtasjenr() + "");
        viewHolder.besk.setText(dataModel.getBeskrivelse());
        viewHolder.kap.setText(dataModel.getKapasitet());
        viewHolder.romNr.setText(dataModel.getRomnummer() + "");
        return convertView;
    }


}