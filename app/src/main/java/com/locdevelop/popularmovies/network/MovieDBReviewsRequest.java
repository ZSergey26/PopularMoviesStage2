package com.locdevelop.popularmovies.network;

import com.locdevelop.popularmovies.network.data.ReviewsList;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import roboguice.util.temp.Ln;


public class MovieDBReviewsRequest extends RetrofitSpiceRequest<ReviewsList, MovieDBService> {
    private String id;
    private String key;

    public MovieDBReviewsRequest(String id, String key) {
        super(ReviewsList.class, MovieDBService.class);
        this.id = id;
        this.key = key;
    }


    @Override
    public ReviewsList loadDataFromNetwork() throws Exception {
        Ln.d("Call web service ");
        return getService().reviewsList(id, key);
    }
}
