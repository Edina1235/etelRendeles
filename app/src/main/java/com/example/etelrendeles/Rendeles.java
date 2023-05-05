package com.example.etelrendeles;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(primaryKeys = {"rendelo", "etelNev"},tableName = "Rendeles")
public class Rendeles {

    @NonNull
    @ColumnInfo(name = "rendelo")
    private String rendelo;
    @NonNull
    @ColumnInfo(name = "etelNev")
    private String etelNev;
    @NonNull
    @ColumnInfo(name = "mennyiseg")
    private int mennyiseg;
    @NonNull
    @ColumnInfo(name = "ar")
    private int ar;
    @NonNull
    @ColumnInfo(name = "kep")
    private int kep;

    public Rendeles(@NonNull String rendelo, @NonNull String etelNev, @NonNull int mennyiseg, int ar, @NonNull int kep) {
        this.rendelo = rendelo;
        this.etelNev = etelNev;
        this.mennyiseg = mennyiseg;
        this.ar = ar;
        this.kep = kep;
    }

    @NonNull
    public String getRendelo() {
        return rendelo;
    }

    public void setRendelo(@NonNull String rendelo) {
        this.rendelo = rendelo;
    }

    @NonNull
    public String getEtelNev() {
        return etelNev;
    }

    public void setEtelNev(@NonNull String etelNev) {
        this.etelNev = etelNev;
    }

    @NonNull
    public int getMennyiseg() {
        return mennyiseg;
    }

    public void setMennyiseg(@NonNull int mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public int getAr() {
        return ar;
    }

    public void setAr(int ar) {
        this.ar = ar;
    }

    @NonNull
    public int getKep() {
        return kep;
    }

    public void setKep(@NonNull int kep) {
        this.kep = kep;
    }
}
