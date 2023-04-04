package com.example.etelrendeles;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ModositasActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView email;

    EditText mail;
    EditText jelszo;
    EditText jelszoUjra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modositas);
        email=findViewById(R.id.email);
        mail=findViewById(R.id.mail);
        email.setText(user.getEmail());
        jelszo=findViewById(R.id.jelszo);
        jelszoUjra=findViewById(R.id.jelszoUjra);
    }

    public void modosit(View view) {
        String j=jelszo.getText().toString();
        String ju=jelszoUjra.getText().toString();
        String m=mail.getText().toString();
        if(!(j.equals("")) && j.equals(ju)) {
            user.updatePassword(j);
        }
        if(!(m.equals(""))){
            user.updateEmail(m);
        }
        finish();
    }

    public void megse(View view) {
        finish();
    }

}
