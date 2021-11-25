package com.example.moviedb.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviedb.models.MovieModel;
import com.example.moviedb.repositories.MovieRepository;

import java.util.List;

public class MovieListViewModel extends ViewModel {

    //private MutableLiveData<List<MovieModel>> mMovies = new MutableLiveData<>();

    //private MovieRepository movieRepository;
    //******** i changed the code to static MovieRepository
    private static MovieRepository movieRepository;

    public MovieListViewModel() {
        //*******  I add this code becose of crush because ctor was empth in original code
        //******** movieRepository.getInstance();

         movieRepository.getInstance();
    }

    public LiveData<List<MovieModel>> getMovies(){

        return movieRepository.getMovies();
    }

    //3. Calling method in view-model
    public void searchMovieApi(String query,int pageNumber){
        movieRepository.searchMovieApi(query,pageNumber);
    }

}
