package com.example.moviedb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.moviedb.models.MovieModel;
import com.example.moviedb.request.ServiceRequest;
import com.example.moviedb.response.MovieSearchResponse;
import com.example.moviedb.utils.Credentials;
import com.example.moviedb.utils.MovieApi;
import com.example.moviedb.viewmodels.MovieListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

// 1. Search -             https://api.themoviedb.org/3/search/movie?api_key={api_key}&query=Jack+Reacher
//                         return field = results - which is array contains movie list object
// 2. Query for details -  https://api.themoviedb.org/3/movie/343611?api_key={api_key}
//                         return all movie details
// 3. Append to response - https://api.themoviedb.org/3/movie/343611?api_key={api_key}&append_to_response=videos

//****** Before we run we need to add the Network security config

public class MovieListActivity extends AppCompatActivity {

    Button btn;
    private MovieListViewModel movieListViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.button);


        movieListViewModel = new ViewModelProvider(this).get(MovieListViewModel.class);

        //Calling the observers
        ObserveAnyChange();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovieApi("fast",1);
            }
        });


    }

    private void ObserveAnyChange(){

        movieListViewModel.getMovies().observe(this, new Observer<List<MovieModel>>() {
            @Override
            public void onChanged(List<MovieModel> movieModels) {
                //Observing for any data change
                if(movieModels != null){
                    for(MovieModel movieModel: movieModels){
                        Log.v("Tag", "onChanged: " + movieModel.getTitle());
                    }
                }
            }
        });
    }

    //4. Calling method in main activity
    private void searchMovieApi(String query, int pageNumber){
        movieListViewModel.searchMovieApi(query,pageNumber);

    }

    /*
    private void GetRetrofitResponse() {
        MovieApi movieApi = ServiceRequest.getMovieApi();

        Call<MovieSearchResponse> responseCall = movieApi
                .searchMovie(
                        Credentials.API_KEY,
                        "Jack Reacher",
                        1);

        responseCall.enqueue(new Callback<MovieSearchResponse>() {
            @Override
            public void onResponse(Call<MovieSearchResponse> call, Response<MovieSearchResponse> response) {

                if (response.code() == 200) {

                    //MovieModel movie = response.body();

                    //Log.v("Tag", "The response" + movie.getTitle());

                    List<MovieModel> movies = new ArrayList<>(response.body().getMovies());

                    //**** complete the code
                    //**** complete the code
                    //**** complete the code
                    //**** complete the code

                } else {
                    try {
                        Log.v("Tag", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieSearchResponse> call, Throwable t) {

            }
        });

    }

    */

        /*

    private void GetRetrofitResponseAccordingToID() {

        MovieApi movieApi = ServiceRequest.getMovieApi();

        Call<MovieModel> responseCall = movieApi
                .getMovie(
                        550,
                         Credentials.API_KEY);

        responseCall.enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {

                if (response.code() == 200) {

                    MovieModel movie = response.body();

                    Log.v("Tag", "The response" + movie.getTitle());

                } else {
                    try {
                        Log.v("Tag", "Error" + response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {

            }
        });
    }

         */
}