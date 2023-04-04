package com.example.etelrendeles;

public class Etel {
    private String nev;
    private String ar;
    private int kep;
    private String tetszike;

    public Etel(String nev, String ar, int kep, String tetszike) {
        this.nev = nev;
        this.ar = ar;
        this.kep = kep;
        this.tetszike=tetszike;

    }



    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getAr() {
        return ar;
    }

    public void setAr(String ar) {
        this.ar = ar;
    }

    public int getKep() {
        return kep;
    }

    public void setKep(int kep) {
        this.kep = kep;
    }

    public String getTetszike() {
        return tetszike;
    }

    public void setTetszike(String tetszike) {
        this.tetszike = tetszike;
    }
}
