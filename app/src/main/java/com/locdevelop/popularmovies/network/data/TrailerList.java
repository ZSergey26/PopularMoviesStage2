package com.locdevelop.popularmovies.network.data;

/**
 * Created by Sergei Zarochentsev on 01.10.2015.
 */
public class TrailerList {
    private String id;
    private Trailer[] results;

    @Override
    public String toString() {

        String result = "";

        for (Trailer trailer : results) {
            result += trailer.toString() + "\n";
        }

        return result;
    }

    public String[] getTrailerLinks() {
        String[] trailersLinks  = new String[results.length];

        for (int i = 0; i < trailersLinks.length; i++) {
            trailersLinks[i] = results[i].toString();
        }

        return trailersLinks;
    }
}
