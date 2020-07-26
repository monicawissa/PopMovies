package com.example.moka.popmovies.data.source.remote;

import android.support.annotation.NonNull;

import com.example.moka.popmovies.BuildConfig;
import com.example.moka.popmovies.data.Models.ActorResult;
import com.example.moka.popmovies.data.Models.MovieResults;
import com.example.moka.popmovies.data.Models.ReviewResult;
import com.example.moka.popmovies.data.Models.TrailerResult;
import com.example.moka.popmovies.data.source.BaseDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;

 public class MoviesRemoteDataSource extends Client implements BaseDataSource.MoviesDataSource {
    private service serv;
    private final String language="en-US";
     private static MoviesRemoteDataSource INSTANCE;
     private static final int SERVICE_LATENCY_IN_MILLIS = 5000;
     public MoviesRemoteDataSource(String path) {
        serv=new Client().getRetrofit(path,GsonConverterFactory.create())
            .create(service.class);
    }
     public static MoviesRemoteDataSource getInstance(String path) {
         if (INSTANCE == null) {
             INSTANCE=new MoviesRemoteDataSource(path);
         }
         return INSTANCE;
     }
//     public  void Perform(MoviesFilterType option){
//        if(option== MoviesFilterType.top){
//                this.getTopRatedMovies();}
//        else if(option==MoviesFilterType.popular) {
//            this.getpopularMovies();
//        }
//
//    }
//    public  void Perform(MovieDetailsType option, int movie_id){
//        if(option== MovieDetailsType.Review){
//            this.getMovieReview(movie_id);
//        }
//        else if(option== MovieDetailsType.Trailer){
//            this.getMovieTrailer(movie_id);
//        }
//        else if(option== MovieDetailsType.Actor){
//            this.getMovieActors(movie_id);
//        }
//    }
//

     @Override
     public void getpopularMovies(@NonNull final LoadAllMoviesCallback callback) {
         serv.getpopularMovies(BuildConfig.the_movie_db_API_token, language,
                 "1").enqueue(new Callback<MovieResults>() {
             @Override
             public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {
                 if (response.body().getResults().isEmpty()) {
                     // This will be called if the table is new or just empty.
                     callback.onDataNotAvailable("Popular movies is empty");
                 }
                 else callback.onResponse( response.body().getResults());
             }

             @Override
             public void onFailure(Call<MovieResults> call, Throwable t) {
                callback.onDataNotAvailable(t.getMessage());
             }
         });

     }

     @Override
     public void getTopRatedMovies(@NonNull final LoadAllMoviesCallback callback) {
         serv.getTopRatedMovies(BuildConfig.the_movie_db_API_token, language,
                 "1").enqueue(new Callback<MovieResults>() {
             @Override
             public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {
                 if (response.body().getResults().isEmpty()) {
                     // This will be called if the table is new or just empty.
                     callback.onDataNotAvailable("Top movies is empty");
                 }
                 else callback.onResponse(response.body().getResults());
             }

             @Override
             public void onFailure(Call<MovieResults> call, Throwable t) {
                 callback.onDataNotAvailable(t.getMessage());
             }
         });

     }

     @Override
     public void getMovieActors(@NonNull final IResponseCast callback, @NonNull int movie_id) {
         serv.getMovieActors(movie_id, BuildConfig.the_movie_db_API_token).
                 enqueue(new Callback<ActorResult>() {
             @Override
             public void onResponse(Call<ActorResult> call, Response<ActorResult> response) {
                 if (response.body().getCast().isEmpty()) {
                     // This will be called if the table is new or just empty.
                     callback.onDataNotAvailable("Actors movie is empty");
                 }
                 else callback.OnResponseCast(response.body().getCast());
             }

             @Override
             public void onFailure(Call<ActorResult> call, Throwable t) {
                 callback.onDataNotAvailable(t.getMessage());
             }
         });

     }

     @Override
     public void getMovieTrailer(@NonNull final IResponseTrailer callback, @NonNull int movie_id) {
         serv.getMovieTrailer(movie_id, BuildConfig.the_movie_db_API_token)
                 .enqueue(new Callback<TrailerResult>() {
             @Override
             public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                 if (response.body().getTrailers().isEmpty()) {
                     // This will be called if the table is new or just empty.
                     callback.onDataNotAvailable("Trailer movie is empty");
                 }
                 else callback.OnResponseTrailer(response.body().getTrailers());
             }

             @Override
             public void onFailure(Call<TrailerResult> call, Throwable t) {
                 callback.onDataNotAvailable(t.getMessage());

             }
         });

     }

     @Override
     public void getMovieReview(@NonNull final IResponseReview callback, @NonNull int movie_id) {
         serv.getMovieReview(movie_id, BuildConfig.the_movie_db_API_token)
                 .enqueue(new Callback<ReviewResult>() {
             @Override
             public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                 if (response.body().getResults().isEmpty()) {
                     // This will be called if the table is new or just empty.
                     callback.onDataNotAvailable("Review movie is empty");
                 }
                 else  callback.OnResponseReview(response.body().getResults());
             }

             @Override
             public void onFailure(Call<ReviewResult> call, Throwable t) {
                 callback.onDataNotAvailable(t.getMessage());
             }
         });
     }

     @Override
     public void clearCache() {
     }
 }
