package com.example.s331361mappe1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.s331361mappe1.R;

import static com.example.s331361mappe1.keyboard.CustomKeyboard.addNumber;
import static com.example.s331361mappe1.keyboard.CustomKeyboard.removeNumber;

import androidx.fragment.app.Fragment;

public class InputFragment extends Fragment {

    public Button next;
    Button num1;
    Button num2;
    Button num3;
    Button num4;
    Button num5;
    Button num6;
    Button num7;
    Button num8;
    Button num9;
    Button num0;
    Button delete;
    public EditText output;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_input, container, false);
        next = v.findViewById(R.id.btnNext);
        num1 = v.findViewById(R.id.number1);
        num2 = v.findViewById(R.id.number2);
        num3 = v.findViewById(R.id.number3);
        num4 = v.findViewById(R.id.number4);
        num5 = v.findViewById(R.id.number5);
        num6 = v.findViewById(R.id.number6);
        num7 = v.findViewById(R.id.number7);
        num8 = v.findViewById(R.id.number8);
        num9 = v.findViewById(R.id.number9);
        num0 = v.findViewById(R.id.number0);
        delete = v.findViewById(R.id.remove);
        output = v.findViewById(R.id.editTextNumber);
        //Setter alle tastatur tallene inn i addnumber metoden som tilfører en oneclick metode for hver knapp.
        addNumber(output, num0, num1, num2, num3, num4, num5, num6, num7, num8, num9);
        //Setter slett knappen i removeNumber, for å slette siste stringindeks i output.
        removeNumber(delete, output);

        return v;
    }
}