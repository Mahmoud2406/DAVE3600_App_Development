package com.example.s331361.Model;

public class Moote {
    long _ID;
    String _Tidspunkt;
    String _Sted;
    String _Type;

    public Moote() {
    }

    public Moote(String _Tidspunkt, String _Sted, String _Type) {
        this._Tidspunkt = _Tidspunkt;
        this._Sted = _Sted;
        this._Type = _Type;
    }
    
    public Moote(long _ID, String _Tidspunkt, String _Sted, String _Type) {
        this._ID = _ID;
        this._Tidspunkt = _Tidspunkt;
        this._Sted = _Sted;
        this._Type = _Type;
    }

    public long get_ID() {
        return _ID;
    }

    public void set_ID(long _ID) {
        this._ID = _ID;
    }

    public String get_Tidspunkt() {
        return _Tidspunkt;
    }

    public void set_Tidspunkt(String _Tidspunkt) {
        this._Tidspunkt = _Tidspunkt;
    }

    public String get_Sted() {
        return _Sted;
    }

    public void set_Sted(String _Sted) {
        this._Sted = _Sted;
    }

    public String get_Type() {
        return _Type;
    }

    public void set_Type(String _Type) {
        this._Type = _Type;
    }
}
