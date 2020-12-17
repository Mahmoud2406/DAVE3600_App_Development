package com.example.s331361;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.util.Log;

import com.example.s331361.Model.Kontakt;
import com.example.s331361.Model.Moote;

import java.util.ArrayList;
import java.util.List;





public class DBHandler extends SQLiteOpenHelper {
    static String TABLE_KONTAKTER = "Kontakter";
    static String TABLE_MOOTE = "Mooter";
    static String TABLE_MOOTEDELTAGELSE = "Mootedeltagelser";

    //Kontakter attributter
    static String KEY_KONTAKTID = "KontaktId";
    static String KEY_NAME = "Navn";
    static String KEY_PH_NO = "Telefon";

    //Møter attributter
    static String KEY_MOOTEID = "MooteId";
    static String KEY_TIME = "Tidspunkt";
    static String KEY_PLACE = "Sted";
    static String KEY_TYPE = "Type";

    // Møtedeltakelse
    // Skal inneholde mooteId og KontaktId som to fremmednøkler
    // (Mange til mange kobling mot denne tabellen Fra kontakter og fra Møter.
    static String KEY_DELTAGELSEID = "DeltagelseId";
    static String KEY_FK_KONTAKTID = "FK_Kontaktid";
    static String KEY_FK_MOOTEID = "FK_Mooteid";


    static int DATABASE_VERSION = 1;
    static String DATABASE_NAME = "Mooteeksperten";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //Kontakter tabell
        String LAG_TABELL_KONTAKTER = "CREATE TABLE " + TABLE_KONTAKTER + "(" + KEY_KONTAKTID +
                " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT," + KEY_PH_NO + " TEXT" + ")";

        System.out.println("SQL " + LAG_TABELL_KONTAKTER);
        //Møter tabell
        String LAG_TABELL_MOOTER = "CREATE TABLE " + TABLE_MOOTE + "(" + KEY_MOOTEID +
                " INTEGER PRIMARY KEY," + KEY_TIME + " TEXT," + KEY_PLACE + " TEXT," + KEY_TYPE + " TEXT" + ")";


        String LAG_TABELL_MOOTEDELTAKELSE = "CREATE TABLE " + TABLE_MOOTEDELTAGELSE + "(" + KEY_MOOTEID +
                " INTEGER," + KEY_KONTAKTID + " INTEGER," + "CONSTRAINT "+ KEY_DELTAGELSEID + "INTEGER PRIMARY KEY " + "(" +KEY_MOOTEID + "," + KEY_KONTAKTID + ")," +
                "CONSTRAINT " + KEY_FK_KONTAKTID + " FOREIGN KEY" + "(" + KEY_KONTAKTID + ")" +  " REFERENCES " + TABLE_KONTAKTER + "(" + KEY_KONTAKTID + ")," +
                "CONSTRAINT "+KEY_FK_MOOTEID + " FOREIGN KEY" +"("+ KEY_MOOTEID + ")" +  " REFERENCES " + TABLE_MOOTE + "(" + KEY_MOOTEID + ") )";

