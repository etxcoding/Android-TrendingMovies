package com.example.trendingmovies.Views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.trendingmovies.Adapters.ListRecyclerAdapter;
import com.example.trendingmovies.Models.Movie;
import com.example.trendingmovies.R;
import com.example.trendingmovies.Shared.Services;

import java.util.List;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Obtain items from service.
        Services services = new Services(this);
        services.GetListMovie(new Services.GetListMovieCallback(){
            @Override
            public void onSuccess(Movie.MovieItem[] results) {
                List<Movie.MovieItem> ListMovie = Movie.SetList(results);
                LoadList(ListMovie);
            }
        });
    }

    protected void LoadList(List<Movie.MovieItem> ListMovie) {

        // Declare recycler view and set a layout manager.
        final RecyclerView recyclerList = (RecyclerView) findViewById(R.id.rvList);
//        LinearLayoutManager listLayoutManager = new LinearLayoutManager(this);
//        recyclerList.setLayoutManager(listLayoutManager);

        final ListRecyclerAdapter listRecyclerAdapter = new ListRecyclerAdapter(this, ListMovie);
        recyclerList.setAdapter(listRecyclerAdapter);
    }
}
