package com.locdevelop.popularmovies.network;

import android.support.v4.app.Fragment;

import com.octo.android.robospice.SpiceManager;


public class RobospiceFragement extends Fragment {
    protected SpiceManager spiceManager = new SpiceManager(RetrofitSpiceService.class);


    @Override
    public void onStart() {
        super.onStart();
        spiceManager.start(getActivity());
    }

    @Override
    public void onStop() {
        spiceManager.shouldStop();
        super.onStop();
    }



    protected SpiceManager getSpiceManager() {
        return spiceManager;
    }
}
