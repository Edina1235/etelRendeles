package com.example.etelrendeles;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class RegisztracioActivity extends AppCompatActivity {
    private static final String LOG = MainActivity.class.getName();
    EditText email;

    User user;
    EditText jelszo;
    EditText jelszoUjra;
    EditText telefon;
    EditText cim;
    EditText szuletesi;
    FirebaseAuth firebase;
    CollectionReference reference;
    FirebaseFirestore firestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regisztracio);
        email=findViewById(R.id.email);
        jelszo=findViewById(R.id.jelszo);
        jelszoUjra=findViewById(R.id.jelszoUjra);
        telefon=findViewById(R.id.telefonszam);
        cim= findViewById(R.id.postaiCim);
        szuletesi=findViewById(R.id.szuletesiDatum);
        firebase=FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Felhasznalok");

    }

    public void regisztracio(View view) {
        String emaill = email.getText().toString();
        String jelszoo=jelszo.getText().toString();
        String jelszoUjraa=jelszoUjra.getText().toString();
        String tel = telefon.getText().toString();
        String cime = cim.getText().toString();
        String szuletDatum=szuletesi.getText().toString();
        Log.d(LOG, szuletDatum);
        if(emaill.equals("") || jelszoo.equals("") || jelszoUjraa.equals("") || cime.equals("") || tel.equals("") || szuletDatum.equals("")) {
            Log.d(LOG,"minden mezőt ki kell tölteni");
        }else if(!(jelszoo.equals(jelszoUjraa))) {
            Log.d(LOG, "Nem egyeznek meg a jelszók!");
        }else {
        firebase.createUserWithEmailAndPassword(emaill,jelszoo).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) {
                String nev = emaill.split("@")[0];
                user = new User(nev, emaill, tel, cime, szuletDatum);
                reference.add(user);
                Log.d(LOG, user.getNev());
                Log.d(LOG, user.getEmail());
                Log.d(LOG, user.getTelefon());
                Log.d(LOG, user.getCim());
                Log.d(LOG, user.getDatum());
                bejelentkezes();
            } else {
                Log.d(LOG, "sikertelen");
                Toast.makeText(RegisztracioActivity.this, "Nem sikerült létrehozni a felhasználót: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        }
    }

    public void megse(View view) {
        finish();
    }

    public void bejelentkezes() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
