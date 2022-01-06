package com.example.taramatimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    // FOR DESIGN
    private RecyclerView recyclerViewContainer;

    private DatabaseClient db;
    // 2 - Declare list of users (GithubUser) & Adapter
    private ArrayList<Entrainement> entrainements;
    private MainListAapter adapterEntrainementList;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.recyclerViewContainer = findViewById(R.id.fragment_main_recycler_view);
        this.configureRecyclerView(); // - 4 Call during UI creation
    }

    @Override
    protected void onStart() {
        super.onStart();
        db = DatabaseClient.getInstance(getApplicationContext());

        getTasks();
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
        this.adapterEntrainementList.clear();
        this.entrainements.addAll(entrainements);
        adapterEntrainementList.notifyDataSetChanged();
    }

    // -------------------
    // GET Entrainements
    // -------------------
    private void getTasks() {
        // Classe asynchrone permettant de récupérer les entrainements et de mettre à jour l'UI
        class GetEntrainements extends AsyncTask<Void, Void, ArrayList<Entrainement>> {

            @Override
            protected ArrayList<Entrainement> doInBackground(Void... voids) {
                ArrayList<Entrainement> taskList = new ArrayList<>(db.getAppDatabase().entrainementDao().getAll());
                return taskList;
            }

            @Override
            protected void onPostExecute(ArrayList<Entrainement> entrainements) {
                super.onPostExecute(entrainements);
                updateUI(entrainements);
            }
        }

        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetEntrainements ge = new GetEntrainements();
        ge.execute();
    }

    public void addEntrainement(View v){
        Intent intent = new Intent(this, AddEntrainement.class);
        intent.putExtra(AddEntrainement.KEY_ENTRAINEMENT_ID, -1);
        startActivity(intent);
    }
}