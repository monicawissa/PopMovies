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


import android.support.annotation.NonNull;

import com.example.moka.popmovies.data.Models.Cast;
import com.example.moka.popmovies.data.Models.Movie;
import com.example.moka.popmovies.data.Models.Review;
import com.example.moka.popmovies.data.Models.Trailer;

import java.util.List;

/**
 * Main entry point for accessing Movies data.
 * <p>
 * For simplicity, only getMovies() and getMovie() have callbacks. Consider adding callbacks to other
 * methods to inform the user of network/database errors or successful operations.
 * For example, when a new Movie is created, it's synchronously stored in cache but usually every
 * operation on database or network should be executed in a different thread.
 */
public interface BaseDataSource {
    interface FavoriteDataSource {

        interface LoadAllFavoriteCallback {

            void onAllFavoriteLoaded(List<Movie> Favorites);

            void onDataNotAvailable(String table_is_empty);
        }

        interface GetFavoriteCallback {

            void onFavoriteLoaded(Movie Favorite);

            void onDataNotAvailable();
        }

        void getAllFavorite(@NonNull final LoadAllFavoriteCallback callback);

        void getFavorite(@NonNull final int FavoriteId, @NonNull final GetFavoriteCallback callback);

        void addFavorite(@NonNull final Movie Favorite);

        void updateFavorite(@NonNull final Movie Favorite);

        void deleteAllFavorite();

        void deleteFavorite(@NonNull int FavoriteId);
    }
    interface MoviesDataSource {

        interface LoadAllMoviesCallback {
            void onResponse(List<Movie> Movies);
            void onDataNotAvailable(String message);
        }
        interface IResponseTrailer{
            void OnResponseTrailer(List<Trailer> data);
            void onDataNotAvailable(String message);
        }
        interface IResponseCast{
            void OnResponseCast(List<Cast> data);
            void onDataNotAvailable(String message);
        }
        interface IResponseReview{
            void OnResponseReview(List<Review> data);
            void onDataNotAvailable(String message);
        }

        void getpopularMovies(@NonNull LoadAllMoviesCallback callback);
        void getTopRatedMovies(@NonNull LoadAllMoviesCallback callback);
        void getMovieActors(@NonNull IResponseCast callback,@NonNull int movie_id);
        void getMovieTrailer(@NonNull IResponseTrailer callback,@NonNull int movie_id);
        void getMovieReview(@NonNull IResponseReview callback,@NonNull int movie_id);
        void clearCache();
    }
}