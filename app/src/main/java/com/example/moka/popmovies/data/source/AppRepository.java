/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.moka.popmovies.data.source;

import android.content.Context;
import android.support.annotation.NonNull;


import com.example.moka.popmovies.R;
import com.example.moka.popmovies.UI.main.MoviesFilterType;
import com.example.moka.popmovies.data.Models.Cast;
import com.example.moka.popmovies.data.Models.Movie;
import com.example.moka.popmovies.data.Models.Review;
import com.example.moka.popmovies.data.Models.Trailer;
import com.example.moka.popmovies.utilities.CheckInternetConnection;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Concrete implementation to load tasks from the data sources into a cache.
 * <p>
 * For simplicity, this implements a dumb synchronisation between locally persisted data and data
 * obtained from the server, by using the remote data source only if the local database doesn't
 * exist or is empty.
 */
public class AppRepository implements BaseDataSource.MoviesDataSource,BaseDataSource.FavoriteDataSource {

    private static AppRepository INSTANCE = null;

    private final BaseDataSource.MoviesDataSource mMoviesRemoteDataSource;

    private final BaseDataSource.FavoriteDataSource mFavoritesLocalDataSource;

    /**
     * This variable has package local visibility so it can be accessed from tests.
     */
    //type , List
    Map<MoviesFilterType, List<Movie>> mCachedMovies;
    //movie_id ,
    Map<Integer, List<Trailer>> mCachedMoviesTrailes;
    Map<Integer, List<Review>> mCachedMoviesReviews;
    Map<Integer, List<Cast>> mCachedMoviesCasts;

    /**
     * Marks the cache as invalid, to force an update the next time data is requested. This variable
     * has package local visibility so it can be accessed from tests.
     */
    boolean mCacheIsDirty = false;
    private Context context;
    CheckInternetConnection cic ;

    public void setContext(Context context) {
        this.context = context;
        cic = new CheckInternetConnection(context);

    }
    
    // Prevent direct instantiation.
    private AppRepository(@NonNull BaseDataSource.MoviesDataSource tasksRemoteDataSource,
                          @NonNull BaseDataSource.FavoriteDataSource tasksLocalDataSource) {
        mMoviesRemoteDataSource = checkNotNull(tasksRemoteDataSource);
        mFavoritesLocalDataSource = checkNotNull(tasksLocalDataSource);
    }

