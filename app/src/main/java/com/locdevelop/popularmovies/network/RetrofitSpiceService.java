package com.locdevelop.popularmovies.network;

import com.octo.android.robospice.retrofit.RetrofitGsonSpiceService;

public class RetrofitSpiceService extends RetrofitGsonSpiceService {


    @Override
    public void onCreate() {
        super.onCreate();
        addRetrofitInterface(MovieDBService.class);
    }

    @Override
    protected String getServerUrl() {
        return MovieDBService.TRAILER_BASE_URL;
    }

}
