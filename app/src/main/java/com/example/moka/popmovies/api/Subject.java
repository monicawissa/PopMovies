package com.example.moka.popmovies.api;

public interface Subject {
    void add(IonlineResponse observer);
    void remove(IonlineResponse observer);
    void Notify();
}
