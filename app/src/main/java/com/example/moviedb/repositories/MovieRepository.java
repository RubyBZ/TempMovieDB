package com.example.moviedb.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.models.MovieModel;
import com.example.moviedb.request.MovieApiClient;

import java.util.List;

public class MovieRepository {

    private static MovieRepository instance;

    private MovieApiClient movieApiClient;

    public static MovieRepository getInstance(){

        if(instance == null){

            instance = new MovieRepository();
        }

        return instance;
    }

    private MovieRepository() {

        movieApiClient = MovieApiClient.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){
        return movieApiClient.getMovies();

    }

    //2. Calling method in repository
    public void searchMovieApi(String query, int pageNumber){
        movieApiClient.searchMovieApi(query,pageNumber);

    }

 }
