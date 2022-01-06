package com.example.taramatimer;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class timerActivity extends AppCompatActivity implements OnUpdateListener {

    private Compteur compteur;
    private TextView timerValue;
    private DatabaseClient db;
    public static String KEY_ID_ENTRAINEMENT = "entrainementUid";
    private int currentPos = 0;
    private long currentTime = 0;
    private ArrayList<String> names;
    private ArrayList<Integer> times;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(this.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            setContentView(R.layout.activity_timer_horisontal);
        }
        else{
            setContentView(R.layout.activity_timer);
        }

        db = DatabaseClient.getInstance(getApplicationContext());
        int entrainementUid = getIntent().getIntExtra(KEY_ID_ENTRAINEMENT, 0);

        // Récupérer la view
        timerValue = (TextView) findViewById(R.id.timerValue);

        // Initialiser l'objet Compteur
        compteur = new Compteur();

        // Abonner l'activité au compteur pour "suivre" les événements
        compteur.addOnUpdateListener(this);

        ImageButton prevBtn = findViewById(R.id.previousBtn);
        ImageButton suivBtn = findViewById(R.id.skipBtn);

        prevBtn.setOnClickListener(v -> {
            cyclePreced();
        });

        suivBtn.setOnClickListener(v -> {
            cycleSuivant();
        });

        suivBtn.setEnabled(false);
        prevBtn.setEnabled(false);

        getEntrainementDb(entrainementUid);
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(this, "Destroyed", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        Toast.makeText(this, "STOP", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onResume() {
        Toast.makeText(this, "Resume", Toast.LENGTH_SHORT).show();

        super.onResume();
    }

    public void toggleStarStop(View v){
        if(compteur.isStarted()){
            this.onPause(v);
        }
        else{
            this.onStart(v);
        }
    }

    // Lancer le compteur
    public void onStart(View view) {
        compteur.start();
        view.setBackgroundResource(R.drawable.ic_baseline_pause_24);
    }

    // Mettre en pause le compteur
    public void onPause(View view) {
        compteur.pause();
        view.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
    }


    // Mise à jour graphique
    private void miseAJour() {
        // Affichage des informations du compteur
        timerValue.setText("" + compteur.getMinutes() + ":" + String.format("%02d", compteur.getSecondes()));

    }

    /**
     * Méthode appelée à chaque update du compteur (l'activité est abonnée au compteur)
     *
     */
    @Override
    public void onUpdate() {
        checkButtons();
        miseAJour();
        if(compteur.getUpdatedTime() == 0){
            cycleSuivant();
        }
    }

    private void getEntrainementDb(int idEnt) {
        // Classe asynchrone permettant de récupérer les entrainements et de mettre à jour l'UI
        class GetEntrainement extends AsyncTask<Void, Void, Entrainement> {

            @Override
            protected Entrainement doInBackground(Void... voids) {
                Entrainement dbEnt = db.getAppDatabase().entrainementDao().loadById(idEnt);

                if(dbEnt == null){
                    return new Entrainement("Nouvel entrainements");
                }
                return dbEnt;

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(Entrainement entrainement) {
                super.onPostExecute(entrainement);
                initTimer(entrainement);
            }
        }

        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetEntrainement ge = new GetEntrainement();
        ge.execute();
    }

    private void initTimer(Entrainement entrainement) {
        HashMap<String, ArrayList> datas = entrainement.getTimesList();
        names = datas.get("names");
        times = datas.get("times");
        setCycle(names.get(currentPos), times.get(currentPos));
    }

    private void setCycle(String name, int time){
        compteur.setUpdatedTime((long) time*1000);
        LinearLayout suivants = findViewById(R.id.suivants);
        suivants.removeAllViews();
        for (int i = currentPos; i < times.size(); i++){
            TextView tvSuiv = new TextView(this);
            if(i < times.size()-1){
                tvSuiv.setText(names.get(i+1) + " " + times.get(i+1) + "s");
            }
            else{
                tvSuiv.setText("Fin !");
            }
            tvSuiv.setTextSize(25);
            tvSuiv.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            suivants.addView(tvSuiv, i-currentPos);
        }
        TextView tvName = findViewById(R.id.timerName);
        tvName.setText(name);

        compteur.update();
    }

    private void cycleSuivant(){
        compteur.stop();
        if(currentPos < times.size()-1) {
            currentPos++;
            setCycle(names.get(currentPos), times.get(currentPos));
            compteur.start();
        }
        else{
            timerValue.setText("FIN");
            TextView tvName = findViewById(R.id.timerName);
            tvName.setText("Bien joué !");
            findViewById(R.id.playPauseBtn).setEnabled(false);
        }
        checkButtons();
    }

    private void cyclePreced(){
        compteur.stop();
        if(currentPos > 0) {
            currentPos--;
            setCycle(names.get(currentPos), times.get(currentPos));
            compteur.start();
        }
    }

    private void checkButtons(){
        ImageButton prevBtn = findViewById(R.id.previousBtn);
        ImageButton suivBtn = findViewById(R.id.skipBtn);

        if(currentPos > 0 && compteur.isStarted()){
            prevBtn.setEnabled(true);
        }
        else{
            prevBtn.setEnabled(false);
        }

        if(currentPos >= times.size() || !compteur.isStarted()){
            suivBtn.setEnabled(false);
        }
        else{
            suivBtn.setEnabled(true);
        }
    }
}