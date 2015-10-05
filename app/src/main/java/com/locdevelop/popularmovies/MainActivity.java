package com.locdevelop.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.locdevelop.popularmovies.fragment.MainActivityFragment;
import com.locdevelop.popularmovies.fragment.MovieDetailFragment;
import com.locdevelop.popularmovies.movie.Movie;


/**
 * App main screen activity
 * @author Sergei Zarochentsev
 */
public class MainActivity extends AppCompatActivity implements Callback {

    private boolean isTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            showFragment(new MainActivityFragment(), R.id.fragment_container);
        }

        if (findViewById(R.id.detail_fragment_container) != null) {
            isTwoPane = true;
            showFragment(new MainActivityFragment(), R.id.detail_fragment_container);
        } else {
            isTwoPane = false;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settingsActivityStart = new Intent(this, SettingsActivity.class);
            startActivity(settingsActivityStart);
            return true;
        }

        if (id == R.id.action_fav) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            MainActivityFragment fragment = (MainActivityFragment)fragmentManager.findFragmentById(R.id.fragment_container);
            fragment.showFavourites();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showFragment(Fragment fragment, int container_ID)
    {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(container_ID, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onItemSelected(Movie selectedMovie) {
        if (isTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putSerializable(getString(R.string.serializable_movie), selectedMovie);
            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.detail_fragment_container, fragment)
                    .commit();

        } else {
            Intent startMovieDetailActivity = new Intent(this, DetailActivity.class);
            startMovieDetailActivity.putExtra( getString(R.string.serializable_movie), selectedMovie);
            startActivity(startMovieDetailActivity);
        }
    }
}


