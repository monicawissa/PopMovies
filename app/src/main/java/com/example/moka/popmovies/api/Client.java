package com.example.moka.popmovies.api;

import com.example.moka.popmovies.BuildConfig;
import com.example.moka.popmovies.Models.ActorResult;
import com.example.moka.popmovies.Models.MovieResults;
import com.example.moka.popmovies.Models.ReviewResult;
import com.example.moka.popmovies.Models.TrailerResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;
interface IResponse{
    void OnRespponse(Object data);
}
 public class Client extends Retrofitt {
    private service serv;
    private final String language="en-US";
    private  static Client instance;
    private IResponse ref;
    public Client(String path) {
        serv=new Retrofitt().getRetrofit(path,GsonConverterFactory.create())
            .create(service.class);
    }
    public void setResponse(IResponse ref){
        this.ref=ref;
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
                 this.getReviewTrailer(movie_id);
                 break;
             case "Trailer":
                 this.getMovieTrailer(movie_id);
                 break;
             case "Actor":
                 this.getActorsMovies(movie_id);
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
                    ref.OnRespponse((Object) response.body().getResults());
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
                if(ref!=null)
                    ref.OnRespponse(response.body().getTrailers());
            }

            @Override
            public void onFailure(Call<TrailerResult> call, Throwable t) {
                if(ref!=null)
                    ref.OnRespponse(null);
            }
        });

    }
    private void getReviewTrailer(int movie_id){
         serv.getReviewTrailer(movie_id, BuildConfig.the_movie_db_API_token).enqueue(new Callback<ReviewResult>() {
             @Override
             public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                 if(ref!=null)
                     ref.OnRespponse(response.body().getResults());
             }

             @Override
             public void onFailure(Call<ReviewResult> call, Throwable t) {
                 if(ref!=null)
                     ref.OnRespponse(null);
             }
         });
    }
    private void getActorsMovies(int movie_id){
         serv.getActorsMovies(movie_id, BuildConfig.the_movie_db_API_token).enqueue(new Callback<ActorResult>() {
            @Override
            public void onResponse(Call<ActorResult> call, Response<ActorResult> response) {
                if(ref!=null)
                    ref.OnRespponse(response.body().getCast());
            }

            @Override
            public void onFailure(Call<ActorResult> call, Throwable t) {
                if(ref!=null)
                    ref.OnRespponse(null);
            }
        });
    }

}
