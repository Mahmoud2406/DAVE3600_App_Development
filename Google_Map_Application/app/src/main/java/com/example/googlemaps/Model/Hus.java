package com.example.googlemaps.Model;

public class Hus {
    int id;
    String beskrivelse;
    String gateadresse;
    String gps;
    int etasjer;

    public Hus(int id, String beskrivelse, String gateadresse, String gps, int etasjer) {
        this.id = id;
        this.beskrivelse = beskrivelse;
        this.gateadresse = gateadresse;
        this.gps = gps;
        this.etasjer = etasjer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBeskrivelse() {
        return beskrivelse;
    }

    public void setBeskrivelse(String beskrivelse) {
        this.beskrivelse = beskrivelse;
    }

    public String getGateadresse() {
        return gateadresse;
    }

    public void setGateadresse(String gateadresse) {
        this.gateadresse = gateadresse;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public int getEtasjer() {
        return etasjer;
    }

    public void setEtasjer(int etasjer) {
        this.etasjer = etasjer;
    }

    @Override
    public String toString() {
        return "Hus{" +
                "id=" + id +
                ", beskrivelse='" + beskrivelse + '\'' +
                ", gateadresse='" + gateadresse + '\'' +
                ", gps='" + gps + '\'' +
                ", etasjer=" + etasjer +
                '}';
    }
}
