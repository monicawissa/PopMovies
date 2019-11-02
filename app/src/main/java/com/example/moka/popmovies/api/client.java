package com.example.moka.popmovies.api;

import com.google.gson.Gson;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class client {
    Retrofit retrofit=null;

    public Retrofit getRetrofit() {
        if(retrofit==null){
            //https://api.themoviedb.org/3/movie/popular?api_key=ab1c959cae25c6b6b98f2416bf143f2a&language=en-US&page=1
            String base_url = "https://api.themoviedb.org/3/";
            retrofit = new Retrofit.Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
