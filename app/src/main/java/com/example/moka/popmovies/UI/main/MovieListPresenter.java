package com.example.moka.popmovies.UI.main;

import android.support.annotation.NonNull;
import com.example.moka.popmovies.data.Models.Movie;
import com.example.moka.popmovies.data.source.AppRepository;
import com.example.moka.popmovies.data.source.BaseDataSource;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.Preconditions.checkNotNull;

public class MovieListPresenter implements MovieList_contract.Presenter {
    private final MovieList_contract.View mview;
    private MoviesFilterType mCurrentFiltering=MoviesFilterType.popular;
    private final AppRepository appRepository;
    private boolean mFirstLoad = true;


    public MovieListPresenter(@NonNull MovieList_contract.View view,
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
        execute(forceUpdate || mFirstLoad, true);
        mFirstLoad = false;
    }

    private void execute(boolean forceUpdate, boolean showLoadingUI ) {
        if (showLoadingUI) {
            mview.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            appRepository.clearCache();
        }
        if(mCurrentFiltering==MoviesFilterType.popular)getpopularMovies(true);
        else if(mCurrentFiltering==MoviesFilterType.top)getTopRatedMovies(true);
        else if(mCurrentFiltering==MoviesFilterType.favorite)getAllFavorite(true);
        else if(mCurrentFiltering==MoviesFilterType.deleteAllFavorite)deleteAllFavorite(true);
        else {mview.setLoadingIndicator(false);}
    }

    @Override
    public void getAllFavorite(final Boolean showLoadingUI) {
        appRepository.getAllFavorite(new BaseDataSource.FavoriteDataSource.LoadAllFavoriteCallback() {
            @Override
            public void onAllFavoriteLoaded(List<Movie> Favorites) {
                if (!mview.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mview.setLoadingIndicator(false);
                }
                mview.showMovies(Favorites);
            }

            @Override
            public void onDataNotAvailable(String table_is_empty) {
                if (!mview.isActive()) {
                    return;
                }
                mview.showErrorMessage(table_is_empty);
            }
        });
    }

    @Override
    public void getpopularMovies(final Boolean showLoadingUI) {
        appRepository.getpopularMovies(new BaseDataSource.MoviesDataSource.LoadAllMoviesCallback() {
            @Override
            public void onResponse(List<Movie> Movies) {
                if (!mview.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mview.setLoadingIndicator(false);
                }
                mview.showMovies(Movies);
            }

            @Override
            public void onDataNotAvailable(String message) {
                if (!mview.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mview.setLoadingIndicator(false);
                }
                mview.showErrorMessage(message);
            }
        });
    }

    @Override
    public void getTopRatedMovies(final Boolean showLoadingUI) {
        appRepository.getTopRatedMovies(new BaseDataSource.MoviesDataSource.LoadAllMoviesCallback() {
            @Override
            public void onResponse(List<Movie> Movies) {
                if (!mview.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mview.setLoadingIndicator(false);
                }
                mview.showMovies(Movies);
            }

            @Override
            public void onDataNotAvailable(String message) {
                if (!mview.isActive()) {
                    return;
                }
                if (showLoadingUI) {
                    mview.setLoadingIndicator(false);
                }
                mview.showErrorMessage(message);
            }
        });
    }

    @Override
    public void deleteAllFavorite(Boolean showLoadingUI) {
        appRepository.deleteAllFavorite();
        if (showLoadingUI) {
            mview.setLoadingIndicator(false);
        }
        getAllFavorite(true);
        mview.showSuccessfullyDeleteFavoritesMessage();
    }

    @Override
    public void setFiltering(MoviesFilterType requestType) {
        mCurrentFiltering=requestType;
        execute(false);
    }

    @Override
    public MoviesFilterType getFiltering() {
        return mCurrentFiltering;
    }

}
