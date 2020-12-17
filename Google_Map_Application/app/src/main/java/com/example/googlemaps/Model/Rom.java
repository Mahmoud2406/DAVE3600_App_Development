package com.example.googlemaps.Model;

public class Rom {

    int id;
    int etasjenr;
    String romnummer;
    String kapasitet;
    String beskrivelse;
    int husId;

    public Rom(int id, int etasjenr, String romnummer, String kapasitet, String beskrivelse, int husId) {
        this.id = id;
        this.etasjenr = etasjenr;
        this.romnummer = romnummer;
        this.kapasitet = kapasitet;
        this.beskrivelse = beskrivelse;
        this.husId = husId;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEtasjenr() {
        return etasjenr;
    }

    public void setEtasjenr(int etasjenr) {
        this.etasjenr = etasjenr;
    }

    public String getRomnummer() {
        return romnummer;
    }

    public void setRomnummer(String romnummer) {
        this.romnummer = romnummer;
    }

    public String getKapasitet() {
        return kapasitet;
    }

    public void setKapasitet(String kapasitet) {
        this.kapasitet = kapasitet;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public int getHusId() {
        return husId;
    }

    public void setHusId(int husId) {
        this.husId = husId;
    }

    @Override
    public String toString() {
        return "Rom{" +
                "id=" + id +
                ", etasjenr=" + etasjenr +
                ", romnummer=" + romnummer +
                ", kapasitet='" + kapasitet + '\'' +
                ", beskrivelse='" + beskrivelse + '\'' +
                ", husId=" + husId +
                '}';
    }
}
