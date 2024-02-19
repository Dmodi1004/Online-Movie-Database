package com.example.tmdbclient.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.tmdbclient.R;
import com.example.tmdbclient.service.MovieDataService;
import com.example.tmdbclient.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDataSource extends PageKeyedDataSource<Long, Movie> {

    private MovieDataService movieDataService;
    private final Application application;

    public MovieDataSource(MovieDataService movieDataService, Application application) {
        this.movieDataService = movieDataService;
        this.application = application;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Long> loadInitialParams, @NonNull LoadInitialCallback<Long, Movie> callback) {

        movieDataService = RetrofitInstance.getService();
        Call<MovieDBResponse> call = movieDataService.getPopularMoviesWithPaging(application.getApplicationContext().getString(R.string.api_key), 1);
        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieDBResponse> call, @NonNull Response<MovieDBResponse> response) {
                MovieDBResponse movieDBResponse = response.body();
                ArrayList<Movie> movies = new ArrayList<>();

                if (movieDBResponse != null && movieDBResponse.getMovies() != null) {
                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    callback.onResult(movies, null, (long) 2);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDBResponse> call, @NonNull Throwable t) {

            }
        });

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Long> loadParams, @NonNull LoadCallback<Long, Movie> loadCallback) {

    }

    @Override
    public void loadAfter(@NonNull LoadParams<Long> loadParams, @NonNull final LoadCallback<Long, Movie> callback) {

        movieDataService = RetrofitInstance.getService();
        Call<MovieDBResponse> call = movieDataService.getPopularMoviesWithPaging(application.getApplicationContext().getString(R.string.api_key), loadParams.key);
        call.enqueue(new Callback<MovieDBResponse>() {
            @Override
            public void onResponse(@NonNull Call<MovieDBResponse> call, @NonNull Response<MovieDBResponse> response) {
                MovieDBResponse movieDBResponse = response.body();
                ArrayList<Movie> movies = new ArrayList<>();

                if (movieDBResponse != null && movieDBResponse.getMovies() != null) {
                    movies = (ArrayList<Movie>) movieDBResponse.getMovies();
                    callback.onResult(movies, loadParams.key + 1);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MovieDBResponse> call, @NonNull Throwable t) {

            }
        });

    }


}
