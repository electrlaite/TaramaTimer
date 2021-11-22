package com.example.taramatimer;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Entrainement.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EntrainementDao entrainementDao();
}