        db.execSQL(LAG_TABELL_MOOTER);
        db.execSQL(LAG_TABELL_KONTAKTER);
        db.execSQL(LAG_TABELL_MOOTEDELTAKELSE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_KONTAKTER);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOOTE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOOTEDELTAGELSE);

        onCreate(db);
    }

    public void leggTilKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, kontakt.get_Navn());
        values.put(KEY_PH_NO, kontakt.get_Telefon());
        db.insert(TABLE_KONTAKTER, null, values);
        db.close();
    }

    // Transaksjon som følger ACID konseptet. //Legger til møte kun
    public void leggTilMoote(Moote moote, List<Long> kontakterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mooteVal = new ContentValues();

        mooteVal.put(KEY_TIME, moote.get_Tidspunkt());
        mooteVal.put(KEY_PLACE, moote.get_Sted());
        mooteVal.put(KEY_TYPE, moote.get_Type());

        db.beginTransaction();
        //Returnerer id av raden som ble addet.
        long last_inserted_id = db.insert(TABLE_MOOTE, null, mooteVal);

        //Hvis insert møte ble vellyket, da inserter vi i møtedeltagelse tabellen
        if(last_inserted_id != -1) {
            //Lagrer kontaktid sammen med gitt møteid
            for (long kontaktId : kontakterId) {
                ContentValues deltagelseVal = new ContentValues();
                deltagelseVal.put(KEY_KONTAKTID, kontaktId);
                deltagelseVal.put(KEY_MOOTEID, last_inserted_id);
                //Utfører insert på enhver kontakt i møtedeltagelse (Mange til Mange)
                long last_inserted_row = db.insert(TABLE_MOOTEDELTAGELSE, null, deltagelseVal);
                //Feilhåndteering
                if(last_inserted_row != -1){
                    //Hvis alt går bra kan vi committe til databasen
                }
                else
                {
                    Log.d("SQL feil","Feil i Møtedeltagelse Insert");
                }

            }
            db.setTransactionSuccessful();

        }
        else {
            //feilhåndtering
            Log.d("SQL feil","Feil i Møte tabellen Insert");
        }

        db.endTransaction();
        db.close();
    }

    public List<Moote> finnAlleMooter() {
        List<Moote> mooteListe = new ArrayList<Moote>();
        String selectQuery = "SELECT * FROM " + TABLE_MOOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Moote moote = new Moote();
                moote.set_ID(cursor.getLong(0));
                moote.set_Tidspunkt(cursor.getString(1));
                moote.set_Sted(cursor.getString(2));
                moote.set_Type(cursor.getString(3));
                mooteListe.add(moote);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return mooteListe;
    }




    public void slettMoote(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
         db.beginTransaction();

        long last_deleted_row = db.delete(TABLE_MOOTE, KEY_MOOTEID + " =? ",  new String[]{Long.toString(inn_id)});
        if(last_deleted_row != -1) {
            db.delete(TABLE_MOOTEDELTAGELSE, KEY_MOOTEID+"= '" + inn_id + "'", null);
            db.setTransactionSuccessful();
        }
        db.endTransaction();
        db.close();
    }

    public void slettKontakt(Long inn_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_KONTAKTER, KEY_KONTAKTID + " =? ",
                new String[]{Long.toString(inn_id)});
        db.close();
    }

    public int oppdaterMoote(Moote moote ,List<Long> kontakterId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues mooteVal = new ContentValues();

        mooteVal.put(KEY_TIME, moote.get_Tidspunkt());
        mooteVal.put(KEY_PLACE, moote.get_Sted());
        mooteVal.put(KEY_TYPE, moote.get_Type());

        db.beginTransaction();
        //Returnerer id av raden som ble addet.
        long last_inserted_id  = db.update(TABLE_MOOTE, mooteVal, KEY_MOOTEID + "= ?", new String[]{Long.toString(moote.get_ID())});


        if(last_inserted_id != -1) {
            //  Oppadaterer alle kontaktene til møte. Sletter alle og
            // adderer dem igjen for å få det nye antallet deltagere pr møte.
            db.delete(TABLE_MOOTEDELTAGELSE, KEY_MOOTEID+"= '" + last_inserted_id + "'", null);

            for (long kontaktId : kontakterId) {
                ContentValues deltagelseVal = new ContentValues();
                deltagelseVal.put(KEY_KONTAKTID, kontaktId);
                deltagelseVal.put(KEY_MOOTEID, last_inserted_id);
                //Utfører insert på enhver kontakt i møtedeltagelse (Mange til Mange)
                long last_inserted_row = db.insert(TABLE_MOOTEDELTAGELSE, null, deltagelseVal);
                //Feilhåndteering
                if (last_inserted_row != -1) {
                    //Hvis alt går bra kan vi committe til databasen
                } else {
                    Log.d("SQL feil", "Feil i Møtedeltagelse Insert");
                }

            }
            db.setTransactionSuccessful();
            db.endTransaction();
            db.close();

        }
       return (int) last_inserted_id;
    }

    public int oppdaterKontakt(Kontakt kontakt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, kontakt.get_Navn());
        values.put(KEY_PH_NO, kontakt.get_Telefon());
        int endret = db.update(TABLE_KONTAKTER, values, KEY_KONTAKTID + "= ?",
                new String[]{String.valueOf(kontakt.get_ID())});
        db.close();
        return endret;
    }

    public int finnAntallMooter() {
        String sql = "SELECT * FROM " + TABLE_MOOTE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int antall = cursor.getCount();
        cursor.close();
        db.close();
        return antall;
    }



    public ArrayList<Kontakt> finnDeltagere(long id) {
        ArrayList<Kontakt> kontakter = new ArrayList<>();

        String sql = "SELECT k.*" +
                "FROM Kontakter k" +
                " JOIN " + TABLE_MOOTEDELTAGELSE + " md ON k."+ KEY_KONTAKTID + " = md." + KEY_KONTAKTID +
               // " JOIN " + TABLE_MOOTE + " m ON md." + KEY_MOOTEID + " = m." + KEY_MOOTEID +
                " WHERE md." + KEY_MOOTEID + "= " + id;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                Kontakt kontakt = new Kontakt();
                kontakt.set_ID(cursor.getLong(0));
                kontakt.set_Navn(cursor.getString(1));
                kontakt.set_Telefon(cursor.getString(2));
                kontakter.add(kontakt);
                System.out.println("Navn::::::::::::::::::::::::::::::::::::::::::::::::::::::::::"+kontakt.get_Navn());
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontakter;
    }


    public Moote finnMoote(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_MOOTE, new String[]{
                        KEY_MOOTEID,KEY_TIME, KEY_PLACE, KEY_TYPE}, KEY_MOOTEID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Moote moote = new
                Moote(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3) );
        cursor.close();
        db.close();
        return moote;
    }

    public Kontakt finnKontakt(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_KONTAKTER, new String[]{
                        KEY_KONTAKTID, KEY_NAME, KEY_PH_NO}, KEY_KONTAKTID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) cursor.moveToFirst();
        Kontakt kontakt = new
                Kontakt(Long.parseLong(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
        cursor.close();
        db.close();
        return kontakt;
    }

    public int finnAntallKontakter() {
        String sql = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(sql, null);
        int antall = cursor.getCount();
        cursor.close();
        db.close();
        return antall;
    }


    public List<Kontakt> finnAlleKontakter() {
        List<Kontakt> kontaktListe = new ArrayList<Kontakt>();
        String selectQuery = "SELECT * FROM " + TABLE_KONTAKTER;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Kontakt kontakt = new Kontakt();
                kontakt.set_ID(cursor.getLong(0));
                kontakt.set_Navn(cursor.getString(1));
                kontakt.set_Telefon(cursor.getString(2));
                kontaktListe.add(kontakt);
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        }
        return kontaktListe;
    }


}
