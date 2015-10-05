package com.locdevelop.popularmovies.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.locdevelop.popularmovies.movie.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Image adapter for grid view
 * Based on code from http://developer.android.com/guide/topics/ui/layout/gridview.html
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Movie> movieList;

    public ImageAdapter(Context c, ArrayList<Movie> movieList) {
        mContext = c;
        this.movieList = movieList;
    }

    public int getCount() {
        return movieList.size();
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }


    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null)
        {
            imageView = new ImageView(mContext);
           //imageView.setLayoutParams(new GridView.LayoutParams(300, 500));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else
        {
            imageView = (ImageView) convertView;
        }

        String url = getItem(position).getImageUrl();
        Picasso.with(mContext).load(url).into(imageView);
        return imageView;
    }
}