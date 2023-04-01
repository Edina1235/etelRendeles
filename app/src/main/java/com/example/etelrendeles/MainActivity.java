package com.example.etelrendeles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final int AHA=0;
    private static final String LOG = MainActivity.class.getName();
    EditText email;
    EditText jelszo;

    private SharedPreferences sharedPreferences;
    private FirebaseAuth firebaseAuth;
    private GoogleSignInClient googleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.email);
        jelszo=findViewById(R.id.jelszo);
    }

    public void bejelentkezes(View view) {
        Log.d(LOG, "bejelentkezes");
    }

    public void regisztracio(View view) {
        Intent intent = new Intent(this, RegisztracioActivity.class);
        Log.d(LOG, "regisztracio");
        startActivity(intent);
    }

    public void google(View view) {
        Log.d(LOG, "google");
    }

    public void vendeg(View view) {
        Log.d(LOG, "vendeg");
    }
}