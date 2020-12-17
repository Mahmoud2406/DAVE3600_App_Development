package com.example.s331361mappe1.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.s331361mappe1.R;

public class ScoreDialog extends DialogFragment {
    private ScoreDialog.DialogClickListener callback;
    private String score;

    public void setScore(String score) {
        this.score = score;
    }

    public interface DialogClickListener {
        void onPlayAgain();

        void onFinish();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            callback = (ScoreDialog.DialogClickListener) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException("Kallende klasse må implementere interfacet!");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Lager et alertdialog vindu som viser frem resultat, knapp for å spille igjen og knapp for å avslutte
        return new AlertDialog.Builder(getActivity()).setTitle(R.string.gameMsg).setMessage(score).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                callback.onFinish();
            }
        }).setPositiveButton(R.string.playAgain, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                callback.onPlayAgain();
            }
        }).create();
    }
}