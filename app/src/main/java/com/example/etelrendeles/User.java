package com.example.etelrendeles;

public class User {
    //(nev, emaill, tel, cime, szuletDatum);
    private String nev;
    private String email;
    private String telefon;
    private String cim;
    private String datum;

    public User() {
    }

    public User(String nev, String email, String telefon, String cim, String datum) {
        this.nev = nev;
        this.email = email;
        this.telefon = telefon;
        this.cim = cim;
        this.datum = datum;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }
}
