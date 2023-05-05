package com.example.etelrendeles;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class FelhasznalokActivity extends AppCompatActivity {

    FelhasznalokAdapter adapter;

    RecyclerView recycle;

    ArrayList<User> users;

    CollectionReference reference;

    FirebaseFirestore firestore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.felhasznalok_activity);
        users= new ArrayList<>();
        recycle = findViewById(R.id.recyclerView);
        adapter=new FelhasznalokAdapter(this,users);
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setAdapter(adapter);
        firestore = FirebaseFirestore.getInstance();
        reference = firestore.collection("Felhasznalok");

        adatLekeres();
    }

    private void adatLekeres() {
        users.clear();
        reference.orderBy("cim").orderBy("nev").whereNotEqualTo("cim", "").get().addOnSuccessListener(queryDocumentSnapshots -> {
            for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                User user = document.toObject(User.class);
                users.add(user);
                Log.d(FelhasznalokAdapter.class.getName(), user.getNev());
            }

            if (users.size() == 0) {
                Log.d(FelhasznalokAdapter.class.getName(), "valami nem jo");
                return;
            }

            adapter.notifyDataSetChanged();
        });
    }

    public void vissza(View view) {
        finish();
    }
}
