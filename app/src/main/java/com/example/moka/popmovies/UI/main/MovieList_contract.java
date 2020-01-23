package com.example.moka.popmovies.UI.main;

import com.example.moka.popmovies.Models.movie;
import com.example.moka.popmovies.utilities.BasePresenter;
import com.example.moka.popmovies.utilities.BaseView;

import java.util.List;

public interface MovieList_contract {
    interface View extends BaseView<Presenter> {

        void showFavoriteMovies(List<movie> movies);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void get_favorites_Presenter();
    }
}
