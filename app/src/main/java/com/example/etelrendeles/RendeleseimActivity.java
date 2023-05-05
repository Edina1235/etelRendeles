package com.example.etelrendeles;

import static com.example.etelrendeles.R.layout.activity_rendeleseim;

import android.content.Intent;
import android.content.res.TypedArray;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.Objects;

public class RendeleseimActivity extends AppCompatActivity {
    RendelesVModel rModel;

    FirebaseAuth firebaseAuth;

    FirebaseUser aktualUser;

    TextView osszeg;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_rendeleseim);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        final RendelesAdapter adapter = new RendelesAdapter(new RendelesAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseAuth = FirebaseAuth.getInstance();
        aktualUser = firebaseAuth.getCurrentUser();
        rModel = new ViewModelProvider(this).get(RendelesVModel.class);
        rModel.getByRendelo(aktualUser.getEmail()).observe(this, adapter::submitList);
        osszeg = findViewById(R.id.osszeg);

    }

    @Override
    protected void onResume() {
        super.onResume();
        int ossz = rModel.osszeg(aktualUser.getEmail());
        Log.d(EtelekActivity.class.getName(), "Ennyirendeles " + ossz);
        osszeg.setText(String.valueOf(ossz));
    }

    public void torles(String etelNev, int mennyiseg, int ar) {
        int hanyadik=0;
        String[] etelek = getResources()
                .getStringArray(R.array.etelek);
        TypedArray kepek =
                getResources().obtainTypedArray(R.array.kepek);
        for(int i=0; i< etelek.length;i++) {
            if(etelek[i].equals(etelNev)) {
                hanyadik=i;
            }
        }
        Rendeles rendeles = new Rendeles(Objects.requireNonNull(aktualUser.getEmail()),etelNev, mennyiseg, ar, kepek.getResourceId(hanyadik,0));
        rModel.delete(rendeles);
        kepek.recycle();
    }


    public void vissza(View view) {
        Intent intent = new Intent(this, EtelekActivity.class);
        startActivity(intent);
    }

    public void rendel(View view) {
        if(Integer.parseInt(osszeg.getText().toString())!=0) {
            rModel.deleteAllByRendelo(aktualUser.getEmail());
            Intent intent = new Intent(this, WaitingActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Nem tudod megrendelni a semmit!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, EtelekActivity.class);
            startActivity(intent);
        }

    }
}
