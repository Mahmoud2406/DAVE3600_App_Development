package com.example.googlemaps.Model;

public class Reservasjon {

    int id;
    String dato;
    String tidFra;
    String tidTil;
    int romId;
    String studentNavn;


    public Reservasjon(int id, String dato, String tidFra, String tidTil, int romId, String studentNavn) {
        this.id = id;
        this.dato = dato;
        this.tidFra = tidFra;
        this.tidTil = tidTil;
        this.romId = romId;
        this.studentNavn = studentNavn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDato() {
        return dato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getTidFra() {
        return tidFra;
    }

    public void setTidFra(String tidFra) {
        this.tidFra = tidFra;
    }

    public String getTidTil() {
        return tidTil;
    }

    public void setTidTil(String tidTil) {
        this.tidTil = tidTil;
    }

    public int getRomId() {
        return romId;
    }

    public void setRomId(int romId) {
        this.romId = romId;
    }

    public String getStudentNavn() {
        return studentNavn;
    }

    public void setStudentNavn(String studentNavn) {
        this.studentNavn = studentNavn;
    }
}