    /**
     * Returns the single instance of this class, creating it if necessary.
     *
     * @param tasksRemoteDataSource the backend data source
     * @param tasksLocalDataSource  the device storage data source
     * @return the {@link AppRepository} instance
     */
    public static AppRepository getInstance(@NonNull BaseDataSource.MoviesDataSource tasksRemoteDataSource,
                                            @NonNull BaseDataSource.FavoriteDataSource tasksLocalDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new AppRepository(tasksRemoteDataSource, tasksLocalDataSource);
        }
        return INSTANCE;
    }


    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getAllFavorite(@NonNull final LoadAllFavoriteCallback callback) {
        checkNotNull(callback);
        mFavoritesLocalDataSource.getAllFavorite(new LoadAllFavoriteCallback() {
            @Override
            public void onAllFavoriteLoaded(List<Movie> Favorites) {
                callback.onAllFavoriteLoaded(Favorites);
            }

            @Override
            public void onDataNotAvailable(String table_is_empty) {
                callback.onDataNotAvailable("table is empty");
            }
        });
    }
    @Override
    public void getFavorite(@NonNull int FavoriteId, @NonNull final GetFavoriteCallback callback) {
        mFavoritesLocalDataSource.getFavorite(FavoriteId,new GetFavoriteCallback() {
            @Override
            public void onFavoriteLoaded(Movie Favorite) {
                callback.onFavoriteLoaded(Favorite);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }
    @Override
    public void addFavorite(@NonNull Movie Favorite) {
        mFavoritesLocalDataSource.addFavorite(Favorite);
    }
    @Override
    public void updateFavorite(@NonNull Movie Favorite) {
        mFavoritesLocalDataSource.updateFavorite(Favorite);

    }
    @Override
    public void deleteAllFavorite() {
        mFavoritesLocalDataSource.deleteAllFavorite();

    }
    @Override
    public void deleteFavorite(@NonNull int FavoriteId) {
        mFavoritesLocalDataSource.deleteFavorite(FavoriteId);
    }

    @Override
    public void getpopularMovies(@NonNull final LoadAllMoviesCallback callback) {
        checkNotNull(callback);
        if (mCachedMovies!=null && mCachedMovies.containsKey(MoviesFilterType.popular)  &&
                !mCacheIsDirty) {
            callback.onResponse(new ArrayList<>(mCachedMovies.get(MoviesFilterType.popular)));
            return;
        }
        boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            callback.onDataNotAvailable(context.getString(R.string.no_internet_connection));
            return;
        }
        mMoviesRemoteDataSource.getpopularMovies(new LoadAllMoviesCallback() {
        @Override
        public void onResponse(List<Movie> Movies) {
            // save to cache
            if(mCachedMovies==null)mCachedMovies=new LinkedHashMap<>();
            if(!mCachedMovies.containsKey(MoviesFilterType.popular))
                mCachedMovies.put(MoviesFilterType.popular,Movies);
            mCacheIsDirty=false;
            //
            callback.onResponse(Movies);
        }

        @Override
        public void onDataNotAvailable(String message) {
            callback.onDataNotAvailable(message);
        }
    });}

    @Override
    public void getTopRatedMovies(@NonNull final LoadAllMoviesCallback callback) {
        checkNotNull(callback);
        // Respond immediately with cache if available and not dirty
        if (mCachedMovies!=null &&mCachedMovies.get(MoviesFilterType.top) != null &&
                !mCacheIsDirty) {
            callback.onResponse(new ArrayList<>(mCachedMovies.get(MoviesFilterType.top)));
            return;
        }
        boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            callback.onDataNotAvailable(context.getString(R.string.no_internet_connection));
            return;
        } 
        mMoviesRemoteDataSource.getTopRatedMovies(new LoadAllMoviesCallback() {
            @Override
            public void onResponse(List<Movie> Movies)
            {   // save to cache
                if(mCachedMovies==null)mCachedMovies=new LinkedHashMap<>();
                if(mCachedMovies.get(MoviesFilterType.top)==null)
                    mCachedMovies.put(MoviesFilterType.top,Movies);
                mCacheIsDirty=false;
                //
                callback.onResponse(Movies);
            }

            @Override
            public void onDataNotAvailable(String message) {
                callback.onDataNotAvailable(message);
            }
        });
    }
    @Override
    public void getMovieActors(@NonNull final IResponseCast callback, @NonNull final int movie_id) {
        checkNotNull(callback);
        // Respond immediately with cache if available and not dirty
        if (mCachedMoviesCasts!=null &&mCachedMoviesCasts.containsKey(movie_id)  && !mCacheIsDirty) {
            callback.OnResponseCast(new ArrayList<>(mCachedMoviesCasts.get(movie_id)));
            return;
        }
        boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            callback.onDataNotAvailable(context.getString(R.string.no_internet_connection));
            return;
        }
        mMoviesRemoteDataSource.getMovieActors(new IResponseCast() {


            @Override
            public void OnResponseCast(List<Cast> data) {
                // save to cache
                if(mCachedMoviesCasts==null)mCachedMoviesCasts=new LinkedHashMap<>();
                if(!mCachedMoviesCasts.containsKey(movie_id))mCachedMoviesCasts.put(movie_id,data);
                mCacheIsDirty=false;
                //
                callback.OnResponseCast(data);
            }

            @Override
            public void onDataNotAvailable(String message) {
                callback.onDataNotAvailable(message);
            }
        },movie_id);
    }
    @Override
    public void getMovieTrailer(@NonNull final IResponseTrailer callback, @NonNull final int movie_id) {
        checkNotNull(callback);
        // Respond immediately with cache if available and not dirty
        if (mCachedMoviesTrailes!=null &&mCachedMoviesTrailes.containsKey(movie_id)  && !mCacheIsDirty) {
            callback.OnResponseTrailer(new ArrayList<>(mCachedMoviesTrailes.get(movie_id)));
            return;
        }
        boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            callback.onDataNotAvailable(context.getString(R.string.no_internet_connection));
            return;
        }
        mMoviesRemoteDataSource.getMovieTrailer(new IResponseTrailer() {

            @Override
            public void OnResponseTrailer(List<Trailer> data) {
                // save to cache
                if(mCachedMoviesTrailes==null)mCachedMoviesTrailes=new LinkedHashMap<>();
                if(!mCachedMoviesTrailes.containsKey(movie_id))mCachedMoviesTrailes.put(movie_id,data);
                mCacheIsDirty=false;
                //
                callback.OnResponseTrailer(data);
            }

            @Override
            public void onDataNotAvailable(String message) {
                callback.onDataNotAvailable(message);
            }
        },movie_id);
    }
    @Override
    public void getMovieReview(@NonNull final IResponseReview callback, @NonNull final int movie_id) {
        checkNotNull(callback);
        // Respond immediately with cache if available and not dirty
        if (mCachedMoviesReviews!=null &&mCachedMoviesReviews.containsKey(movie_id)  && !mCacheIsDirty) {
            callback.OnResponseReview(new ArrayList<>(mCachedMoviesReviews.get(movie_id)));
            return;
        }
        boolean Ch = cic.isConnectingToInternet();
        if (!Ch) {
            callback.onDataNotAvailable(context.getString(R.string.no_internet_connection));
            return;
        }
        mMoviesRemoteDataSource.getMovieReview(new IResponseReview() {
            @Override
            public void OnResponseReview(List<Review> data)
            {   // save to cache
                if(mCachedMoviesReviews==null)mCachedMoviesReviews=new LinkedHashMap<>();
                if(!mCachedMoviesReviews.containsKey(movie_id))mCachedMoviesReviews.put(movie_id,data);

                mCacheIsDirty=false;
                //mCachedMoviesReviews.put(String.valueOf(movie_id),data);
                callback.OnResponseReview(data);
            }

            @Override
            public void onDataNotAvailable(String message) {
                callback.onDataNotAvailable(message);
            }
        },movie_id);
    }

    @Override
    public void clearCache() {
        mCacheIsDirty = true;
    }
}
