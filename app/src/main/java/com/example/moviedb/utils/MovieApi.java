package com.example.moviedb.utils;

import com.example.moviedb.models.MovieModel;
import com.example.moviedb.response.MovieSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {

    // 1. Search for movies- https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
    @GET("/3/search/movie")
    Call<MovieSearchResponse> searchMovie(
            @Query("api_key") String key,
            @Query("query") String query,
            @Query("page") String page
    );

    // 1. Search for movie id = 550 - https://api.themoviedb.org/3/movie/550?api_key={api_key}
    @GET("/3/movie/{movie_id}")
    Call<MovieModel> getMovie(
            @Path("movie_id") int movie_id,
            @Query("api_key") String api_key
    );
}
