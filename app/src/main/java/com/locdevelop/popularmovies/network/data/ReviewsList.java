package com.locdevelop.popularmovies.network.data;

/**
 * Created by Sergei Zarochentsev on 04.10.2015.
 */
public class ReviewsList {
    private String id;
    private String page;

    private Review[] results;


    public String[] getReviews() {
        String[] result = new String[results.length];

        for (int i = 0; i < result.length; i++) {
            result[i] = results[i].toString();
        }
        return result;
    }
}
