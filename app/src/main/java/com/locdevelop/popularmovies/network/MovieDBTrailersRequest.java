package com.locdevelop.popularmovies.network;

import com.locdevelop.popularmovies.network.data.TrailerList;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import roboguice.util.temp.Ln;


public class MovieDBTrailersRequest extends RetrofitSpiceRequest<TrailerList, MovieDBService> {

    private String id;
    private String key;

    public MovieDBTrailersRequest(String id, String key) {
        super(TrailerList.class, MovieDBService.class);
        this.id = id;
        this.key = key;
    }

    @Override
    public TrailerList loadDataFromNetwork() throws Exception {
        Ln.d("Call web service ");
        return getService().trailerList(id, key);
    }
}
