package com.etxcoding.trendingmovies.Controllers;

import android.content.Context;

import com.etxcoding.trendingmovies.Adapters.MovieRecyclerAdapter;
import com.etxcoding.trendingmovies.Models.Movie;
import com.etxcoding.trendingmovies.Shared.MovieDatabaseOpenHelper;
import com.etxcoding.trendingmovies.Shared.Services;

public class MoviesController {

    public static final String ARG_ITEM_ID = "item_id";
    private static MovieDatabaseOpenHelper mDbOpenHelper;

    public static void GetList(final Context mContext, final MovieRecyclerAdapter listRecyclerAdapter) {

        // Obtain items from web service.
        Services services = new Services(mContext);
        services.GetListMovie(new Services.GetListMovieCallback() {
            @Override
            public void onSuccess(Movie.MovieItem[] results) {

                // Declare adapter and set it to recycler view.
                listRecyclerAdapter.setMovies(Movie.SetList(results));
                listRecyclerAdapter.notifyDataSetChanged();
            }
        });
    }
}
