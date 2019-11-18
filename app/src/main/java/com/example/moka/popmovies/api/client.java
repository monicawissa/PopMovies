package com.example.moka.popmovies.api;

import com.example.moka.popmovies.BuildConfig;
import com.example.moka.popmovies.jsonmovie.ActorResult;
import com.example.moka.popmovies.jsonmovie.MovieResults;
import com.example.moka.popmovies.jsonmovie.ReviewResult;
import com.example.moka.popmovies.jsonmovie.TrailerResult;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

public class client extends retrofitt{
    private service serv;
    private final String language="en-US";
    private static client instance;
    public client() {

        serv=new retrofitt().getRetrofit("https://api.themoviedb.org/3/",GsonConverterFactory.create())
            .create(service.class);
    }


    public static client getinstance(){
        if(instance==null)instance=new client();
        return instance;
    }
    public Call<MovieResults> getpopularMovies(){
        return serv.getpopularMovies(BuildConfig.The_MovieDBapiToke, language, "1");
    }
    public Call<MovieResults> getTopRatedMovies(){
        return serv.getTopRatedMovies(BuildConfig.The_MovieDBapiToke, language, "1");
    }
    public Call<TrailerResult> getMovieTrailer(int movie_id){
        return serv.getMovieTrailer(movie_id, BuildConfig.The_MovieDBapiToke);
    }
    public Call<ReviewResult> getReviewTrailer(int movie_id){
        return serv.getReviewTrailer(movie_id, BuildConfig.The_MovieDBapiToke);
    }
    public Call<ActorResult> getActorsMovies(int movie_id){
        return serv.getActorsMovies(movie_id, BuildConfig.The_MovieDBapiToke);
    }

}
