package com.example.taramatimer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // FOR DESIGN
    private RecyclerView recyclerViewContainer;

    // 2 - Declare list of users (GithubUser) & Adapter
    private ArrayList<Entrainement> entrainements;
    private MainListAapter adapterEntrainementList;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recyclerViewContainer = findViewById(R.id.fragment_main_recycler_view);
        this.configureRecyclerView(); // - 4 Call during UI creation

        ArrayList<Entrainement> tmpEntrainements = new ArrayList<>();
        tmpEntrainements.add(new Entrainement("e1"));
        tmpEntrainements.add(new Entrainement("e2"));
        tmpEntrainements.add(new Entrainement("e3"));
        tmpEntrainements.add(new Entrainement("e4"));
        tmpEntrainements.add(new Entrainement("e5"));
        tmpEntrainements.add(new Entrainement("e6"));

        updateUI(tmpEntrainements);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    // -----------------
    // CONFIGURATION
    // -----------------

    // 3 - Configure RecyclerView, Adapter, LayoutManager & glue it together
    private void configureRecyclerView(){
        // 3.1 - Reset list
        this.entrainements = new ArrayList<>();
        // 3.2 - Create adapter passing the list of entrainements
        this.adapterEntrainementList = new MainListAapter(this.entrainements);
        // 3.3 - Attach the adapter to the recyclerview to populate items
        this.recyclerViewContainer.setAdapter(this.adapterEntrainementList);
        // 3.4 - Set layout manager to position the items
        this.recyclerViewContainer.setLayoutManager(new LinearLayoutManager(this));
    }

    // -------------------
    // UPDATE UI
    // -------------------

    private void updateUI(ArrayList<Entrainement> entrainements){
        this.entrainements.addAll(entrainements);
        adapterEntrainementList.notifyDataSetChanged();
    }
}