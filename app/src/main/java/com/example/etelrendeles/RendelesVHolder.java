package com.example.etelrendeles;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class RendelesVHolder extends RecyclerView.ViewHolder {
    private final TextView etelNev;
    private final ImageView kep;
    private final TextView mennyiseg;
    private final TextView ar;

    private RendelesVHolder(View itemView) {
        super(itemView);
        etelNev = itemView.findViewById(R.id.neve);
        kep = itemView.findViewById(R.id.kep);
        mennyiseg = itemView.findViewById(R.id.spinner);
        ar = itemView.findViewById(R.id.ar);
        itemView.findViewById(R.id.exit).setOnClickListener(view -> ((RendeleseimActivity)view.getContext()).torles(etelNev.getText().toString(), Integer.parseInt(mennyiseg.getText().toString()), Integer.parseInt(ar.getText().toString())));
    }

    public void bind(Rendeles rendeles) {
        etelNev.setText(rendeles.getEtelNev());
        Log.d(EtelekActivity.class.getName(),"Kep: " + rendeles.getKep());
        kep.setImageResource(rendeles.getKep());
        mennyiseg.setText(String.valueOf(rendeles.getMennyiseg()));
        ar.setText(String.valueOf(rendeles.getAr()));
    }

    static RendelesVHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rendeles, parent, false);
        return new RendelesVHolder(view);
    }
}
