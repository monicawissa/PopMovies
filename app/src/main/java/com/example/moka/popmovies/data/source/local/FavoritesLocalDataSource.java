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

package com.example.moka.popmovies.data.source.local;

import android.support.annotation.NonNull;

import com.example.moka.popmovies.data.Models.Movie;
import com.example.moka.popmovies.data.source.BaseDataSource;
import com.example.moka.popmovies.utilities.AppExecutors;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Concrete implementation of a data source as a db.
 */
public class FavoritesLocalDataSource implements BaseDataSource.FavoriteDataSource {

    private static volatile FavoritesLocalDataSource INSTANCE;

    private FavoriteDao mFavoritesDao;

    private AppExecutors mAppExecutors;

    // Prevent direct instantiation.
    private FavoritesLocalDataSource(@NonNull AppExecutors appExecutors,
                                 @NonNull FavoriteDao FavoritesDao) {
        mAppExecutors = appExecutors;
        mFavoritesDao = FavoritesDao;
    }

    public static FavoritesLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
            @NonNull FavoriteDao FavoritesDao) {
        if (INSTANCE == null) {
            synchronized (FavoritesLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoritesLocalDataSource(appExecutors, FavoritesDao);
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Note: {@link LoadAllFavoriteCallback#onDataNotAvailable(String)} is fired if the database doesn't exist
     * or the table is empty.
     */

    @Override
    public void getAllFavorite(@NonNull final LoadAllFavoriteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<Movie> Favorites = mFavoritesDao.getAllFavorite();
                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (Favorites.isEmpty()) {
                            // This will be called if the table is new or just empty.
                            callback.onDataNotAvailable("table is empty");
                            callback.onAllFavoriteLoaded(Favorites);
                        } else {
                            callback.onAllFavoriteLoaded(Favorites);
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }

    /**
     * Note: {@link GetFavoriteCallback#onDataNotAvailable()} is fired if the {@link Movie} isn't
     * found.
     */
    @Override
    public void getFavorite(@NonNull final int FavoriteId, @NonNull final GetFavoriteCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final Movie Favorite = mFavoritesDao.getFavoriteById(FavoriteId);

                mAppExecutors.mainThread().execute(new Runnable() {
                    @Override
                    public void run() {
                        if (Favorite != null) {
                            callback.onFavoriteLoaded(Favorite);
                        } else {
                            callback.onDataNotAvailable();
                        }
                    }
                });
            }
        };

        mAppExecutors.diskIO().execute(runnable);
    }
    @Override
    public void addFavorite(@NonNull final Movie Favorite) {
        checkNotNull(Favorite);
        Runnable saveRunnable = new Runnable() {
            @Override
            public void run() {
                mFavoritesDao.insertFavorite(Favorite);
            }
        };
        mAppExecutors.diskIO().execute(saveRunnable);
    }

    @Override
    public void updateFavorite(@NonNull final Movie Favorite) {
        Runnable completeRunnable = new Runnable() {
            @Override
            public void run() {
                mFavoritesDao.updateFavorite(Favorite);
            }
        };

        mAppExecutors.diskIO().execute(completeRunnable);
    }

    @Override
    public void deleteAllFavorite() {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mFavoritesDao.deletetable();
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

    @Override
    public void deleteFavorite(@NonNull final int FavoriteId) {
        Runnable deleteRunnable = new Runnable() {
            @Override
            public void run() {
                mFavoritesDao.deleteFavoritebyId(FavoriteId);
            }
        };

        mAppExecutors.diskIO().execute(deleteRunnable);
    }

   // @VisibleForTesting
    static void clearInstance() {
        INSTANCE = null;
    }
}
