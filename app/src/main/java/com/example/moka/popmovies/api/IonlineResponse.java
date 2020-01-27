package com.example.moka.popmovies.api;

import com.example.moka.popmovies.Models.Cast;
import com.example.moka.popmovies.Models.Review;
import com.example.moka.popmovies.Models.Trailer;
import com.example.moka.popmovies.Models.movie;

import java.util.List;
import java.util.ListIterator;

public interface IonlineResponse {
    void onDataError();
    void onDataFetched(Object data);


}
