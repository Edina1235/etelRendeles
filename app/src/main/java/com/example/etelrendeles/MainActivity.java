package com.example.etelrendeles;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static final int AHA=0;
    private static final String LOG = MainActivity.class.getName();
    EditText email;
    EditText jelszo;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email=findViewById(R.id.email);
        jelszo=findViewById(R.id.jelszo);
        firebaseAuth=FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
    }

    public void bejelentkezes(View view) {
        String emaill=email.getText().toString();
        String jelszoo=jelszo.getText().toString();
        firebaseAuth.signInWithEmailAndPassword(emaill,jelszoo).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()) rendeles();
            else Log.d(LOG,"Sikertelen");
        });
    }

    public void regisztracio(View view) {
        Intent intent = new Intent(this, RegisztracioActivity.class);
        startActivity(intent);
    }

    public void vendeg(View view) {
        firebaseAuth.signInAnonymously().addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Log.d(LOG, "Sikeresen bejelentkezett anonimként");
                rendeles();
            } else {
                Log.d(LOG, "Nem sikerült bejelentkezni anonimként");
            }
        });
    }

    public void rendeles() {
        Intent intent = new Intent(this, EtelekActivity.class);
        startActivity(intent);
    }
}