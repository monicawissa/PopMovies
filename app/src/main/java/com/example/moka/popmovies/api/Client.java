package com.example.moka.popmovies.api;

import com.example.moka.popmovies.BuildConfig;
import com.example.moka.popmovies.Models.ActorResult;
import com.example.moka.popmovies.Models.Cast;
import com.example.moka.popmovies.Models.MovieResults;
import com.example.moka.popmovies.Models.Review;
import com.example.moka.popmovies.Models.ReviewResult;
import com.example.moka.popmovies.Models.Trailer;
import com.example.moka.popmovies.Models.TrailerResult;
import com.example.moka.popmovies.Models.movie;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
interface IResponse{
    void OnRespponse(List<movie> data);
}
interface IResponseTrailer{
    void OnRespponseTrailer(List<Trailer> data);
}
interface IResponseCast{
    void OnRespponseCast(List<Cast> data);
}
interface IResponseReview{
    void OnRespponseReview(List<Review> data);
}
 public class Client extends Retrofitt {
    private service serv;
    private final String language="en-US";
    private  static Client instance;
    private IResponse ref;
    private IResponseCast refCast;
     private IResponseReview refReview;
     private IResponseTrailer refTrailer;

     public Client(String path) {
        serv=new Retrofitt().getRetrofit(path,GsonConverterFactory.create())
            .create(service.class);
    }
    public void setResponse(IResponse ref){
        this.ref=ref;
    }
     public void setResponseCast(IResponseCast ref){
         this.refCast=ref;
     }
     public void setResponseReview(IResponseReview ref){
         this.refReview=ref;
     }
     public void setResponseTrailer(IResponseTrailer ref){
         this.refTrailer=ref;
     }


     public static Client getInstance(String path) {
            instance=new Client(path);
        return instance;
     }

     public  void Perform(String option){
        switch(option) {
            case "top":
                this.getTopRatedMovies();
                break;
            case "popular":
                this.getpopularMovies();
                break;
            default:
                // code block
        }
    }
    public  void Perform(String option, int movie_id){
         switch(option) {

             case "Review":
                 this.getMovieReview(movie_id);
                 break;
             case "Trailer":
                 this.getMovieTrailer(movie_id);
                 break;
             case "Actor":
                 this.getMovieActors(movie_id);
                 break;
             default:
                 // code block
         }
    }


    private void getpopularMovies(){

        serv.getpopularMovies(BuildConfig.the_movie_db_API_token, language, "1").enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {

                if(ref!=null)
                    ref.OnRespponse( response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                if(ref!=null)
                    ref.OnRespponse(null);
            }
        });

    }
    private void getTopRatedMovies(){
        serv.getTopRatedMovies(BuildConfig.the_movie_db_API_token, language, "1").enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, retrofit2.Response<MovieResults> response) {
                if(ref!=null)
                    ref.OnRespponse(response.body().getResults());
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                if(ref!=null)
                    ref.OnRespponse(null);
            }
        });
    }
    private void getMovieTrailer(int movie_id){
        serv.getMovieTrailer(movie_id, BuildConfig.the_movie_db_API_token).enqueue(new Callback<TrailerResult>() {
            @Override
            public void onResponse(Call<TrailerResult> call, Response<TrailerResult> response) {
                if(refTrailer!=null)
                    refTrailer.OnRespponseTrailer(response.body().getTrailers());
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {
                if(refTrailer!=null)
                    refTrailer.OnRespponseTrailer(null);
            }
        });

    }
    private void getMovieReview(int movie_id){
         serv.getMovieReview(movie_id, BuildConfig.the_movie_db_API_token).enqueue(new Callback<ReviewResult>() {
             @Override
             public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                 if(refReview!=null)
                     refReview.OnRespponseReview(response.body().getResults());
             }

             @Override
             public void onFailure(Call<ReviewResult> call, Throwable t) {
                 if(refReview!=null)
                     refReview.OnRespponseReview(null);
             }
         });
    }
    private void getMovieActors(int movie_id){
         serv.getMovieActors(movie_id, BuildConfig.the_movie_db_API_token).enqueue(new Callback<ActorResult>() {
            @Override
            public void onResponse(Call<ActorResult> call, Response<ActorResult> response) {
                if(refCast!=null)
                    refCast.OnRespponseCast(response.body().getCast());
            }

            @Override
            public void onFailure(Call<ActorResult> call, Throwable t) {
                if(refCast!=null)
                    refCast.OnRespponseCast(null);
            }
        });
    }

}
