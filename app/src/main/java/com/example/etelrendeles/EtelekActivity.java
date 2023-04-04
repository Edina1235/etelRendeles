package com.example.etelrendeles;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EtelekActivity extends AppCompatActivity {
    private static final String LOG = EtelekActivity.class.getName();
    ArrayList<Etel> etel;
    EtelAdapter adapter;

    RecyclerV recyclerV;

    CollectionReference reference;

    FirebaseFirestore firestore;

    TextView tetszike;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etelek);
        RecyclerView recycle = findViewById(R.id.recyclerView);
        etel=new ArrayList<>();
        adapter=new EtelAdapter(this,etel);
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Etelek");
        //adatInicializalas();
        adatLekeres();

        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);
    }


    private void adatInicializalas() {
        String[] etelek = getResources()
                .getStringArray(R.array.etelek);
        String[] arak = getResources()
                .getStringArray(R.array.arak);
        TypedArray kepek =
                getResources().obtainTypedArray(R.array.kepek);


        for (int i = 0; i < etelek.length; i++) {
            etel.add(new Etel(
                    etelek[i],
                    arak[i],
                    kepek.getResourceId(i, 0),
                    ""));
        }

        kepek.recycle();
    }

    public void kosarba(View view) {

    }

    private void adatLekeres() {
        reference.orderBy("nev").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                Etel etel0 = document.toObject(Etel.class);
                etel.add(etel0);
            }

            if (etel.size() == 0) {
                adatInicializalas();
                adatLekeres();
            }

            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.kosar:
                rendeleseim();
                return true;
            case R.id.beallitasok:
                modositas();
                return true;
            case R.id.kijelentkezes:
                FirebaseAuth.getInstance().signOut();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem menuItem = menu.findItem(R.id.kereses);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                Log.d(LOG, s);
                adapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    public void rendeleseim() {
        Intent intent = new Intent(this, RendeleseimActivity.class);
        startActivity(intent);
    }

    public void modositas() {
        Intent intent = new Intent(this, ModositasActivity.class);
        startActivity(intent);
    }



}
