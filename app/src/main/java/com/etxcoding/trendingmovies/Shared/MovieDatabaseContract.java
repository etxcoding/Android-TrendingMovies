package com.etxcoding.trendingmovies.Shared;

import android.provider.BaseColumns;

public final class MovieDatabaseContract {
    private MovieDatabaseContract() {}

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_MOVIE_ID = "movie_id";
        // public static final String COLUMN_MOVIE_TITLE = "movie_title";
        public static final String COLUMN_MOVIE_RATING = "movie_title";

        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY, " +
                        COLUMN_MOVIE_ID + " TEXT UNIQUE NOT NULL , " +
                        // COLUMN_MOVIE_TITLE + "TEXT NOT NULL, " +
                        COLUMN_MOVIE_RATING + ")";
    }
}
