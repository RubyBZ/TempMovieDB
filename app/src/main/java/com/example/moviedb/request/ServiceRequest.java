package com.example.moviedb.request;

import com.example.moviedb.utils.Credentials;
import com.example.moviedb.utils.MovieApi;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceRequest {

    private static Retrofit.Builder retrofitBuilder =
                new Retrofit.Builder()
                .baseUrl(Credentials.BASE_URL)
                //.baseUrl("https://api.themoviedb.org")
                .addConverterFactory(GsonConverterFactory.create());


    private static Retrofit retrofit = retrofitBuilder.build();

    private static MovieApi movieApi = retrofit.create(MovieApi.class);

    public static MovieApi getMovieApi(){
    //public MovieApi getMovieApi(){
        return movieApi;
    }
}
