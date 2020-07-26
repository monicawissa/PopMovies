package com.example.moka.popmovies.data.source.remote;

import com.example.moka.popmovies.data.Models.ActorResult;
import com.example.moka.popmovies.data.Models.MovieResults;
import com.example.moka.popmovies.data.Models.ReviewResult;
import com.example.moka.popmovies.data.Models.TrailerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
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
    @GET("movie/{movie_id}/videos")
    Call<TrailerResult> getMovieTrailer(
            @Path("movie_id") int id,
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}/reviews")
    Call<ReviewResult> getMovieReview(
            @Path("id") int id,
            @Query("api_key") String apiKey
    );
    @GET("movie/{id}/casts")
    Call<ActorResult> getMovieActors(
            @Path("id") int id,
            @Query("api_key")String api_key

    );


}
