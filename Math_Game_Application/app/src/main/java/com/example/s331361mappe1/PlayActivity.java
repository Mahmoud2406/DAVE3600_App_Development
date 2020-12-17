package com.example.s331361mappe1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.preference.PreferenceManager;

import com.example.s331361mappe1.animation_handler.ClickAnimation;
import com.example.s331361mappe1.fragments.WarningDialog;
import com.example.s331361mappe1.fragments.ExitDialog;
import com.example.s331361mappe1.fragments.ScoreDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class PlayActivity extends AppCompatActivity implements ExitDialog.DialogClickListener, ScoreDialog.DialogClickListener {

    public static final String statRight = "right";
    public static final String statWrong = "wrong";
    public static final String SHARED_PREFS = "shared-prefs";
    int counterCalc;
    MediaPlayer mpRight, mpWrong;
    TextView wrong, right, calculation;
    int wrongCount, rightCount;
    Resources res;
    List<String> regnestykker, resultater, nyttArrayRegnestykker, nyttArrayResultater;
    EditText input;
    Button next;
    SharedPreferences pref;
    EditText myTextBox;
    public static int statRightVal;
    public static int statWrongVal;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        //Instansierer nødvendige variabler
        right = findViewById(R.id.right);
        wrong = findViewById(R.id.wrong);
        mpRight = MediaPlayer.create(getApplicationContext(), R.raw.right);
        mpWrong = MediaPlayer.create(getApplicationContext(), R.raw.wrong);
        calculation = findViewById(R.id.calculation);
        input = findViewById(R.id.editTextNumber);
        next = findViewById(R.id.btnNext);
        pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        myTextBox = findViewById(R.id.editTextNumber);

        //Henter ut valgte preferanser
        String antall = pref.getString("antallValgt_Key", "5");
        String spraak = pref.getString("spraak_Key", "no");

        //Sjekker om språk i aktivitet er lik språk i instillinger. Hvis ikke, setland();
        if (!(spraak.equals(Locale.getDefault().getLanguage()))) {
            settland(spraak);
        }



        //Kaller på tilfeldig();
        tilfeldig(Integer.parseInt(antall), savedInstanceState);

        //Onclick til svar knappen
        next.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ClickAnimation.animateClick(view, getApplicationContext());
                //sjekker om det blir skrevet inn noe
                if (!(input.getText().toString().isEmpty())) {
                    //sjekker om indeksen er mindre enn regnestykkerlisten
                    if (counterCalc < nyttArrayRegnestykker.size()) {
                        String res = nyttArrayResultater.get(counterCalc);
                        //sjekker om input stemmer med regnestykke svaret
                        if (input.getText().toString().equals(res)) {
                            //hvis ja økes riktig variabelen med 1
                            rightCount += 1;
                            String tall = rightCount + "";
                            right.setText(tall);
                            mpRight.start();
                            ClickAnimation.animateClick(findViewById(R.id.calculation), getApplicationContext());
                        } else {
                            //hvis ikke økes feilvariebelen med en
                            wrongCount += 1;
                            String tall = wrongCount + "";
                            wrong.setText(tall);
                            mpWrong.start();
                        }
                        //Tømmer svarfeltet for hver gang man svarer.
                        input.setText("");
                        //øker indeks telleren med 1 for å hoppe til neste sprømål
                        counterCalc++;
                        if (counterCalc < nyttArrayRegnestykker.size()) {
                            //hopper til neste sprømål etter å ha økt indeksvariabelen med 1
                            calculation.setText(nyttArrayRegnestykker.get(counterCalc) + " = ");
                        }
                    }
                    //dersom indeksen er lik lengden på arrayet, vil spillet bli ferdig
                    if (counterCalc == nyttArrayRegnestykker.size()) {
                        loadData();
                        saveData();
                        visScoreDialog();
                    }
                } else {
                    //Hvis ikke skal det vises et dialogvindu med tekst om å skrive inn noe i svarfeltet.
                    WarningDialog dialog = new WarningDialog();
                    dialog.show(getSupportFragmentManager(), "warning");
                }
            }
        });



        //Opddaterer svarfeltet for hver gang det blir gjort en endring
        myTextBox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) { } public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (nyttArrayRegnestykker != null) {
                    calculation.setText(nyttArrayRegnestykker.get(counterCalc) + " = " + input.getText().toString());
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //Tar vare på variabeler ved rotasjon
        savedInstanceState.putInt("right", rightCount);
        savedInstanceState.putInt("wrong", wrongCount);
        savedInstanceState.putInt("counterCalc", counterCalc);
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("nyttArrayRegnestykker", (ArrayList<String>) nyttArrayRegnestykker);
        savedInstanceState.putStringArrayList("nyttArrayResultater", (ArrayList<String>) nyttArrayResultater);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Henter ut lagrede variabeler ved rotasjon
        rightCount = savedInstanceState.getInt("right");
        wrongCount = savedInstanceState.getInt("wrong");
        String rightString = rightCount + "";
        String wrongString = wrongCount + "";
        right.setText(rightString);
        wrong.setText(wrongString);
        counterCalc = savedInstanceState.getInt("counterCalc");
        savedInstanceState.putInt("wrong", wrongCount);
        nyttArrayRegnestykker = savedInstanceState.getStringArrayList("nyttArrayRegnestykker");
        nyttArrayResultater = savedInstanceState.getStringArrayList("nyttArrayResultater");
        if (counterCalc < nyttArrayRegnestykker.size()) {
            calculation.setText(nyttArrayRegnestykker.get(counterCalc) + " = " + input.getText().toString());
        }
    }



    void saveData() {
        //Oppdaterer lagrede spill og adderere nylig spilt spill.
        statRightVal = rightCount + statRightVal;
        statWrongVal = wrongCount + statWrongVal;
        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(statRight, statRightVal);
        editor.putInt(statWrong, statWrongVal);
        editor.apply();
    }



    public void loadData() {
        //Henter ut total antall feil og riktige fra sharedpreferences
        SharedPreferences pref = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        statRightVal = pref.getInt(statRight, 0);
        statWrongVal = pref.getInt(statWrong, 0);
    }


    void tilfeldig(int antall, Bundle savedInstanceState) {
        //Hivs det ikke har blitt gjort noe rotasjon.
        if (savedInstanceState == null) {
            //lager 2 nye lister for å lagre tilfeldige regnestykker og resultatet disse har.
            res = getResources();
            regnestykker = new ArrayList(Arrays.asList(res.getStringArray(R.array.regnestykker)));
            resultater = new ArrayList(Arrays.asList(res.getStringArray(R.array.resultater)));
            nyttArrayRegnestykker = new ArrayList<String>();
            nyttArrayResultater = new ArrayList<String>();

            // kjører gjennom (antall) ganger
            for (int i = 1; i <= antall; i++) {
                //genererer en tilfeldig indeks i listen
                int random = (int) Math.floor(Math.random() * regnestykker.size());

                //setter inn den tilfeldige indeksen for regnestykker og resultat i de nye listene.
                nyttArrayRegnestykker.add(regnestykker.get(random));
                nyttArrayResultater.add(resultater.get(random));
                //sletter de samme indeksne fra de originale listene for å unngå like regnestykker.
                regnestykker.remove(random);
                resultater.remove(random);
            }
            //Setter aller første regnestykke
            calculation.setText(nyttArrayRegnestykker.get(counterCalc) + " = ");
        }

    }

    // Setter språk med landkode som innparameter
    public void settland(String landskode) {
        Locale locale = new Locale(landskode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        recreate();
    }


    @Override
    public void onYesClick() {
        finish();
    }

    @Override
    public void onNoClick() {
        return;
    }

    public void visDialog() {
        DialogFragment dialog = new ExitDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "Finish");
    }

    public void visScoreDialog() {
        DialogFragment dialog = new ScoreDialog();
        dialog.setCancelable(false);
        int res = wrongCount + rightCount;
        ((ScoreDialog) dialog).setScore(rightCount + "/" + res + " " + getApplicationContext().getResources().getString(R.string.finishDialogRight));
        dialog.show(getSupportFragmentManager(), "Score");
    }

    @Override
    public void onBackPressed() {
        visDialog();
    }

    @Override
    public void onPlayAgain() {
        //Starter ny activity
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    @Override
    public void onFinish() {
        finish();
    }
}