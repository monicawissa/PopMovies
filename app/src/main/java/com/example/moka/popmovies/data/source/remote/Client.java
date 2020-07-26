package com.example.moka.popmovies.data.source.remote;

import retrofit2.Converter;
import retrofit2.Retrofit;

class Client {
    private Retrofit retrofit;
    public Retrofit getRetrofit(String base_url, Converter.Factory fac) {
        retrofit=new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(fac)
                .build();
        return retrofit;
    }
}
