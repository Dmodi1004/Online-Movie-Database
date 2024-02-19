package com.example.tmdbclient.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tmdbclient.R;
import com.example.tmdbclient.databinding.MovieListItemBinding;
import com.example.tmdbclient.model.Movie;
import com.example.tmdbclient.view.MovieActivity;

import org.jetbrains.annotations.NotNull;


public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {

    private final Context context;

    public MovieAdapter(Context context) {
        super(Movie.CALLBACK);
        this.context = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieListItemBinding movieListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.movie_list_item, parent, false);

        return new MovieViewHolder(movieListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {

        Movie movie = getItem(position);
        holder.movieListItemBinding.setMovie(movie);


    }

    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private final MovieListItemBinding movieListItemBinding;

        public MovieViewHolder(@NotNull MovieListItemBinding movieListItemBinding) {
            super(movieListItemBinding.getRoot());
            this.movieListItemBinding = movieListItemBinding;

            movieListItemBinding.getRoot().setOnClickListener(view -> {

                int position = getAdapterPosition();

                if (position != RecyclerView.NO_POSITION) {

                    Movie selctedMovie = getItem(position);

                    Intent intent = new Intent(context, MovieActivity.class);
                    intent.putExtra("movie", selctedMovie);
                    context.startActivity(intent);


                }


            });


        }
    }
}
