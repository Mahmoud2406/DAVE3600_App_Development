package com.example.todolist;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.todolist.Filehandler.readData;
import com.example.todolist.Filehandler.saveData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AddtaskActivity extends Activity {
    Date date;
    TimePicker timePicker;
    DatePicker datePicker;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

    final List<Object> list = new ArrayList<>();


    protected void onCreate(Bundle savedInstanceState) {

        readData.readData(this.getFilesDir(), list);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtask);

        View view = findViewById(R.id.back);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddtaskActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

            }
        });
        EditText editTxt = findViewById(R.id.editText);


        Switch swtch = findViewById(R.id.switch1);
        TextView txtTime = findViewById(R.id.txtTime);
        TextView txtDate = findViewById(R.id.txtDate);

        timePicker = (TimePicker) findViewById(R.id.time_picker);

        datePicker = (DatePicker) findViewById(R.id.date_picker);


        swtch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (swtch.isChecked()) {
                    txtDate.setVisibility(View.VISIBLE);
                    txtTime.setVisibility(View.VISIBLE);
                    datePicker.setVisibility(View.VISIBLE);
                    timePicker.setVisibility(View.VISIBLE);
                } else {
                    txtDate.setVisibility(View.INVISIBLE);
                    txtTime.setVisibility(View.INVISIBLE);
                    datePicker.setVisibility(View.INVISIBLE);
                    timePicker.setVisibility(View.INVISIBLE);
                }
            }
        });
        datePicker.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                datePicker.init(year, monthOfYear, dayOfMonth, null);
                datePicker = view;
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                timePicker.setHour(hourOfDay);
                timePicker.setMinute(minute);
                timePicker = view;

            }
        });
        ImageView finished = findViewById(R.id.finished);


        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getHour(), timePicker.getMinute());
                String date = calendar.getTime().toString();
                int index = date.indexOf(" GMT");
                if(index > -1)
                    date = date.substring(0, index);

                if (!editTxt.getText().toString().isEmpty()) {
                    list.add(editTxt.getText().toString() + ";" + date);
                    saveData.saveData(AddtaskActivity.this.getFilesDir(), list);
                }
                Intent intent = new Intent(AddtaskActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);

                // get the values for day of month , month and year from a date picker
                String day = "Day = " + datePicker.getDayOfMonth();
                String month = "Month = " + (datePicker.getMonth() + 1);
                String year = "Year = " + datePicker.getYear();
                // display the values by using a toast
                Toast.makeText(getApplicationContext(), "Task : " + editTxt.getText() + "\n" + day + "\n" + month + "\n" + year, Toast.LENGTH_LONG).show();

            }
        });

    }


}