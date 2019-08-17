package com.example.moka.popmovies.Room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import java.util.List;

@Dao
public interface FavoriteDao {



    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Favorite FavoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Favorite FavoriteEntry);


    @Query("SELECT * FROM Favorite_table")
    LiveData<List<Favorite>> getAllFavorite();

    @Query("SELECT *FROM Favorite_table WHERE Favoriteid =:Favoriteid")
    LiveData<Favorite> getFavoriteById(int Favoriteid);

    @Query("DELete From Favorite_table")
    void deletetable();

    @Query("DELETE FROM Favorite_table WHERE Favoriteid =:Favorite_id")
    void deleteFavoritebyId(int Favorite_id);
}
