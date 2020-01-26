package com.example.myclosetapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ClotheDAO {

    @Insert
    void insertClothe(Clothe clothe);
    @Update
    void updateClothe(Clothe clothe);
    @Delete
    void deleteClothe(Clothe clothe);

    @Query("SELECT * FROM clothe_table")
    LiveData<List<Clothe>>  getAllClothes();

    @Query("SELECT * FROM clothe_table WHERE color= :color")
    LiveData<List<Clothe>> getClothesByColor(String color);

}
