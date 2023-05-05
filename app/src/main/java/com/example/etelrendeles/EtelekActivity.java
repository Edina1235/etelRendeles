package com.example.etelrendeles;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EtelekActivity extends AppCompatActivity {
    private static final String LOG = EtelekActivity.class.getName();
    ArrayList<Etel> etel;
    EtelAdapter adapter;
    Menu menu;

    List<Rendeles> rendeles;

    FirebaseAuth firebaseAuth;

    FirebaseUser firebaseUser;

    CollectionReference reference;

    FirebaseFirestore firestore;

    RecyclerView recycle;

    RendelesVModel vModel;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_etelek);
        recycle = findViewById(R.id.recyclerView);
        etel=new ArrayList<>();
        rendeles = new ArrayList<>();
        adapter=new EtelAdapter(this,etel);
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Etelek");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        adatLekeres();

        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);
        vModel = new ViewModelProvider(this).get(RendelesVModel.class);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_LOW);
        filter.addAction(Intent.ACTION_BATTERY_OKAY);
        this.registerReceiver(receiver,filter);

    }

    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String power = intent.getAction();

            if(power == null) return;

            if(power.equals(Intent.ACTION_BATTERY_LOW)) {
                limitalas();
            }else if(power.equals(Intent.ACTION_BATTERY_OKAY)) {
                adatLekeres();
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        List<Rendeles> seged = vModel.getAllList();
        if(seged!=null) {
           //TODO menu.findItem(R.id.kosar).setIcon(ContextCompat.getDrawable(this, R.drawable.fullshopping));
            Log.d(LOG, "van bennem valami");
            rendeles=seged;
        }
    }

    public void limitalas() {
        etel.clear();
        Toast.makeText(this, "Merülő akkumlátor! Rakd fel töltőre!", Toast.LENGTH_LONG).show();
        reference.orderBy("ar").whereNotEqualTo("ar", "0").orderBy("nev").limit(10).get().addOnSuccessListener(queryDocumentSnapshots -> {
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

    private void adatInicializalas() {
        String[] etelek = getResources()
                .getStringArray(R.array.etelek);
        int[] arak = getResources()
                .getIntArray(R.array.arak);
        TypedArray kepek =
                getResources().obtainTypedArray(R.array.kepek);


        for (int i = 0; i < etelek.length; i++) {
            reference.add(new Etel(
                    etelek[i],
                    arak[i],
                    kepek.getResourceId(i, 0),
                    ""));
        }

        kepek.recycle();
    }

    public void kosarba(String nev, int ar) {
        if(firebaseUser.getEmail() == null) {
            Toast.makeText(this, "Csak bejelentkezett felhasználók tehetnek a kosárba ételt", Toast.LENGTH_SHORT).show();
        }else {
            if (menu.findItem(R.id.kosar) != null) {
                Log.d(LOG, "inicializálva van");
                Log.d(LOG, nev + " " + ar);
                menu.findItem(R.id.kosar).setIcon(ContextCompat.getDrawable(this, R.drawable.fullshopping));
                for(int i=0;i<rendeles.size();i++) {
                    if(rendeles.get(i).getRendelo().equals(firebaseUser.getEmail()) && rendeles.get(i).getEtelNev().equals(nev)) {
                        rendeles.get(i).setMennyiseg(rendeles.get(i).getMennyiseg()+1);
                        rendeles.get(i).setAr(rendeles.get(i).getMennyiseg()* ar);
                        Rendeles r = rendeles.get(i);
                        Log.d(LOG,  r.getRendelo() + " " + r.getEtelNev() + " " + r.getKep() + " " + r.getMennyiseg() + " " + r.getAr());
                        vModel.update(r);
                        return;
                    }
                }
                int hanyadik=0;
                String[] etelek = getResources()
                        .getStringArray(R.array.etelek);
                TypedArray kepek =
                        getResources().obtainTypedArray(R.array.kepek);
                for(int i=0; i< etel.size();i++) {
                    if(etelek[i].equals(nev)) {
                        hanyadik=i;
                    }
                }
                Rendeles r = new Rendeles(firebaseUser.getEmail(),nev,1, ar, kepek.getResourceId(hanyadik, 0));
                Log.d(LOG,  r.getRendelo() + " " + r.getEtelNev() + " " + r.getKep() + " " + r.getMennyiseg() + " " + r.getAr());
                rendeles.add(r);
                vModel.insert(r);
                kepek.recycle();
            } else {
                Log.d(LOG, "NEEEEM");
            }

        }
    }

    private void adatLekeres() {
        etel.clear();
        reference.orderBy("ar").whereNotEqualTo("ar", "0").orderBy("nev").get().addOnSuccessListener(queryDocumentSnapshots -> {
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
            case R.id.felhasznalok:
                felhasznalok();
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
        this.menu=menu;
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
        if(firebaseUser.getEmail()==null) {
            Toast.makeText(this, "Csak bejelentkezett felhasználók számára elérhető", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, RendeleseimActivity.class);
        startActivity(intent);
    }

    public void modositas() {
        if(firebaseUser.getEmail()==null) {
            Toast.makeText(this, "Csak bejelentkezett felhasználók számára elérhető", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, ModositasActivity.class);
        startActivity(intent);
    }

    public void felhasznalok() {
        if(firebaseUser.getEmail()==null ||  !firebaseUser.getEmail().equals("admin@admin.com")) {
            Toast.makeText(this, "Csak az admin nézheti meg!", Toast.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(this, FelhasznalokActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
