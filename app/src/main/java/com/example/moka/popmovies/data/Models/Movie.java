
package com.example.moka.popmovies.data.Models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "Favorite_table")
public class Movie implements Parcelable
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "vote_count")
    @SerializedName("vote_count")
    @Expose
    private Integer voteCount=0;
    @ColumnInfo(name = "id")
    @SerializedName("id")
    @Expose
    private Integer id=null;
    @ColumnInfo(name = "video")
    @SerializedName("video")
    @Expose
    private Boolean video=false;
    @ColumnInfo(name = "vote_average")
    @SerializedName("vote_average")
    @Expose
    private Double voteAverage=0.0;
    @ColumnInfo(name = "title")
    @SerializedName("title")
    @Expose
    private String title="";
    @ColumnInfo(name = "popularity")
    @SerializedName("popularity")
    @Expose
    private Double popularity=0.0;
    @ColumnInfo(name = "poster_path")
    @SerializedName("poster_path")
    @Expose
    public String posterPath="";

    @ColumnInfo(name = "original_language")
    @SerializedName("original_language")
    @Expose
    private String originalLanguage="";
    @ColumnInfo(name = "original_title")
    @SerializedName("original_title")
    @Expose
    private String originalTitle="";
    @Ignore
    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds ;
    @ColumnInfo(name = "backdrop_path")
    @SerializedName("backdrop_path")
    @Expose
    public String backdropPath="";
    @ColumnInfo(name = "adult")
    @SerializedName("adult")
    @Expose
    private Boolean adult=false;
    @ColumnInfo(name = "overview")
    @SerializedName("overview")
    @Expose
    private String overview="";
    @ColumnInfo(name = "release_date")
    @SerializedName("release_date")
    @Expose
    private String releaseDate="";
    private final static long serialVersionUID = -6614350303280038261L;

    @ColumnInfo(name = "favorite")
    @SerializedName("favorite")
    @Expose
    private Boolean favorite=false;

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        String base_img_url = "https://image.tmdb.org/t/p/w185";
        return base_img_url +posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public String getBackdropPath() {

        String base_img_url = "https://image.tmdb.org/t/p/w185";
        return base_img_url +backdropPath;
    }

    public void setBackdropPath(String backdropPath) {this.backdropPath = backdropPath;}

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeByte(this.adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeList(this.genreIds);
        dest.writeValue(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.originalLanguage);
        dest.writeString(this.title);
        dest.writeString(this.backdropPath);
        dest.writeValue(this.popularity);
        dest.writeValue(this.voteCount);
        dest.writeValue(this.video);
        dest.writeValue(this.voteAverage);
        dest.writeValue(this.favorite);
    }
    protected Movie(Parcel in) {
        this.posterPath = in.readString();
        this.adult = in.readByte() != 0;
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.genreIds = new ArrayList<Integer>();
        in.readList(this.genreIds, Integer.class.getClassLoader());
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originalTitle = in.readString();
        this.originalLanguage = in.readString();
        this.title = in.readString();
        this.backdropPath = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
        this.voteCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.video = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
        this.favorite = (Boolean) in.readValue(Boolean.class.getClassLoader());

    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(){
    }

}