package com.example.s331361mappe1.keyboard;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class CustomKeyboard {

    //Adderer tall i edittext ved klikk
    public static void addNumber(final EditText output, final Button... input) {
        for (final Button btn: input) {
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String prev = output.getText().toString();
                    prev = prev + btn.getText();
                    output.setText(prev);
                }
            });
        }
    }
    //Fjerner siste tall i edittext ved klikk
    public static void removeNumber(final Button input, final EditText output) {
        input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(output.getText().toString().isEmpty())) {
                    String prev = output.getText().toString();
                    output.setText(prev.substring(0, prev.length() - 1));
                }
            }
        });
    }
}
