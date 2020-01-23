package com.example.moka.popmovies.api;

public interface IonlineResponse {
    void onDataError();
    void onDataFetched(Object data);
}
