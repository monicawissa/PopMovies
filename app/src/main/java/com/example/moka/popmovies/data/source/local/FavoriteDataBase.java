package com.example.moka.popmovies.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.moka.popmovies.data.Models.Movie;

@Database(entities = {Movie.class},version = 1,exportSchema = false)
public abstract class FavoriteDataBase extends RoomDatabase {

    private static FavoriteDataBase instance;
    public abstract FavoriteDao FavoriteDao();

    public static  synchronized FavoriteDataBase getInstance(Context context){
        if(instance==null){
            instance =Room.databaseBuilder(context.getApplicationContext()
                    ,FavoriteDataBase.class,"Favorite_database")
                    .build();

        }return instance;
    }
    
}
