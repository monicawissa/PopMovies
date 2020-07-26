package com.example.moka.popmovies.data.Models;

import java.util.ArrayList;
import java.util.List;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ActorResult implements Parcelable
{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("cast")
    @Expose
    private List<Cast> cast = new ArrayList<Cast>();

    public final static Parcelable.Creator<ActorResult> CREATOR = new Creator<ActorResult>() {


        @SuppressWarnings({
                "unchecked"
        })
        public ActorResult createFromParcel(Parcel in) {
            return new ActorResult(in);
        }

        public ActorResult[] newArray(int size) {
            return (new ActorResult[size]);
        }

    }
            ;

    protected ActorResult(Parcel in) {
        this.id = ((Integer) in.readValue((Integer.class.getClassLoader())));
        in.readList(this.cast, (com.example.moka.popmovies.data.Models.Cast.class.getClassLoader()));
    }

    public ActorResult() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }


    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeList(cast);
    }

    public int describeContents() {
        return 0;
    }

}