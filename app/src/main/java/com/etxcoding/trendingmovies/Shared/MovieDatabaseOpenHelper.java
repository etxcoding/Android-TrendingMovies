package com.etxcoding.trendingmovies.Shared;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MovieDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TrendingMovies.db";
    public static final int DATABASE_VERSION = 1;

    public MovieDatabaseOpenHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MovieDatabaseContract.MovieEntry.SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
