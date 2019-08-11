package com.example.trendingmovies.Shared;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.trendingmovies.Models.Movie;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class Services {
    private final Context mContext;
    private final String TheMovieDBApiKey = "1d6df0cf5fc53140ee1d90a925166716";

    public Services(Context mContext) {
        this.mContext = mContext;
    }

    public void GetListMovie(final GetListMovieCallback callback){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(mContext);
        String url ="https://api.themoviedb.org/3/trending/all/day?api_key=" + TheMovieDBApiKey;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        ResponseClass ResponseClassItem = new ObjectMapper().readValue(response, ResponseClass.class);
                        callback.onSuccess(ResponseClassItem.results);
                    } catch(IOException ex) { }
                }
            },
            new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) { }
            });

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    public interface GetListMovieCallback{
        void onSuccess(Movie.MovieItem[] results);
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ResponseClass {
        public int page;
        public Movie.MovieItem[] results;

        // The dummy constructor
        public ResponseClass() {
        }
    }
}


