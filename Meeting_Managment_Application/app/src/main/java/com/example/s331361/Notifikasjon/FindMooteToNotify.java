package com.example.s331361.Notifikasjon;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.telephony.SmsManager;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import com.example.s331361.DBHandler;
import com.example.s331361.Activities.MainActivity;
import com.example.s331361.Model.Kontakt;
import com.example.s331361.Model.Moote;
import com.example.s331361.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class FindMooteToNotify extends Service {
    public FindMooteToNotify() {

    }


    public ArrayList<Moote> alleMooter;
    DBHandler db;
    Long enId;

    SharedPreferences prefs;

    @Override
    public IBinder onBind(Intent ars0) {
        return null;
    }

    public int onStartCommand(Intent intent, int flags, int startId) {


        Toast.makeText(getApplicationContext(), "Find Neste Møte to notify", Toast.LENGTH_SHORT).show();


        System.out.println("Heiii Fra FindMooteToNotify");

        String nesteMoote = (String) getNextMoote().get(0);
            System.out.println(nesteMoote + "    :   "  + getNextMooteId());


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean sendSms = prefs.getBoolean("sendSms", true);

        if(sendSms) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            Intent i = new Intent(this, MainActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(this, 0, i, 0);
            Notification notifikasjon = new NotificationCompat.Builder(this)
                    .setContentTitle("NB! Møte Påminnelse ")
                    .setContentText(nesteMoote)
                    .setSmallIcon(R.drawable.headerlogo) //TODO Change the icon
                    .setContentIntent(pIntent).build();
            notifikasjon.flags |= Notification.FLAG_AUTO_CANCEL;
            notificationManager.notify(0, notifikasjon);

           SendSMS();
        }


        return super.onStartCommand(intent, flags, startId);


    }


    public Long getNextMooteId()  {
        Date nestemoote = (Date) getNextMoote().get(1);
        if(nestemoote !=null) {
            alleMooter = (ArrayList<Moote>) db.finnAlleMooter();
            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);
            for (Moote moote : alleMooter) {
                Date mooteDato = null;
                try {
                    mooteDato = formatter.parse(moote.get_Tidspunkt());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                if (mooteDato.compareTo(nestemoote) == 0) {
                    return moote.get_ID();
                }
            }
        }
        return (long) -1;
    }

    public ArrayList<Object> getNextMoote() {
        Date naarmestdato = null;
        String nesteMoote;
        db = new DBHandler(this);
        //Henter mooter fra DB
        alleMooter = (ArrayList<Moote>) db.finnAlleMooter();

        if (alleMooter.size() != 0) {
            //Array til mooteDatoer som Dato(ikke String)
            ArrayList<Date> datoer = new ArrayList<>();

            SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy hh:mm", Locale.ENGLISH);

            //Current Dato:
            Date nowDate = new Date(System.currentTimeMillis());

            for (int i = 0; i < alleMooter.size(); i++) {

                //formaterer datoer fra String til Dato
                Date mooteDato = null;
                try {
                    mooteDato = formatter.parse(alleMooter.get(i).get_Tidspunkt());
                } catch (ParseException e) {
                    System.out.println("Exeception: " + e);
                }

                //Hvis mootedato har passert ikke legg den til i datoer array
                if (nowDate.compareTo(mooteDato) < 0) {
                    datoer.add(mooteDato);
                } else {
                    System.out.println(mooteDato + " Har passert");
                }
            }

            //Finner den nærmeste datoen

            if (datoer.size() == 0) {
                nesteMoote = "Du har ikke kommende moote";
            } else if (datoer.size() == 1) {
                nesteMoote = formatter.format(datoer.get(0));
                naarmestdato = datoer.get(0);
            } else {
                naarmestdato = Collections.min(datoer);

                //retunerer formateret neste mootedato til String igjen
                nesteMoote = "Kommende Mooter er:   " + formatter.format(naarmestdato);
            }
        } else {
            nesteMoote = "Du har ikke kommende moote";
        }
        ArrayList<Object> list = new ArrayList<>();
        list.add(nesteMoote);
        list.add(naarmestdato);
        return list;
    }





    public void SendSMS() {

        if(getNextMooteId() == -1) {
            Toast.makeText(this, "Kunne ikke sende melding, (Ingen møøter)", Toast.LENGTH_SHORT).show();

        }
        else {
            String melding = prefs.getString("smsMelding","Møtepåminnelse");
            db = new DBHandler(this);
            ArrayList<Kontakt> kontakterIEnMoote =  db.finnDeltagere(getNextMooteId());

            SmsManager smsMan = SmsManager.getDefault();
            for (Kontakt kontakt : kontakterIEnMoote) {
                smsMan.sendTextMessage(kontakt.get_Telefon(), null,melding, null, null);
            }
            Toast.makeText(this, "Har sendt sms", Toast.LENGTH_SHORT).show();
        }


    }
}
