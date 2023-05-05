package com.example.etelrendeles;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class EtelAdapter extends RecyclerView.Adapter<EtelAdapter.RecyclerV> implements Filterable {

    Context context;
    ArrayList<Etel> etelek;
    ArrayList<Etel> etelekMind;

    private int lastPosition = -1;

    public EtelAdapter(Context context, ArrayList<Etel> etelek) {
        this.context = context;
        this.etelek = etelek;
        this.etelekMind=etelek;
    }

    @NonNull
    @Override
    public RecyclerV onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerV(LayoutInflater.from(context).inflate(R.layout.activity_etel,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerV holder, int position) {
        holder.nev.setText(etelek.get(position).getNev());
        holder.ar.setText(String.valueOf(etelek.get(position).getAr()));
        holder.tetszike.setText(etelek.get(position).getTetszike());
        holder.kep.setImageResource(etelek.get(position).getKep());

        if(holder.getAdapterPosition() > lastPosition) {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.animation);
            holder.itemView.startAnimation(animation);
            lastPosition = holder.getAdapterPosition();
        }

    }

    @Override
    public int getItemCount() {
        return etelek.size();
    }

    @Override
    public Filter getFilter() {
        return etelFilter;
    }

    private final Filter etelFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<Etel> filteredList = new ArrayList<>();
            FilterResults results = new FilterResults();

            if (charSequence == null || charSequence.length() == 0) {
                results.count = etelekMind.size();
                results.values = etelekMind;
            } else {
                String filterPattern = charSequence.toString().toLowerCase().trim();
                for (Etel etel : etelekMind) {
                    if (etel.getNev().toLowerCase().contains(filterPattern)) {
                        filteredList.add(etel);
                    }
                }

                results.count = filteredList.size();
                results.values = filteredList;
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            etelek = (ArrayList<Etel>) filterResults.values;
            notifyDataSetChanged();
        }
    };


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
            itemView.findViewById(R.id.like).setOnClickListener(view -> {
                        tetszike.setText("Neked tetszett ez az étel");
                Animation fadeAnim = new TranslateAnimation(
                        TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 0,
                        TranslateAnimation.ABSOLUTE, getAdapterPosition() - view.getRight(), TranslateAnimation.ABSOLUTE, 0);
                fadeAnim.setDuration(700);
                fadeAnim.start();
                        view.setAnimation(fadeAnim);
                    }
            );
            itemView.findViewById(R.id.dislike).setOnClickListener(view -> {
                        tetszike.setText("Neked nem tetszett ez az étel");
                Animation fadeAnim = new TranslateAnimation(
                        TranslateAnimation.ABSOLUTE, 0, TranslateAnimation.ABSOLUTE, 0,
                        TranslateAnimation.ABSOLUTE, getAdapterPosition() + view.getLeft(), TranslateAnimation.ABSOLUTE, 0);
                fadeAnim.setDuration(700);
                fadeAnim.start();
                view.setAnimation(fadeAnim);
            });
            itemView.findViewById(R.id.kosarba).setOnClickListener(view -> ((EtelekActivity)context).kosarba(nev.getText().toString(), Integer.parseInt(ar.getText().toString())));
        }

    }

}
