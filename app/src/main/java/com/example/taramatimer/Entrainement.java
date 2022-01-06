package com.example.taramatimer;

import android.graphics.Color;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@Entity(tableName = "entrainements")
public class Entrainement {
    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "time_prepare")
    private int timePrepare;

    @ColumnInfo(name = "time_work")
    private int timeWork;

    @ColumnInfo(name = "time_rest")
    private int timeRest;

    @ColumnInfo(name = "nb_cycles")
    private int nbCycles;

    @ColumnInfo(name = "nb_sets")
    private int nbSets;

    @ColumnInfo(name = "time_rest_set")
    private int timeRestSet;

    @ColumnInfo(name = "time_cooldown")
    private int timeCooldown;

    @ColumnInfo(name = "icon_id")
    private int icon;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "color_code")
    private int color;

    public Entrainement(int timePrepare, int timeWork, int timeRest, int nbCycles, int nbSets, int timeRestSet, int timeCooldown, int icon, String name, int color) {
        this.timePrepare = timePrepare;
        this.timeWork = timeWork;
        this.timeRest = timeRest;
        this.nbCycles = nbCycles;
        this.nbSets = nbSets;
        this.timeRestSet = timeRestSet;
        this.timeCooldown = timeCooldown;
        this.icon = icon;
        this.name = name;
        this.color = color;
    }

    @Ignore
    public Entrainement(String name) {
        this.timePrepare = 10;
        this.timeWork = 20;
        this.timeRest = 10;
        this.nbCycles = 6;
        this.nbSets = 2;
        this.timeRestSet = 30;
        this.timeCooldown = 0;
        this.icon = R.drawable.ic_baseline_sports_24;
        this.name = name;
        this.color = Color.WHITE;
    }


    public int getTotalDurtion(){
        int initialDelays = getTimePrepare() + getTimeCooldown();
        int cycleDelays = getTimeWork() + getTimeRest();
        int cycles = cycleDelays*getNbCycles();

        return initialDelays + ((cycles + getTimeRestSet())*getNbSets());
    }

    public HashMap<String, ArrayList> getTimesList(){
        ArrayList<Integer> times = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();
        if(timePrepare > 0){
            times.add(timePrepare);
            names.add("Preparation");
        }
        for (int set = 0; set < nbSets; set++) {
            for (int cycle = 0; cycle < nbCycles; cycle++) {
                times.add(timeWork);
                names.add("Travail");
                times.add(timeRest);
                names.add("Repos");
            }
            times.add(timeRestSet);
            names.add("Repos long");
        }
        if(timeCooldown > 0){
            times.add(timeCooldown);
            names.add("Repos de fin");
        }

        HashMap<String, ArrayList> ret = new HashMap<>();
        ret.put("times", times);
        ret.put("names", names);
        return ret;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getTimePrepare() {
        return timePrepare;
    }

    public void setTimePrepare(int timePrepare) {
        this.timePrepare = timePrepare;
    }

    public int getTimeWork() {
        return timeWork;
    }

    public void setTimeWork(int timeWork) {
        this.timeWork = timeWork;
    }

    public int getTimeRest() {
        return timeRest;
    }

    public void setTimeRest(int timeRest) {
        this.timeRest = timeRest;
    }

    public int getNbCycles() {
        return nbCycles;
    }

    public void setNbCycles(int nbCycles) {
        this.nbCycles = nbCycles;
    }

    public int getNbSets() {
        return nbSets;
    }

    public void setNbSets(int nbSets) {
        this.nbSets = nbSets;
    }

    public int getTimeRestSet() {
        return timeRestSet;
    }

    public void setTimeRestSet(int timeRestSet) {
        this.timeRestSet = timeRestSet;
    }

    public int getTimeCooldown() {
        return timeCooldown;
    }

    public void setTimeCooldown(int timeCooldown) {
        this.timeCooldown = timeCooldown;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }
}
