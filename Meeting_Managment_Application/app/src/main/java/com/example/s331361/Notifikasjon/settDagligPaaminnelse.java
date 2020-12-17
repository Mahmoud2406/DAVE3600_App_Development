package com.example.s331361.Notifikasjon;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.s331361.Builders.TimePreference;

import java.util.Calendar;
import java.util.Date;

public class settDagligPaaminnelse extends Service {


    SharedPreferences prefs;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean sendSms = prefs.getBoolean("sendSms", true);
        String tid = prefs.getString("smsTid", "10:55");
        String[] timeMinutt = tid.split(":");
        int hour = Integer.parseInt(timeMinutt[0]);
        int minute =  Integer.parseInt(timeMinutt[1]);
        System.out.println(hour + " : " + minute + " -----------------------------------------");
        Intent i = new Intent(this, FindMooteToNotify.class);
        PendingIntent pintent = PendingIntent.getService(this, 0, i, 0);
        AlarmManager alarm;

        if (sendSms) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            calendar.set(Calendar.SECOND, 0);

            if (calendar.before(Calendar.getInstance())) {
                calendar.add(Calendar.DAY_OF_YEAR, 1);
            }

            alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pintent);
        }
        else {
            alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            alarm.cancel(pintent);
            Toast.makeText(this, "Alarm er slått av", Toast.LENGTH_SHORT).show();
        }

        return super.onStartCommand(intent, flags, startId);
    }
}