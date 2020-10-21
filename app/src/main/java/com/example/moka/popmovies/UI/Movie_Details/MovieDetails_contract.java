package com.example.moka.popmovies.UI.Movie_Details;

import android.support.annotation.NonNull;

import com.example.moka.popmovies.UI.main.MoviesFilterType;
import com.example.moka.popmovies.data.Models.Cast;
import com.example.moka.popmovies.data.Models.Movie;
import com.example.moka.popmovies.data.Models.Review;
import com.example.moka.popmovies.data.Models.Trailer;
import com.example.moka.popmovies.utilities.BasePresenter;
import com.example.moka.popmovies.utilities.BaseView;

import java.util.List;

public interface MovieDetails_contract {
    interface View extends BaseView<Presenter> {
        //void setLoadingIndicator(boolean active);
        void showErrorMessage(String message);
        void showReview(List<Review> Movies);
        void showTrailer(List<Trailer> Movies);
        void showCast(List<Cast> Movies);
        void showNotFavorite();
        void showSuccessfullyDeleteFavoritesMessage();
        void showSuccessfullyAddFavoritesMessage();
        boolean isActive();

        void showisFavorite();
    }

    interface Presenter extends BasePresenter {
        void execute(Boolean forceUpdate);
        void getMovieActors();
        void getMovieTrailer();
        void getMovieReview();
        void getFavoriteFormDB();

        void addFavorite();
        void favoriteButtonClicked();
        void deleteFavorite();
        void setMovie(Movie movieId);
        Movie getMovie();
    }
}
