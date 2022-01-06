package com.example.taramatimer;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainListAapter extends RecyclerView.Adapter<MainListViewHolder>{
    // FOR DATA
    private ArrayList<Entrainement> entrainements;
    private Context context;

    // CONSTRUCTOR
    public MainListAapter(ArrayList<Entrainement> entrainementsList) {
        this.entrainements = entrainementsList;
    }

    @Override
    public MainListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        this.context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(this.context);
        View view = inflater.inflate(R.layout.row_list_entrainements, parent, false);
        return new MainListViewHolder(view);
    }

    // UPDATE VIEW HOLDER WITH AN ENTRAINEMENT
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(MainListViewHolder viewHolder, int position) {
        viewHolder.updateWithEntrainement(this.entrainements.get(position), context);
    }

    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    @Override
    public int getItemCount() {
        return this.entrainements.size();
    }

    public void clear(){this.entrainements.clear();}

}
