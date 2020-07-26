package com.example.moka.popmovies.UI.main;

import com.example.moka.popmovies.data.Models.Movie;
import com.example.moka.popmovies.utilities.BasePresenter;
import com.example.moka.popmovies.utilities.BaseView;

import java.util.List;

public interface MovieList_contract {
    interface View extends BaseView<Presenter> {
        void setLoadingIndicator(boolean active);
        void showErrorMessage(String message);
        void showMovies(List<Movie> Movies);
        void showSuccessfullyDeleteFavoritesMessage();
        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void execute(Boolean forceUpdate);
        void getAllFavorite(final Boolean showLoadingUI );
        void getpopularMovies(final Boolean showLoadingUI);
        void getTopRatedMovies(final Boolean showLoadingUI);
        void deleteAllFavorite(final Boolean showLoadingUI);
        void setFiltering(MoviesFilterType requestType);
        MoviesFilterType getFiltering();
    }
}
