package com.locdevelop.popularmovies.network.data;

/**
 * Created by Sergei Zarochentsev on 04.10.2015.
 */
public class Review {
    private String author;
    private String content;
    private String url;

    @Override
    public String toString() {
        return content;
    }
}
