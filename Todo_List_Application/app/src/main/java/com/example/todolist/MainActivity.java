package com.example.todolist;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.todolist.Filehandler.readData;
import com.example.todolist.Filehandler.saveData;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity{
    @Override
    public void onBackPressed() {
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }

    final List<Object> list = new ArrayList<>();

    @TargetApi(Build.VERSION_CODES.N)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);


        final ListView listview = findViewById(R.id.listview);
        final TxtAdapter adapter = new TxtAdapter();

/*     Options
        ImageView nav = findViewById(R.id.navigation);
        nav.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this,AddtaskActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.enter,R.anim.exit);

        });

*/
        readData.readData(this.getFilesDir(),list);

        adapter.setData(list);
        listview.setAdapter(adapter);



        listview.setOnItemClickListener((parent, view, position, id) -> {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this,R.style.AppCompatAlertDialogStyle)
                    .setCancelable(false)
                    .setTitle("Options")
                    .setPositiveButton("Delete", (dialogInterface, i) -> {
                        list.remove(position);
                        adapter.setData(list);
                        saveData.saveData(MainActivity.this.getFilesDir(),list);
                    })
                    .setNegativeButton("Cancel", null)
                    .setNeutralButton("Edit", (dialogInterface, i) -> {
                        final EditText editedTask = new EditText(MainActivity.this);
                        editedTask.setSingleLine();
                        editedTask.setText((Integer) list.get(position));
                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                                .setTitle("Edit")
                                .setView(editedTask)
                                .setPositiveButton("Edit", (dialogInterface1, i1) -> {
                                    if (!editedTask.getText().toString().isEmpty()) {
                                        list.set(position, editedTask.getText().toString());
                                        adapter.setData(list);
                                    } else dialogInterface1.cancel();
                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.show();

                    })
                    .create();
            alertDialog.show();
        });

        final Button newTaskBtn = findViewById(R.id.newTaskBtn);


        newTaskBtn.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    ObjectAnimator scaleDownX = ObjectAnimator.ofFloat(newTaskBtn,
                            "scaleX", 0.8f);
                    ObjectAnimator scaleDownY = ObjectAnimator.ofFloat(newTaskBtn,
                            "scaleY", 0.8f);
                    scaleDownX.setDuration(100);
                    scaleDownY.setDuration(100);

                    AnimatorSet scaleDown = new AnimatorSet();
                    scaleDown.play(scaleDownX).with(scaleDownY);

                    scaleDown.start();
                    break;

                case MotionEvent.ACTION_UP:
                    ObjectAnimator scaleDownX2 = ObjectAnimator.ofFloat(
                            newTaskBtn, "scaleX", 1f);
                    ObjectAnimator scaleDownY2 = ObjectAnimator.ofFloat(
                            newTaskBtn, "scaleY", 1f);
                    scaleDownX2.setDuration(100);
                    scaleDownY2.setDuration(100);

                    AnimatorSet scaleDown2 = new AnimatorSet();
                    scaleDown2.play(scaleDownX2).with(scaleDownY2);


                    scaleDown2.start();

                    Intent intent = new Intent(MainActivity.this,AddtaskActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.enter,R.anim.exit);

                    break;
            }
            return true;
        });


       final Button view = findViewById(R.id.clearall);

        view.setOnTouchListener((view1, motionEvent) -> {
            if(MotionEvent.ACTION_UP == motionEvent.getAction()) {
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this).setTitle("Delete all").setMessage("Are you sure?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        list.clear();
                        adapter.setData(list);
                        saveData.saveData(MainActivity.this.getFilesDir(),list);
                    }
                }).setNegativeButton("No",null).create();
                dialog.show();

            }
            return true;
        });


    }





    class TxtAdapter extends BaseAdapter {

        List<Object> list = new ArrayList<>();

        public void setData(List<Object> settedList) {
            list.clear();
            list.addAll(settedList);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View pastView, ViewGroup parentView) {

            if (pastView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        MainActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                pastView = inflater.inflate(R.layout.item, parentView, false);
            }

            TextView textview = pastView.findViewById(R.id.task);
            TextView date = pastView.findViewById(R.id.date);

            String part =(String) list.get(position);

                String[] parts = part.split(";");

                textview.setText(parts[0]);
                date.setText(parts[1]);

            return pastView;

        }
    }
}
