package com.example.trendingmovies.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.trendingmovies.Models.Movie;
import com.example.trendingmovies.R;

import java.util.List;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.ViewHolder> {

    private final Context mContext;
    private final List<Movie.MovieItem> mMovies;
    private final LayoutInflater mLayoutInflater;

    public ListRecyclerAdapter(Context mContext, List<Movie.MovieItem> mMovies) {
        this.mContext = mContext;
        this.mMovies = mMovies;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mLayoutInflater.inflate(R.layout.item_content, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie.MovieItem movie = mMovies.get(i);
        viewHolder.movieTitle.setText(movie.getTitle());
        viewHolder.movieReleaseDate.setText(movie.getReleaseDate());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final TextView movieTitle;
        public final TextView movieReleaseDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieTitle = (TextView)itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView)itemView.findViewById(R.id.movieReleaseDate);
        }
    }
}
