package com.example.moka.popmovies.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;


import com.example.moka.popmovies.data.Models.Movie;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertFavorite(Movie FavoriteEntry);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavorite(Movie FavoriteEntry);

    @Query("DELETE FROM Favorite_table WHERE id =:Favorite_id")
    void deleteFavoritebyId(int Favorite_id);


    @Query("SELECT * FROM Favorite_table")
    List<Movie> getAllFavorite();

    @Query("SELECT *FROM Favorite_table WHERE id =:Favoriteid")
    Movie getFavoriteById(int Favoriteid);

    @Query("DELete From Favorite_table")
    void deletetable();
}
