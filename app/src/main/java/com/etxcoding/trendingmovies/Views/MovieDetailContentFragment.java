package com.etxcoding.trendingmovies.Views;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.etxcoding.trendingmovies.Controllers.MoviesController;
import com.etxcoding.trendingmovies.Models.Movie;
import com.etxcoding.trendingmovies.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MovieDetailContentFragment extends Fragment {

    private Movie.MovieItem item;

    public MovieDetailContentFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_detail_content, container, false);

        // Get item detail.
        String id = this.getActivity().getIntent().getStringExtra(MoviesController.ARG_ITEM_ID);
        this.item = Movie.ListItemsMap.get(id);

        ((TextView) view.findViewById(R.id.textDescription)).setText(item.overview);

        if (item.release_date != null) {
            try {
                SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat format2 = new SimpleDateFormat("dd-MM-yyyy");
                Date date = format1.parse(item.release_date);
                String dateString = format2.format(date);
                ((TextView) view.findViewById(R.id.tvDetailReleaseDate)).setText(dateString);
            }
            catch (ParseException ex) {}
        }
        else {
            ((TextView) view.findViewById(R.id.textView1)).setVisibility(View.INVISIBLE);
            ((TextView) view.findViewById(R.id.tvDetailReleaseDate)).setVisibility(View.INVISIBLE);
        }

        ((TextView) view.findViewById(R.id.tvDetailAdult)).setText(item.adult == true ? "Yes" : "No");
        ((TextView) view.findViewById(R.id.tvDetailPopularity)).setText(String.valueOf(item.popularity));

        return view;
    }
}
