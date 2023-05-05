package com.example.etelrendeles;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


public class Etel {

    private String nev;

    private int ar;

    private int kep;

    private String tetszike;

    public Etel() {
    }

    public Etel(String nev, int ar, int kep, String tetszike) {
        this.nev = nev;
        this.ar = ar;
        this.kep = kep;
        this.tetszike = tetszike;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }


    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
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
