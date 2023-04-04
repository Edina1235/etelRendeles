package com.example.etelrendeles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;

public class EtelAdapter extends RecyclerView.Adapter<RecyclerV> implements Filterable {

    Context context;
    ArrayList<Etel> etelek;
    ArrayList<Etel> etelekMind;

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
        holder.ar.setText(etelek.get(position).getAr());
        holder.tetszike.setText(etelek.get(position).getTetszike());
        holder.kep.setImageResource(etelek.get(position).getKep());

        Etel etel = etelek.get(position);
        holder.bindTo(etel);

    }

    @Override
    public int getItemCount() {
        return etelek.size();
    }

    @Override
    public Filter getFilter() {
        return etelFilter;
    }

    private Filter etelFilter = new Filter() {
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
}
