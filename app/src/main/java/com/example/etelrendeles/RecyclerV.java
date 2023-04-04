package com.example.etelrendeles;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerV extends RecyclerView.ViewHolder {
    TextView nev;
    ImageView kep;
    TextView ar;
    TextView tetszike;

    public RecyclerV(@NonNull View itemView) {
        super(itemView);
        nev=itemView.findViewById(R.id.neve);
        kep=itemView.findViewById(R.id.kep);
        ar=itemView.findViewById(R.id.ar);
        tetszike=itemView.findViewById(R.id.tetszikvagynem);
    }



    void bindTo(Etel etel){
        itemView.findViewById(R.id.like).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tetszike.setText("Neked tetszett ez az étel");
            }
        });
        itemView.findViewById(R.id.dislike).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tetszike.setText("Neked nem tetszett ez az étel");
            }
        });
    }

}
