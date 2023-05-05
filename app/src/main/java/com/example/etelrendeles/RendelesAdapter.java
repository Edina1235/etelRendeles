package com.example.etelrendeles;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RendelesAdapter extends ListAdapter<Rendeles, RendelesVHolder> {

    public RendelesAdapter(@NonNull DiffUtil.ItemCallback<Rendeles> diffCallback) {
        super(diffCallback);
    }

    @Override
    public RendelesVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RendelesVHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(RendelesVHolder holder, int position) {
        Rendeles current = getItem(position);
        holder.bind(current);
    }

    static class WordDiff extends DiffUtil.ItemCallback<Rendeles> {

        @Override
        public boolean areItemsTheSame(@NonNull Rendeles oldItem, @NonNull Rendeles newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Rendeles oldItem, @NonNull Rendeles newItem) {
            return oldItem.getEtelNev().equals(newItem.getEtelNev());
        }
    }
}