package com.example.taramatimer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainListAapter extends RecyclerView.Adapter<MainListViewHolder>{
    // FOR DATA
    private ArrayList<Entrainement> entrainements;

    // CONSTRUCTOR
    public MainListAapter(ArrayList<Entrainement> entrainementsList) {
        this.entrainements = entrainementsList;
    }

    @Override
    public MainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_list_entrainements, parent, false);

        return new MainListViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH AN ENTRAINEMENT
    @Override
    public void onBindViewHolder(MainListViewHolder viewHolder, int position) {
        viewHolder.updateWithEntrainement(this.entrainements.get(position));
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.entrainements.size();
    }

}
