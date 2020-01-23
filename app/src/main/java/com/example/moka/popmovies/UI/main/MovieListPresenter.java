package com.example.moka.popmovies.UI.main;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.moka.popmovies.Models.movie;
import com.example.moka.popmovies.R;
import com.example.moka.popmovies.Room.Favorite;
import com.example.moka.popmovies.Room.FavoriteViewModel;

import java.util.ArrayList;
import java.util.List;

public class MovieListPresenter implements MovieList_contract.Presenter {
    private final FragmentActivity mactivity;
    private final MovieList_contract.View mview;

    public MovieListPresenter(FragmentActivity mactivity, MovieFragment fragment) {
        this.mactivity = mactivity;
        mview=fragment;
        fragment.setPresenter(this);
    }

    @Override
    public void start() {
    }
    @Override
    public void get_favorites_Presenter(){
        FavoriteViewModel nodeViewModel = ViewModelProviders.of((FragmentActivity) mactivity).get(FavoriteViewModel.class);
        nodeViewModel.getAllFavorites().observe((LifecycleOwner)this,new Observer<List<Favorite>>(){

            @Override
            public void onChanged(@Nullable List<Favorite> notes) {
                //update RecycleViewAdapter

                List<movie> movies = new ArrayList<>();
                if(notes==null){
                    Toast.makeText(mactivity,R.string.null_data, Toast.LENGTH_SHORT).show();
                    return;
                }
                //assert notes!=null;
                for (Favorite i :notes ){
                    movie movie = new movie();
                    movie.setId(i.getFavoriteid());
                    movie.setOverview(i.getOverview());
                    movie.setTitle(i.getTitle());
                    movie.setOriginalTitle(i.getTitle());
                    movie.setReleaseDate(i.getReleaseDate());
                    movie.setBackdropPath(i.getBackdropPath());
                    movie.setPosterPath(i.getPosterpath());
                    movie.setVoteAverage(i.getUserrating());
                    movie.setFavorite(true);
                    movies.add(movie);
                }
                Log.d("TAGGG", "favorite movie size "+movies.size());
                mview.showFavoriteMovies(movies);
            }
        });


    }
}
