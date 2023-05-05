package com.example.etelrendeles;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class FelhasznalokAdapter extends RecyclerView.Adapter<FelhasznalokAdapter.RecyclerV> {

    Context context;
    ArrayList<User> felhasznalok;
    ArrayList<User> felhasznalokMind;

    public FelhasznalokAdapter(Context context, ArrayList<User> felhasznalok) {
        this.context = context;
        this.felhasznalok = felhasznalok;
        this.felhasznalokMind=felhasznalok;
        for(int i=0; i<felhasznalok.size();i++) {
            Log.d(FelhasznalokActivity.class.getName(), felhasznalok.get(i).getEmail());
        }
    }

    @NonNull
    @Override
    public RecyclerV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerV(LayoutInflater.from(context).inflate(R.layout.felhasznalo, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerV holder, int position) {
        holder.nev.setText(felhasznalok.get(position).getNev());
        holder.email.setText(felhasznalok.get(position).getEmail());
        holder.cim.setText(felhasznalok.get(position).getCim());
        holder.telefon.setText(felhasznalok.get(position).getTelefon());
    }

    @Override
    public int getItemCount() {
        return felhasznalok.size();
    }


    public static class RecyclerV extends RecyclerView.ViewHolder {
        TextView nev;
        TextView email;
        TextView cim;
        TextView telefon;

        public RecyclerV(@NonNull View itemView) {
            super(itemView);
            nev=itemView.findViewById(R.id.neve);
            email=itemView.findViewById(R.id.email);
            cim=itemView.findViewById(R.id.cim);
            telefon=itemView.findViewById(R.id.telefon);
            }

    }

}
