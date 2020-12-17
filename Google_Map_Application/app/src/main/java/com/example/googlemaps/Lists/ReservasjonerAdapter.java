
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

import com.example.googlemaps.Model.Reservasjon;
import com.example.googlemaps.Model.Rom;
import com.example.googlemaps.R;
import com.example.googlemaps.ServerRepository.ReservasjonRepository;

import java.util.ArrayList;

public class ReservasjonerAdapter extends ArrayAdapter<Reservasjon> {


    private final ArrayList<Reservasjon> dataSet;
    private final ArrayList<Rom> romList;

    Context mContext;
    PopupMenu popup;
    PopupMenu.OnMenuItemClickListener listener;

    public ReservasjonerAdapter(Context context, ArrayList<Reservasjon> res, ArrayList<Rom> romlist) {
        super(context, 0, res);
        this.dataSet = res;
        this.mContext = context;
        this.romList = romlist;
    }


    private static class ViewHolder {
        TextView romNr, dato, fra, til, navn;
        ImageView options;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    private final int lastPosition = -1;

    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Reservasjon dataModel = getItem(position);
        String romNummer = "";
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        for (Rom rom : romList) {
            if (rom.getId() == dataModel.getRomId()) {
                romNummer = rom.getRomnummer();
            }
        }
        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.reservasjonlist, parent, false);
            viewHolder.romNr = convertView.findViewById(R.id.romnr);
            viewHolder.dato = convertView.findViewById(R.id.dato);
            viewHolder.fra = convertView.findViewById(R.id.fra);
            viewHolder.til = convertView.findViewById(R.id.til);
            viewHolder.navn = convertView.findViewById(R.id.Snavn);
            viewHolder.options = convertView.findViewById(R.id.options);
            result = convertView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


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
                            String req = "http://student.cs.hioa.no/~s331361/Apputvk3/slettReservasjon.php/?Id=" + dataModel.getId();
                            ReservasjonRepository task = new ReservasjonRepository();
                            boolean godkjent = task.slettReservasjon(req);
                            if (godkjent) {
                                System.out.println("Slettet");
                                dataSet.remove(position);
                                notifyDataSetChanged();
                                return true;
                            } else {
                                System.out.println("Ikke slettet");
                                Toast.makeText(getContext(), "Klarte ikke å slette reservasjon: " + dataModel.getId() + "", Toast.LENGTH_SHORT).show();
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


        viewHolder.romNr.setText(romNummer + "");
        viewHolder.navn.setText(dataModel.getStudentNavn() + "");
        viewHolder.navn.setText(dataModel.getStudentNavn() + "");
        viewHolder.dato.setText(dataModel.getDato() + "");
        viewHolder.fra.setText(dataModel.getTidFra() + "");
        viewHolder.til.setText(dataModel.getTidTil() + "");
        return convertView;
    }


}