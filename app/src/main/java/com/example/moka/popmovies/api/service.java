package com.example.moka.popmovies.api;

import com.example.moka.popmovies.jsonmovie.MovieResults;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface service {
    @GET("movie/popular")
    Call<MovieResults> getpopularMovies(
      @Query("api_key")String api_key,
      @Query("language")String lang,
      @Query("page")String page

      );
    @GET("movie/top_rated")
    Call<MovieResults> getTopRatedMovies(
            @Query("api_key")String api_key,
            @Query("language")String lang,
            @Query("page")String page

    );
}
