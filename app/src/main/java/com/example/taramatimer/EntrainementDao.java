package com.example.taramatimer;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EntrainementDao {
    @Query("SELECT * FROM entrainements")
    List<Entrainement> getAll();

    @Query("SELECT * FROM entrainements WHERE uid IN (:ids)")
    List<Entrainement> loadAllByIds(int[] ids);

    @Query("SELECT * FROM entrainements WHERE uid = (:id)")
    Entrainement loadById(int id);

    @Update
    void updateAll(Entrainement... entrainements);

    @Insert
    void insertAll(Entrainement... entrainements);

    @Delete
    void delete(Entrainement entrainement);
}