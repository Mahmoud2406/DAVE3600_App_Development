package com.example.todolist;

import android.icu.util.Calendar;
import android.os.Build;
import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

public class Datepicker {
    @RequiresApi(api = Build.VERSION_CODES.N)
    public static java.util.Date getDateFromDatePicker(DatePicker datePicker, TimePicker timePicker){
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year =  datePicker.getYear();
        int hour = timePicker.getHour();
        int minute  = timePicker.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day,hour,minute);




        return calendar.getTime();
    }

}
