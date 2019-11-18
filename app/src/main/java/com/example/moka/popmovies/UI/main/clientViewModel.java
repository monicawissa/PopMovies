package com.example.moka.popmovies.UI.main;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.widget.Toast;

import com.example.moka.popmovies.UI.main.RecyclerViewAdapter;
import com.example.moka.popmovies.api.client;
import com.example.moka.popmovies.jsonmovie.MovieResults;
import com.example.moka.popmovies.jsonmovie.movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class clientViewModel extends ViewModel {
    public MutableLiveData<List<movie>> listItems=new MutableLiveData<>();

    public void getpopularMovies(){
        client.getinstance().getpopularMovies().enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {
                listItems.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "Can't Fetch the data ", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void getTopRatedMovies(){
        client.getinstance().getTopRatedMovies().enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {
                listItems.setValue(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                //Toast.makeText(getApplicationContext(), "Can't Fetch the data ", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
