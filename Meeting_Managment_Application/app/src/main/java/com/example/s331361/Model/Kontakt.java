package com.example.s331361.Model;

import java.io.Serializable;

public class Kontakt {
    boolean isselected;
    long _ID;
    String _Navn;
    String _Telefon;


    public Kontakt() {
    }

    public Kontakt(String _Navn, String _Telefon) {
        this._Navn = _Navn;
        this._Telefon = _Telefon;
    }

    public Kontakt(long _ID, String _Navn, String _Telefon) {
        this._ID = _ID;
        this._Navn = _Navn;
        this._Telefon = _Telefon;
    }

    public boolean isselected() {
        return isselected;
    }

    public void setIsselected(boolean isselected) {
        this.isselected = isselected;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String get_Navn() {
        return _Navn;
    }

    public void set_Navn(String _Navn) {
        this._Navn = _Navn;
    }

    public String get_Telefon() {
        return _Telefon;
    }

    public void set_Telefon(String _Telefon) {
        this._Telefon = _Telefon;
    }

}
