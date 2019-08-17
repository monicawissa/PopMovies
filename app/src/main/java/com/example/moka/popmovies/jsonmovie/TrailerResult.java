package com.example.moka.popmovies.jsonmovie;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TrailerResult implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<Trailer> Trailers = new ArrayList<Trailer>();
    public final static Parcelable.Creator<Trailer> CREATOR = new Creator<Trailer>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        public Trailer[] newArray(int size) {
            return (new Trailer[size]);
        }

    }
            ;

    protected TrailerResult(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.Trailers, (com.example.moka.popmovies.jsonmovie.Trailer.class.getClassLoader()));
    }

    public TrailerResult() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Trailer> getTrailers() {
        return Trailers;
    }

    public void setTrailers(List<Trailer> Trailers) {
        this.Trailers = Trailers;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(Trailers);
    }

    public int describeContents() {
        return 0;
    }

}