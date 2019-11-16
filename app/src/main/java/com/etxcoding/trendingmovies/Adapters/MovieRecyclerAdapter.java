package com.etxcoding.trendingmovies.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.etxcoding.trendingmovies.Controllers.MoviesController;
import com.etxcoding.trendingmovies.Models.Movie;
import com.etxcoding.trendingmovies.R;
import com.etxcoding.trendingmovies.Views.MovieDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.ViewHolder> implements Filterable {

    private final Context mContext;
    private List<Movie.MovieItem> mMovies;
    private List<Movie.MovieItem> mMoviesFiltered;
    private final LayoutInflater mLayoutInflater;

    public MovieRecyclerAdapter(Context mContext, List<Movie.MovieItem> mMovies) {
        this.mContext = mContext;
        this.mMovies = mMovies;
        this.mMoviesFiltered = mMovies;
        this.mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setMovies(List<Movie.MovieItem> mMovies) {
        this.mMovies = mMovies;
        this.mMoviesFiltered = mMovies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = mLayoutInflater.inflate(R.layout.item_content, viewGroup, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Movie.MovieItem movie = mMoviesFiltered.get(i);

        Glide.with(mContext)
                .load(movie.getPosterPath("w185"))
                // .apply(RequestOptions.circleCropTransform())
                .into(viewHolder.moviePoster);
        viewHolder.movieTitle.setText(movie.getTitle());
        viewHolder.movieReleaseDate.setText(movie.getReleaseYear());
        viewHolder.movieRating.setText(movie.getRating());
        viewHolder.movieOverview.setText(movie.overview);
        viewHolder.itemView.setTag(movie.id);
    }

    @Override
    public int getItemCount() {
        return mMoviesFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mMoviesFiltered = mMovies;
                } else {
                    List<Movie.MovieItem> filteredList = new ArrayList<>();
                    for (Movie.MovieItem row : mMovies) {
                        if (row.getTitle().toLowerCase().contains(charString.toLowerCase()) || row.getReleaseYear().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    mMoviesFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mMoviesFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mMoviesFiltered = (ArrayList<Movie.MovieItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final ImageView moviePoster;
        public final TextView movieTitle;
        public final TextView movieReleaseDate;
        public final TextView movieRating;
        public final TextView movieOverview;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            moviePoster = (ImageView)itemView.findViewById(R.id.moviePoster);
            movieTitle = (TextView)itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView)itemView.findViewById(R.id.movieReleaseDate);
            movieRating = (TextView)itemView.findViewById(R.id.movieRating);
            movieOverview = (TextView)itemView.findViewById(R.id.movieOverview);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MovieDetailActivity.class);
                    String id = String.valueOf(itemView.getTag());
                    intent.putExtra(MoviesController.ARG_ITEM_ID, id);
                    // intent.putExtra("caller", "MovieListActivity");
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
