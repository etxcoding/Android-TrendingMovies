package com.etxcoding.trendingmovies.Models;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.RatingBar;

import com.etxcoding.trendingmovies.Controllers.MoviesController;
import com.etxcoding.trendingmovies.Shared.MovieDatabaseContract;
import com.etxcoding.trendingmovies.Shared.MovieDatabaseOpenHelper;
import com.etxcoding.trendingmovies.Views.MovieDetailRatingFragment;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Movie {

    private static MovieDatabaseOpenHelper mDbOpenHelper;

    // Image configuration.
    public static final String mBaseUrl = "https://image.tmdb.org/t/p/";
    public static final String[] mPosterSizes = new String[]{ "w92", "w154", "w185", "w342", "w500", "w780", "original"};

    // Declare list.
    public static List<MovieItem> ListItems = new ArrayList<MovieItem>();
    public static final Map<String, MovieItem> ListItemsMap = new HashMap<String, MovieItem>();

    // Set list from source.
    public static List<MovieItem> SetList(MovieItem[] ItemArray) {

        // Reset list.
        ListItems = new ArrayList<MovieItem>();
        Map<String, Float> ListRating = GetListRating();

        // Add to list.
        for (MovieItem item: ItemArray) {

            item.rating = ListRating.get(item.id) != null ? ListRating.get(item.id) : 0;

            ListItems.add(item);
            ListItemsMap.put(item.id, item);
        }

        // Sort list by popularity field.
        Collections.sort(ListItems, new Comparator<MovieItem>() {
            @Override
            public int compare(MovieItem c1, MovieItem c2) {
            return Double.compare(c2.popularity, c1.popularity);
            }
        });

        return ListItems;
    }

    // Get list of ratings from database.
    public static Map<String, Float> GetListRating() {

        Map<String, Float> ListRating = new HashMap<String, Float>();

        // Get database.
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

        // Create query.
        final String[] columns = {MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_ID, MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_RATING};
//        String selection = MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_ID + " = ?";
//        String[] selectionArgs = { id };

        // Run query.
        final Cursor movieCursor = db.query(MovieDatabaseContract.MovieEntry.TABLE_NAME, columns, null /*selection*/, null /*selectionArgs*/, null, null, null);
        int movieIdPos = movieCursor.getColumnIndex(MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_ID);
        int movieRatingPos = movieCursor.getColumnIndex(MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_RATING);
        while(movieCursor.moveToNext()) {
            String movieId = movieCursor.getString(movieIdPos);
            String movieRating = movieCursor.getString(movieRatingPos);
            ListRating.put(movieId, Float.valueOf(movieRating));
        }
        return ListRating;
    }

    public static void SetRating(String id, float rating) {

        // Create record.
        ContentValues values = new ContentValues();
        values.put(MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_ID, id);
        values.put(MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_RATING, String.valueOf(rating));

        // Get database.
        SQLiteDatabase db = mDbOpenHelper.getWritableDatabase();
        if(MovieExists(id) == false) {

            // Insert new record.
            int newId = (int) db.insert(MovieDatabaseContract.MovieEntry.TABLE_NAME, null, values);
        }
        else {

            // Update existing record.
            String selection = MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_ID + " = ?";
            String[] selectionArgs = { id };
            int oldId = (int) db.update(MovieDatabaseContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
        }

        Movie.ListItemsMap.get(id).rating = rating;
    }

    public static boolean MovieExists(String id) {

        // Get database.
        SQLiteDatabase db = mDbOpenHelper.getReadableDatabase();

        // Create query.
        final String[] columns = {MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_ID, MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_RATING};
        String selection = MovieDatabaseContract.MovieEntry.COLUMN_MOVIE_ID + " = ?";
        String[] selectionArgs = { id };

        // Run query.
        final Cursor movieCursor = db.query(MovieDatabaseContract.MovieEntry.TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if(movieCursor.getCount() > 0)
            return true;

        return false;
    }

    public static void CreateDatabaseOpenHelper(Context mContext) {
        mDbOpenHelper = new MovieDatabaseOpenHelper(mContext);
    }
    public static void CloseDatabaseOpenHelper() {
        mDbOpenHelper.close();
    }

    // This is to serialize the object from response in case of not declaring all fields.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class MovieItem implements Serializable {
        public String id;
        public String title;
        public String original_title;
        public String name;
        public double popularity;
        public String release_date;
        public String poster_path;
        public String overview;
        public boolean adult;

        public float rating;

        // A dummy constructor.
        public MovieItem() {

        }

        public String getTitle() {
            return  this.title != null ?
                    this.title :
                    this.original_title != null ?
                            this.original_title :
                            this.name;
        }
        public String getReleaseDate() {
            return this.release_date;
        }
        public String getReleaseYear() {
            if(this.release_date != null) {
                return this.release_date.substring(0,4);
            }

            return "";
        }
        public String getRating() {
            return this.rating == 0 ? "Not rated yet" : String.valueOf(this.rating);
        }
        public String getPosterPath(String size) {
            return mBaseUrl + size + this.poster_path;
        }
    }
}