package com.example.taramatimer;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;

public class DatabaseClient {

    private static DatabaseClient instance;
    private AppDatabase appDatabase;

    private DatabaseClient(final Context context) {
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "TaramaDb").addCallback(roomDatabaseCallback).build();
    }

    public static synchronized DatabaseClient getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseClient(context);
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }

    RoomDatabase.Callback roomDatabaseCallback = new RoomDatabase.Callback() {

        // Called when the database is created for the first time.
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            EntrainementDao entrainementDao = appDatabase.entrainementDao();
            entrainementDao.insertAll(new Entrainement("e1"), new Entrainement("e2"), new Entrainement("e3"), new Entrainement("e4"), new Entrainement("e5"), new Entrainement("eA"));

        }
    };
}