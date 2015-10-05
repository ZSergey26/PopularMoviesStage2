package com.locdevelop.popularmovies.network;


import com.locdevelop.popularmovies.network.data.ReviewsList;
import com.locdevelop.popularmovies.network.data.TrailerList;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Request to MovieDB with Retrofit lib
 */

public interface MovieDBService {
    @GET("/3/movie/{id}/videos")
    TrailerList trailerList(@Path("id") String id, @Query("api_key") String key);

    @GET("/3/movie/{id}/reviews")
    ReviewsList reviewsList(@Path("id") String id, @Query("api_key") String key);

    String TRAILER_BASE_URL = "http://api.themoviedb.org";
}
