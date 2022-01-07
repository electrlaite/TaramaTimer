package com.example.taramatimer;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.graphics.ColorSpace;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.internal.FlowLayout;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddEntrainement extends AppCompatActivity {

    public static String KEY_ENTRAINEMENT_ID = "idEtrainement";
    private Entrainement ent;
    private AlertDialog dialogIcon;
    private DatabaseClient db;
    private int entId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onStart() {
        super.onStart();
        db = DatabaseClient.getInstance(getApplicationContext());
        entId = getIntent().getIntExtra(KEY_ENTRAINEMENT_ID, -1);
        setContentView(R.layout.activity_add_entrainement);
        Button btnSubmit = findViewById(R.id.submit_btn);
        btnSubmit.setOnClickListener(v -> {
            submitEntrainement();
        });
        if (entId > -1) {
            getEntrainementDb(entId);
        } else {
            ent = new Entrainement("Nouvel entrainement");
            setFieldsValue();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setFieldsValue() {
        TextView tvName = findViewById(R.id.name_value);
        TextView tvPrepare = findViewById(R.id.prepare_value);
        TextView tvWork = findViewById(R.id.work_value);
        TextView tvRest = findViewById(R.id.rest_value);
        TextView tvCycles = findViewById(R.id.cycles_value);
        TextView tvSets = findViewById(R.id.sets_value);
        TextView tvRepos = findViewById(R.id.restSet_value);
        TextView tvCooldown = findViewById(R.id.cooldown_value);

        tvName.setText(ent.getName());
        tvPrepare.setText(String.valueOf(ent.getTimePrepare()));
        tvWork.setText(String.valueOf(ent.getTimeWork()));
        tvRest.setText(String.valueOf(ent.getTimeRest()));
        tvCycles.setText(String.valueOf(ent.getNbCycles()));
        tvSets.setText(String.valueOf(ent.getNbSets()));
        tvRepos.setText(String.valueOf(ent.getTimeRestSet()));
        tvCooldown.setText(String.valueOf(ent.getTimeCooldown()));

        ImageView icon = findViewById(R.id.icon_icon);
        icon.setImageResource(ent.getIcon());

        selectColorUpdate(ent.getColor());
        selectIcon(ent.getIcon());

        Color color = Color.valueOf(ent.getColor());
        final ColorPicker cp = new ColorPicker(AddEntrainement.this, (int) (color.red() * 255), (int) (color.green() * 255), (int) (color.blue() * 255));
        cp.enableAutoClose();
        cp.setCallback(colorSelected -> {
            selectColorUpdate(colorSelected);
            ent.setColor(colorSelected);
        });
        Button okColor = (Button) findViewById(R.id.color_btn);
        okColor.setOnClickListener(v -> {
            cp.show();
        });
    }

    public void selectColorUpdate(int colorSelected) {
        Button selector = (Button) findViewById(R.id.color_btn);
        selector.setBackgroundColor(colorSelected);
        if (Color.red(colorSelected) > 200 && Color.blue(colorSelected) > 200 && Color.green(colorSelected) > 200) {
            selector.setTextColor(Color.BLACK);
        } else {
            selector.setTextColor(Color.WHITE);
        }
    }

    public void showIconDialog(View v) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        builder.setTitle("Choisir un icon");
        Field[] drawablesFields = R.drawable.class.getFields();

        HashMap<String, Integer> icons = new HashMap<>();
        icons.put("Cycle", R.drawable.ic_baseline_cached_24);
        icons.put("Rest", R.drawable.ic_baseline_chair_24);
        icons.put("Hiking", R.drawable.ic_baseline_hiking_24);
        icons.put("Sports", R.drawable.ic_baseline_sports_24);
        icons.put("Cricket", R.drawable.ic_baseline_sports_cricket_24);
        icons.put("Martial", R.drawable.ic_baseline_sports_martial_arts_24);
        icons.put("MMA", R.drawable.ic_baseline_sports_mma_24);
        icons.put("Soccer", R.drawable.ic_baseline_sports_soccer_24);
        icons.put("Tennis", R.drawable.ic_baseline_sports_tennis_24);
        icons.put("Voleyball", R.drawable.ic_baseline_sports_volleyball_24);
        icons.put("Running", R.drawable.ic_running_icon);
        icons.put("Ski", R.drawable.ic_baseline_snowboarding_24);
        icons.put("Speed", R.drawable.ic_baseline_speed_24);
        icons.put("Baseball", R.drawable.ic_baseline_sports_baseball_24);
        icons.put("Basketball", R.drawable.ic_baseline_sports_basketball_24);
        icons.put("Football", R.drawable.ic_baseline_sports_football_24);
        icons.put("Gym", R.drawable.ic_baseline_sports_gymnastics_24);
        icons.put("Handball", R.drawable.ic_baseline_sports_handball_24);
        icons.put("Hockey", R.drawable.ic_baseline_sports_hockey_24);
        icons.put("Moto", R.drawable.ic_baseline_sports_motorsports_24);
        icons.put("Surf", R.drawable.ic_baseline_surfing_24);
        icons.put("Try hard", R.drawable.ic_baseline_whatshot_24);
        icons.put("Alcool", R.drawable.ic_baseline_sports_bar_24);

        ScrollView sc = new ScrollView(this);
        sc.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout linV = new LinearLayout(this);
        linV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        linV.setOrientation(LinearLayout.VERTICAL);
        final float scale = this.getResources().getDisplayMetrics().density;
        int pixels = (int) (65 * scale + 0.5f);

        for (Map.Entry<String, Integer> entry : icons.entrySet()) {
            String name = entry.getKey();
            int icon = entry.getValue();
            LinearLayout lin = new LinearLayout(this);
            lin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            lin.setOrientation(LinearLayout.HORIZONTAL);
            ImageView im = new ImageView(this);
            im.setLayoutParams(new LinearLayout.LayoutParams(pixels, pixels));
            im.setImageResource(icon);
            lin.addView(im);
            TextView tv = new TextView(this);
            tv.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            tv.setText(name);
            lin.addView(tv);
            lin.setOnClickListener(v1 -> {
                selectIcon(icon);
                closeIconDialog();
            });
            linV.addView(lin);
        }

        sc.addView(linV);
        builder.setView(sc);
        this.dialogIcon = builder.create();
        dialogIcon.show();
    }

    public void closeIconDialog() {
        this.dialogIcon.dismiss();
    }

    public void selectIcon(int icon) {
        ImageView iconIcon = findViewById(R.id.icon_icon);
        iconIcon.setImageResource(icon);
        this.ent.setIcon(icon);
    }

    private void getEntrainementDb(int idEnt) {
        // Classe asynchrone permettant de récupérer les entrainements et de mettre à jour l'UI
        class GetEntrainement extends AsyncTask<Void, Void, Entrainement> {

            @Override
            protected Entrainement doInBackground(Void... voids) {
                Entrainement dbEnt = db.getAppDatabase().entrainementDao().loadById(idEnt);

                if (dbEnt == null) {
                    return new Entrainement("Nouvel entrainements");
                }
                return dbEnt;

            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(Entrainement entrainement) {
                super.onPostExecute(entrainement);
                setEnt(entrainement);
                setFieldsValue();
            }
        }

        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        GetEntrainement ge = new GetEntrainement();
        ge.execute();
    }

    private void uploadEntrainement(Entrainement entrainement) {
        // Classe asynchrone permettant de récupérer les entrainements et de mettre à jour l'UI
        class uploadEnt extends AsyncTask<Void, Void, Entrainement> {

            @Override
            protected Entrainement doInBackground(Void... voids) {
                if (entId > -1) {
                    db.getAppDatabase().entrainementDao().updateAll(entrainement);
                    Toast.makeText(AddEntrainement.this, "Entrainement " + entrainement.getName() + " modifié !" + entrainement.getUid(), Toast.LENGTH_LONG).show();
                } else {
                    db.getAppDatabase().entrainementDao().insertAll(entrainement);
                    Toast.makeText(AddEntrainement.this, "Entrainement " + entrainement.getName() + " crée !" + entrainement.getUid(), Toast.LENGTH_LONG).show();
                }


                return entrainement;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(Entrainement entrainement) {
                super.onPostExecute(entrainement);
                //Toast.makeText(AddEntrainement.this, "Entrainement " + entrainement.getName() + " crée/modifié !", Toast.LENGTH_LONG).show();
                AddEntrainement.this.finish();
            }
        }

        // Création d'un objet de type GetTasks et execution de la demande asynchrone
        uploadEnt uploader = new uploadEnt();
        uploader.execute();
    }

    public void setEnt(Entrainement ent) {
        this.ent = ent;
    }

    public void submitEntrainement() {
        TextView tvName = findViewById(R.id.name_value);
        TextView tvPrepare = findViewById(R.id.prepare_value);
        TextView tvWork = findViewById(R.id.work_value);
        TextView tvRest = findViewById(R.id.rest_value);
        TextView tvCycles = findViewById(R.id.cycles_value);
        TextView tvSets = findViewById(R.id.sets_value);
        TextView tvRepos = findViewById(R.id.restSet_value);
        TextView tvCooldown = findViewById(R.id.cooldown_value);

        String entName = tvName.getText().toString();
        int entPrepare = Integer.parseInt((tvPrepare.getText().toString().length() == 0 ? "0" : tvPrepare.getText().toString()));
        int entWork = Integer.parseInt((tvWork.getText().toString().length() == 0 ? "10" : tvWork.getText().toString()));
        int entRest = Integer.parseInt((tvRest.getText().toString().length() == 0 ? "5" : tvRest.getText().toString()));
        int entCycles = Integer.parseInt(tvCycles.getText().toString().length() == 0 ? "1" : tvCycles.getText().toString());
        int entSets = Integer.parseInt((tvSets.getText().toString().length() == 0 ? "1" : tvSets.getText().toString()));
        int entRepos = Integer.parseInt((tvRepos.getText().toString().length() == 0 ? "0" : tvRepos.getText().toString()));
        int entCooldown = Integer.parseInt((tvCooldown.getText().toString().length() == 0 ? "0" : tvCooldown.getText().toString()));
        if (entName.length() < 1 || entName.length() > 30) {
            Toast.makeText(this, "Invalid Title", Toast.LENGTH_LONG).show();
            tvName.setTextColor(Color.RED);
            return;
        }
        if (entPrepare < 0 || entPrepare > 1000) {
            Toast.makeText(this, "Invalid Preparation time", Toast.LENGTH_LONG).show();
            tvPrepare.setTextColor(Color.RED);
            return;
        }
        if (entWork < 1 || entWork > 1000) {
            Toast.makeText(this, "Invalid work time", Toast.LENGTH_LONG).show();
            tvWork.setTextColor(Color.RED);
            return;
        }
        if (entRest < 1 || entRest > 1000) {
            Toast.makeText(this, "Invalid rest time", Toast.LENGTH_LONG).show();
            tvRest.setTextColor(Color.RED);
            return;
        }
        if (entCycles < 1 || entCycles > 1000) {
            Toast.makeText(this, "Invalid number of cycles", Toast.LENGTH_LONG).show();
            tvCycles.setTextColor(Color.RED);
            return;
        }
        if (entSets < 1 || entSets > 1000) {
            Toast.makeText(this, "Invalid number of sets", Toast.LENGTH_LONG).show();
            tvSets.setTextColor(Color.RED);
            return;
        }
        if (entRepos < 0 || entRepos > 1000) {
            Toast.makeText(this, "Invalid rest between sets time", Toast.LENGTH_LONG).show();
            tvRepos.setTextColor(Color.RED);
            return;
        }
        if (entCooldown < 0 || entCooldown > 1000) {
            Toast.makeText(this, "Invalid cooldown time", Toast.LENGTH_LONG).show();
            tvCooldown.setTextColor(Color.RED);
            return;
        }

        ent.setName(entName);
        ent.setTimePrepare(entPrepare);
        ent.setTimeWork(entWork);
        ent.setTimeRest(entRest);
        ent.setNbCycles(entCycles);
        ent.setNbSets(entSets);
        ent.setTimeRestSet(entRepos);
        ent.setTimeCooldown(entCooldown);

        uploadEntrainement(ent);
    }


}