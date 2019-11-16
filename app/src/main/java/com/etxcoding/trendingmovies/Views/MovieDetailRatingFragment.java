package com.etxcoding.trendingmovies.Views;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.etxcoding.trendingmovies.Controllers.MoviesController;
import com.etxcoding.trendingmovies.Models.Movie;
import com.etxcoding.trendingmovies.R;

public class MovieDetailRatingFragment extends DialogFragment implements View.OnClickListener {

    private RatingBar rbMovieRating;
    private Movie.MovieItem item;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail_rating, container, false);

        // Get item detail.
        String id = this.getActivity().getIntent().getStringExtra(MoviesController.ARG_ITEM_ID);
        this.item = Movie.ListItemsMap.get(id);

        TextView tvRatingMovieTitle = view.findViewById(R.id.tvRatingMovieTitle);
        tvRatingMovieTitle.setText(this.item.getTitle());

        rbMovieRating = view.findViewById(R.id.rbMovieRating);
        rbMovieRating.setRating(this.item.rating);

        View btnRatingCancel = view.findViewById(R.id.btnRatingCancel);
        btnRatingCancel.setOnClickListener(this);

        View btnRatingOK = view.findViewById(R.id.btnRatingOK);
        btnRatingOK.setOnClickListener(this);
        btnRatingOK.requestFocus();

        return view;
    }

    @Override
    public void onClick(View v) {

        float rating = rbMovieRating.getRating();

        int viewId = v.getId();
        switch (viewId){
            case R.id.btnRatingCancel:
                dismiss();
                break;
            case R.id.btnRatingOK:
                Movie.SetRating(this.item.id, rating);
                ((MovieDetailActivity)this.getActivity()).UpdateRating();
                dismiss();
                break;
        }
    }
}
