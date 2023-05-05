package com.example.etelrendeles;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class ModositasActivity extends AppCompatActivity {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    TextView email;

    EditText mail;
    EditText jelszo;
    EditText jelszoUjra;

    CollectionReference reference;

    User user0;

    FirebaseFirestore firestore;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modositas);
        email=findViewById(R.id.email);
        mail=findViewById(R.id.mail);
        email.setText(user.getEmail());
        jelszo=findViewById(R.id.jelszo);
        jelszoUjra=findViewById(R.id.jelszoUjra);
        firestore = FirebaseFirestore.getInstance();
        user0 = new User();
        id = "";
        reference = firestore.collection("Felhasznalok");
    }

    public void modosit(View view) {
        String j=jelszo.getText().toString();
        String ju=jelszoUjra.getText().toString();
        String m=mail.getText().toString();
        if(!(j.equals("")) && j.equals(ju)) {
            user.updatePassword(j);
        }
        if(!(m.equals(""))){
            reference.get().addOnSuccessListener(queryDocumentSnapshots -> {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            User user1 = document.toObject(User.class);
                            Log.d(ModositasActivity.class.getName(), user1.getEmail());
                            if(user1.getEmail().equals(user.getEmail())) {
                                id = document.getReference().getPath();
                                user0 = document.toObject(User.class);
                                user0.setEmail(m);
                                Log.d(ModositasActivity.class.getName(), user0.getEmail());
                                Map<String,Object> felhasznalo = new HashMap<>();
                                felhasznalo.put("nev", user0.getNev());
                                felhasznalo.put("email", user0.getEmail());
                                felhasznalo.put("cim", user0.getCim());
                                felhasznalo.put("telefon", user0.getTelefon());
                                felhasznalo.put("datum", user0.getDatum());
                                user.updateEmail(m).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()) {
                                            id = id.split("/")[1];
                                            Log.d(ModositasActivity.class.getName(), "itt vagyok"+ id+ " "+felhasznalo.get("email"));
                                            reference.document(id).update(felhasznalo);
                                        } else {
                                            Toast.makeText(ModositasActivity.this, "Sikertelen email módosítás", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });


                                break;
                            }
                        }
            });

        }
        finish();
    }

    public void megse(View view) {
        finish();
    }

}
