package com.example.s331361mappe1.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.s331361mappe1.R;

public class CalculationFragment extends Fragment {
    public TextView calculationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calculation, container, false);
        calculationView = v.findViewById(R.id.calculation);
        return v;
    }
}