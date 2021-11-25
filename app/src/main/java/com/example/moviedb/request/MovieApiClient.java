package com.example.moviedb.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.moviedb.AppExecutors;
import com.example.moviedb.models.MovieModel;
import com.example.moviedb.response.MovieSearchResponse;
import com.example.moviedb.utils.Credentials;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class MovieApiClient {

    private MutableLiveData<List<MovieModel>> mMovies;
    private static MovieApiClient instance;

    //making global Runnable
    private RetrieveMoviesRunnable retrieveMoviesRunnable;

    public static MovieApiClient getInstance(){

        if(instance == null){
            instance = new MovieApiClient();
        }
        return instance;
    }

    private MovieApiClient(){

        mMovies = new MutableLiveData<>();
    }

    public LiveData<List<MovieModel>> getMovies(){

        return  mMovies;
    }

    //1. This method we are going to call through the classes
    public void searchMovieApi(String query, int pageNumber){

        if (retrieveMoviesRunnable != null){
            retrieveMoviesRunnable = null;

        }

        retrieveMoviesRunnable = new RetrieveMoviesRunnable(query, pageNumber);

        //final Future myHandler = AppExecutors.getInstance().networkIO().submit(new Runnable() {
        //    @Override
        //    public void run() {
        //        //retrieve data from api
        //    }
        //});

        final Future myHandler = AppExecutors.getInstance().networkIO().submit(retrieveMoviesRunnable);

        AppExecutors.getInstance().networkIO().schedule( new Runnable() {
            @Override
            public void run() {
                //cancelling the retrofit call
                myHandler.cancel(true);
            }
        },4000, TimeUnit.MICROSECONDS);

    }

    //We have two types of queries : the ID & search queries
    private class RetrieveMoviesRunnable implements Runnable {

        private String query;
        private int pageNumber;
        boolean cancelRequest;

        public RetrieveMoviesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            cancelRequest = false;

        }

        @Override
        public void run() {
            //Get the response object
            try{
                Response response = getMovies(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    List<MovieModel> list = new ArrayList<>(((MovieSearchResponse)response.body()).getMovies());
                    if(pageNumber == 1){
                        //Sending data to live data
                        //PostValue: used for background thread
                        //setValue: not for background thread
                        mMovies.postValue(list);
                    }else {
                        List<MovieModel> currentMovies = mMovies.getValue();
                        currentMovies.addAll(list);
                        mMovies.postValue(currentMovies);
                    }


                }else {

                    String error = response.errorBody().string();
                    Log.v("Tag", "Error " + error);
                    mMovies.postValue(null);

                }

            } catch (IOException e) {
                e.printStackTrace();
                mMovies.postValue(null);

            }

        }

        //Search & Query method
        private Call<MovieSearchResponse> getMovies(String query, int pageNumber) {
            return ServiceRequest.getMovieApi().searchMovie(
                    Credentials.API_KEY,
                    query,
                    //pageNumber
                    String.valueOf(pageNumber)
            );

        }

        private void cancelRequest() {

            Log.v("Tag", "Cancelling Search Request");
            cancelRequest = true;
        }
    }

}
