package com.etxcoding.trendingmovies.Views;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.etxcoding.trendingmovies.Controllers.MoviesController;
import com.etxcoding.trendingmovies.Models.Movie;
import com.etxcoding.trendingmovies.R;

public class MovieDetailActivity extends AppCompatActivity {

    private Movie.MovieItem item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        // Get item detail.
        String id = this.getIntent().getStringExtra(MoviesController.ARG_ITEM_ID);
        this.item = Movie.ListItemsMap.get(id);

        // Set app bar image.
        Glide.with(this)
                .load(item.getPosterPath("w342"))
                // .apply(RequestOptions.circleCropTransform())
                .into((ImageView) findViewById(R.id.app_bar_image));

        ((TextView) findViewById(R.id.tvDetailRating)).setText(item.getRating());

        // Set CollapsingToolbarLayout.
        CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        if (appBarLayout != null) {
            appBarLayout.setTitle(item.getTitle());
        }

        // Set Toolbar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (toolbar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Action button to share item.
        FloatingActionButton fabShare = findViewById(R.id.fabShare);
        fabShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Hi, here's a movie recommendation: " + item.getTitle());
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Synopsis: " + item.overview);
                startActivity(Intent.createChooser(sharingIntent, "Share via..."));
            }
        });

        // Action button to rate item.
        FloatingActionButton fabRating = findViewById(R.id.fabRating);
        fabRating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = getFragmentManager();
                MovieDetailRatingFragment theFragment = new MovieDetailRatingFragment();
                theFragment.show(fm, "MovieRating");
            }
        });
    }

    public void UpdateRating() {
        ((TextView) findViewById(R.id.tvDetailRating)).setText(item.getRating());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            //NavUtils.navigateUpTo(this, new Intent(this, ItemListActivity.class));
            //((MovieListActivity) this.getParent()).raMovies.notifyDataSetChanged();

//            MovieListActivity movieListActivity = (MovieListActivity) this.getParent();
//            movieListActivity.raMovies.setMovies(Movie.ListItems);
//            movieListActivity.raMovies.notifyDataSetChanged();

//            ((MovieListActivity) this.getParent()).raMovies.setMovies(Movie.ListItems);
//            ((MovieListActivity) this.getParent()).raMovies.notifyDataSetChanged();

            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();

        //Refresh your stuff here
        //raMovies.notifyDataSetChanged();
    }

}
