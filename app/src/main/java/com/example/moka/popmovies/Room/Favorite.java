package com.example.moka.popmovies.Room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;



@Entity(tableName = "Favorite_table")
public class Favorite{

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "Favoriteid")
    private int Favoriteid=-1;

    @ColumnInfo(name = "title")
    private String title="";

    @ColumnInfo(name = "userrating")
    private Double userrating=0.0;

    @ColumnInfo(name = "posterpath")
    private String posterpath="";

    @ColumnInfo(name = "overview")
    private String overview="";

    @ColumnInfo(name = "backdropPath")

    private String backdropPath="";

    @ColumnInfo(name = "releaseDate")
    private String releaseDate="";

    public String getBackdropPath() {
        return  backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }



    public Favorite(int Favoriteid, String title, Double userrating, String posterpath, String overview,String releaseDate,String backdropPath) {
        this.Favoriteid = Favoriteid;
        this.title = title;
        this.userrating = userrating;
        this.posterpath = posterpath;
        this.overview = overview;
        this.releaseDate=releaseDate;
        this.backdropPath=backdropPath;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFavoriteid() {
        return Favoriteid;
    }

    public void setFavoriteid(int Favoriteid) {
        this.Favoriteid = Favoriteid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getUserrating() {
        return userrating;
    }

    public void setUserrating(Double userrating) {
        this.userrating = userrating;
    }

    public void setPosterpath(String posterpath){ this.posterpath = posterpath; }

    public String getPosterpath() {  return posterpath;}



    public void setOverview(String overview) { this.overview = overview; }

    public String getOverview() { return overview; }


}