package com.etxcoding.trendingmovies.Views;

import android.app.SearchManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.etxcoding.trendingmovies.Adapters.MovieRecyclerAdapter;
import com.etxcoding.trendingmovies.Controllers.MoviesController;
import com.etxcoding.trendingmovies.Models.Movie;
import com.etxcoding.trendingmovies.R;

import java.util.ArrayList;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView rvMovies;
    public MovieRecyclerAdapter raMovies;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        Movie.CreateDatabaseOpenHelper(this);

        Toolbar toolbar = findViewById(R.id.app_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(R.string.toolbar_title);

        // Set recycler view with empty adapter.
        rvMovies = (RecyclerView) findViewById(R.id.rvMovies);
        raMovies = new MovieRecyclerAdapter(this, new ArrayList<Movie.MovieItem>());
        rvMovies.setAdapter(raMovies);

        // Load adapter with web service.
        MoviesController.GetList(this, raMovies);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // Associate searchable configuration with the SearchView.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);

        // Listening to search query text change.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted

                raMovies.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                raMovies.getFilter().filter(query);
                return false;
            }
        });
        return true;
    }

    @Override
    public void onBackPressed() {

        // Close search view on back button pressed.
        if (searchView.isIconified() == false) {
            searchView.setQuery("", false);
            searchView.setIconified(true);
            return;
        }

        super.onBackPressed();
    }

    @Override
    public void onResume()
    {  // After a pause OR at startup
        super.onResume();

        //Refresh your stuff here
        raMovies.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        Movie.CloseDatabaseOpenHelper();
        super.onDestroy();

    }
}
