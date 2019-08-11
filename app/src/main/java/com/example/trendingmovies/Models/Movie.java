package com.example.trendingmovies.Models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Movie {

    // Declare list.
    public static List<MovieItem> ITEMS2 = new ArrayList<MovieItem>();

    // Set list from source.
    public static List<MovieItem> SetList(MovieItem[] ArrayMovies) {

        // Reset list.
        ITEMS2 = new ArrayList<MovieItem>();

        // Set a title correctly.
        for (MovieItem item: ArrayMovies) {
            item.titleShown =
                    item.title != null ?
                            item.title :
                            item.original_title != null ?
                                    item.original_title :
                                    item.name;
            ITEMS2.add(item);
        }

        // Sort list by popularity field.
        Collections.sort(ITEMS2, new Comparator<MovieItem>() {
            @Override
            public int compare(MovieItem c1, MovieItem c2) {
                return Double.compare(c2.popularity, c1.popularity);
            }
        });

        // Pull ratings from SQLite database.
        SetRating();

        return ITEMS2;
    }

    public static void SetRating() {

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

        public String titleShown;
        public int rating;

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
    }
}