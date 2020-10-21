package com.example.moka.popmovies.UI.Movie_Details;

import android.support.annotation.NonNull;
import android.widget.Button;

import com.example.moka.popmovies.UI.main.MoviesFilterType;
import com.example.moka.popmovies.data.Models.Cast;
import com.example.moka.popmovies.data.Models.Movie;
import com.example.moka.popmovies.data.Models.Review;
import com.example.moka.popmovies.data.Models.Trailer;
import com.example.moka.popmovies.data.source.AppRepository;
import com.example.moka.popmovies.data.source.BaseDataSource;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class MovieDetailsPresenter implements MovieDetails_contract.Presenter {
    private final MovieDetails_contract.View mview;
    private int movieId;
    private final AppRepository appRepository;
    private boolean mFirstLoad = true;
    private Movie movie;

    public MovieDetailsPresenter(@NonNull MovieDetails_contract.View view,
                                 @NonNull AppRepository appRepository) {
        mview=checkNotNull(view);
        this.appRepository = checkNotNull(appRepository);
        mview.setPresenter(this);
    }


    @Override
    public void start() {
        execute(false);
    }

    @Override
    public void execute(Boolean forceUpdate) {
        getMovieActors();
        getMovieReview();
        getMovieTrailer();
        getFavoriteFormDB();
    }

    @Override
    public void getFavoriteFormDB() {
        appRepository.getFavorite(movieId, new BaseDataSource.FavoriteDataSource.GetFavoriteCallback() {
            @Override
            public void onFavoriteLoaded(Movie Favorite) {
                if (!mview.isActive()) {
                    return;
                }
                movie.setFavorite(true);
                mview.showisFavorite();
            }

            @Override
            public void onDataNotAvailable() {
                movie.setFavorite(false);
                 mview.showNotFavorite();
            }
        });
    }
    @Override
    public void favoriteButtonClicked(){
        if(movie.getFavorite())
            deleteFavorite();
        else addFavorite();
    }
    @Override
    public void addFavorite() {

        movie.setFavorite(true);
        appRepository.addFavorite(movie);
        mview.showisFavorite();
        mview.showSuccessfullyAddFavoritesMessage();
    }
    @Override
    public void deleteFavorite() {
        appRepository.deleteFavorite(movieId);
        movie.setFavorite(false);
        mview.showNotFavorite();
        mview.showSuccessfullyDeleteFavoritesMessage();
    }

    @Override
    public void setMovie(Movie movi) {
        this.movie=movi;
        this.movieId=movi.getId();
    }

    @Override
    public Movie getMovie() {
        return movie;
    }

    @Override
    public void getMovieActors() {
        appRepository.getMovieActors(new BaseDataSource.MoviesDataSource.IResponseCast() {
            @Override
            public void OnResponseCast(List<Cast> data) {
                if (!mview.isActive()) {
                    return;
                }
                mview.showCast(data);
            }

            @Override
            public void onDataNotAvailable(String message) {
                mview.showErrorMessage(message);
            }
        },movieId);
    }

    @Override
    public void getMovieTrailer() {
        appRepository.getMovieTrailer(new BaseDataSource.MoviesDataSource.IResponseTrailer() {
            @Override
            public void OnResponseTrailer(List<Trailer> data) {
                if (!mview.isActive()) {
                    return;
                }
                mview.showTrailer(data);
            }

            @Override
            public void onDataNotAvailable(String message) {
                mview.showErrorMessage(message);
            }
        },movieId);
    }

    @Override
    public void getMovieReview() {
        appRepository.getMovieReview(new BaseDataSource.MoviesDataSource.IResponseReview() {
            @Override
            public void OnResponseReview(List<Review> data) {
                if (!mview.isActive()) {
                    return;
                }
                mview.showReview(data);
            }

            @Override
            public void onDataNotAvailable(String message) {
                mview.showErrorMessage(message);
            }
        },movieId);
    }


}
