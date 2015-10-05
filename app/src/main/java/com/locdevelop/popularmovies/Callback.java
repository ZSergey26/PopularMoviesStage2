package com.locdevelop.popularmovies;

import com.locdevelop.popularmovies.movie.Movie;


public interface Callback {
    void onItemSelected(Movie selectedMovie);
}
