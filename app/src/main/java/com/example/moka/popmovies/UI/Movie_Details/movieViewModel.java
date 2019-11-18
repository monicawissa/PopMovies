package com.example.moka.popmovies.UI.Movie_Details;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.moka.popmovies.api.client;
import com.example.moka.popmovies.jsonmovie.ActorResult;
import com.example.moka.popmovies.jsonmovie.Cast;
import com.example.moka.popmovies.jsonmovie.Review;
import com.example.moka.popmovies.jsonmovie.ReviewResult;
import com.example.moka.popmovies.jsonmovie.Trailer;
import com.example.moka.popmovies.jsonmovie.TrailerResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class movieViewModel extends ViewModel {
    public MutableLiveData<List<Trailer>> TrailerItems=new MutableLiveData<>();
    public MutableLiveData<List<Review>> ReviewItems=new MutableLiveData<>();
    public MutableLiveData<List<Cast>> CastItems=new MutableLiveData<>();

    public void getMovieTrailer(int movie_id){
        client.getinstance().getMovieTrailer(movie_id).enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                TrailerItems.setValue(response.body().getTrailers());
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {

            }
        });
    }
    public void getReviewTrailer(int movie_id){
        client.getinstance().getReviewTrailer(movie_id).enqueue(new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                ReviewItems.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {

            }
        });
    }
    public void getActorsMovies(int movie_id){
        client.getinstance().getActorsMovies(movie_id).enqueue(new Callback<ActorResult>() {
            @Override
            public void onResponse(Call<ActorResult> call, Response<ActorResult> response) {
                CastItems.setValue(response.body().getCast());
            }

            @Override
            public void onFailure(Call<ActorResult> call, Throwable t) {

            }
        });
    }
}
