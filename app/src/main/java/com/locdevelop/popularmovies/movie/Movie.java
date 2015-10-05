package com.locdevelop.popularmovies.movie;


import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Movie implements Serializable {

    public static final String SHARED_PREF_KEY = "fav_movies";

    private String id;
    private String mOriginalTitle;
    private String mPlotSynopsis;
    private double mUserRating;
    private String mReleaseDate;

    private String mImageUrl;

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public double getUserRating() {
        return mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    private Movie(String id, String originalTitle, String plotSynopsis, double userRating, String releaseDate, String imageUrl) {
        this.id = id;
        this.mOriginalTitle = originalTitle;
        this.mPlotSynopsis = plotSynopsis;
        this.mUserRating = userRating;
        this.mReleaseDate = releaseDate;
        this.mImageUrl = imageUrl;
    }

    public static ArrayList<Movie> createMovieListFromJson(String moviesJsonString) throws JSONException {

        JSONObject moviesJson = new JSONObject(moviesJsonString);

        final String MOVIEDB_RESULTS = "results";
        JSONArray moviesArray = moviesJson.getJSONArray(MOVIEDB_RESULTS);

        ArrayList<Movie> movieList = new ArrayList<>();
        for(int movieNumber = 0; movieNumber < moviesArray.length(); ++movieNumber)
        {
            Movie movie = parseJsonMovieObject(moviesArray.getJSONObject(movieNumber));
            movieList.add(movie);
        }


        return movieList;
    }

    private static Movie parseJsonMovieObject(JSONObject movieJsonObject) throws JSONException {

        final String MOVIEDB_ID = "id";
        String id = movieJsonObject.getString(MOVIEDB_ID);

        final String MOVIEDB_ORIGINAL_TITLE = "original_title";
        String originalTitle = movieJsonObject.getString( MOVIEDB_ORIGINAL_TITLE );

        final String MOVIEDB_PLOT_SYNOPSIS = "overview";
        String plotSynopsis = movieJsonObject.getString(MOVIEDB_PLOT_SYNOPSIS);

        final String MOVIEDB_USER_RATING = "vote_average";
        double userRating = movieJsonObject.getDouble(MOVIEDB_USER_RATING);

        final String MOVIEDB_RELEASE_DATE = "release_date";
        String releaseDate = movieJsonObject.getString(MOVIEDB_RELEASE_DATE);

        final String MOVIEDB_POSTER_PATH = "poster_path";
        String posterPath = movieJsonObject.getString(MOVIEDB_POSTER_PATH);

        final String POSTER_BASE_URL = "http://image.tmdb.org/t/p/";
        Uri.Builder posterUrl = Uri.parse(POSTER_BASE_URL).buildUpon();
        String posterSize = "w185";
        posterUrl.appendEncodedPath( posterSize );
        posterUrl.appendEncodedPath(posterPath);

        String imageUrl = posterUrl.toString();

        return new Movie(id, originalTitle, plotSynopsis, userRating, releaseDate, imageUrl);
    }

    @Override
    public String toString() {
        return "\n"
                + "Original title: " + mOriginalTitle
                + "\n" + "Plot synopsis: " + mPlotSynopsis
                + "\n" + "User rating: " + String.valueOf(mUserRating)
                + "\n" + "Release date: " + mReleaseDate
                + "\n" + "Image URL: " + mImageUrl
                + "\n";
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (Double.compare(movie.mUserRating, mUserRating) != 0) return false;
        if (!id.equals(movie.id)) return false;
        if (!mOriginalTitle.equals(movie.mOriginalTitle)) return false;
        if (mPlotSynopsis != null ? !mPlotSynopsis.equals(movie.mPlotSynopsis) : movie.mPlotSynopsis != null)
            return false;
        if (mReleaseDate != null ? !mReleaseDate.equals(movie.mReleaseDate) : movie.mReleaseDate != null)
            return false;
        return !(mImageUrl != null ? !mImageUrl.equals(movie.mImageUrl) : movie.mImageUrl != null);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id.hashCode();
        result = 31 * result + mOriginalTitle.hashCode();
        result = 31 * result + (mPlotSynopsis != null ? mPlotSynopsis.hashCode() : 0);
        temp = Double.doubleToLongBits(mUserRating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (mReleaseDate != null ? mReleaseDate.hashCode() : 0);
        result = 31 * result + (mImageUrl != null ? mImageUrl.hashCode() : 0);
        return result;
    }
}
