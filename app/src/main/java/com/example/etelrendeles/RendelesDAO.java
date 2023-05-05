package com.example.etelrendeles;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RendelesDAO {
    @Insert
    void add(Rendeles rendeles);

    @Query("SELECT * FROM Rendeles WHERE rendelo = :rendelo")
    LiveData<List<Rendeles>> getByRendelo(String rendelo);
    @Delete
    void delete(Rendeles rendeles);

    @Query("Delete FROM Rendeles")
    void deleteAll();

    @Query("Delete FROM Rendeles WHERE rendelo=:rendelo")
    void deleteAllByRendelo(String rendelo);

    @Query("SELECT * FROM Rendeles")
    LiveData<List<Rendeles>> getAll();

    @Query("SELECT * FROM Rendeles")
    List<Rendeles> getAllList();

    @Query("SELECT SUM(ar) FROM Rendeles WHERE rendelo=:rendelo")
    int osszeg(String rendelo);

    @Query("UPDATE Rendeles SET mennyiseg=:mennyiseg, ar=:ar WHERE rendelo=:rendelo AND etelNev=:etelNev")
    void update(String rendelo, String etelNev, int mennyiseg, int ar);
}
