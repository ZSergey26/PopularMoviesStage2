package com.locdevelop.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.locdevelop.popularmovies.fragment.MovieDetailFragment;
import com.locdevelop.popularmovies.movie.Movie;


public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Intent onStartDetailActivity = getIntent();
            Movie extraMovie = (Movie) onStartDetailActivity.getSerializableExtra(getString(R.string.serializable_movie));

            Bundle arguments = new Bundle();
            arguments.putSerializable(getString(R.string.serializable_movie), extraMovie);

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, fragment)
                    .commit();
        }


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }




}