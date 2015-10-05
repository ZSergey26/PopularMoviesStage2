package com.locdevelop.popularmovies.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.locdevelop.popularmovies.R;
import com.locdevelop.popularmovies.movie.Movie;
import com.locdevelop.popularmovies.network.MovieDBReviewsRequest;
import com.locdevelop.popularmovies.network.MovieDBTrailersRequest;
import com.locdevelop.popularmovies.network.RobospiceFragement;
import com.locdevelop.popularmovies.network.data.ReviewsList;
import com.locdevelop.popularmovies.network.data.TrailerList;
import com.octo.android.robospice.persistence.DurationInMillis;
import com.octo.android.robospice.persistence.exception.SpiceException;
import com.octo.android.robospice.request.listener.RequestListener;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * I'm using Robospice to network requests
 */
public class MovieDetailFragment extends RobospiceFragement {

        private MovieDBTrailersRequest trailersRequest;
        private MovieDBReviewsRequest reviewsRequest;


        public MovieDetailFragment() {
        }

        // I'm using butterknife by Udacity reviewer's advice :)
        @Bind(R.id.trailersListView)
        ListView trailersListView;

        @Bind(R.id.reviewsListView)
        ListView reviewsListView;

        @OnItemClick(R.id.trailersListView)
        void onItemClick(int position) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailersAdapter.getItem(position))));
        }

        @Bind(R.id.favButton)
        ImageButton favButton;


        /**
         * Add movie to favourites
         * I saved data in SharedPreferences because work with ContentProvider is too uncomfortable and requires many lines of code.
         * I wonder if there is a solution that makes possible to save objects in SQLite database with minimum efforts and lines of code?
         */
        @OnClick(R.id.favButton)
        void addToFavorites() {
            if (extraMovie == null) {
                return;
            }

            if (favoriteMovies.contains(extraMovie)) {
                Toast.makeText(getActivity(), "Movie has already been added to favourites", Toast.LENGTH_SHORT).show();
                return;
            }
            favoriteMovies.add(extraMovie);

            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gson = new Gson();
            String json = gson.toJson(favoriteMovies);
            prefsEditor.putString(Movie.SHARED_PREF_KEY, json);
            prefsEditor.commit();

            Toast.makeText(getActivity(), "Movie has been added to favourites", Toast.LENGTH_SHORT).show();
        }

        Set<Movie> favoriteMovies;
        private Movie extraMovie;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

            SharedPreferences appSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            Gson gson = new Gson();
            String json = appSharedPrefs.getString(Movie.SHARED_PREF_KEY,"");
            Type type = new TypeToken<Set<Movie>>(){}.getType();
            favoriteMovies = gson.fromJson(json, type);

            if (favoriteMovies == null) {
                favoriteMovies = new HashSet<>();
            }


            Bundle arguments = getArguments();
            if (arguments != null) {

                extraMovie = (Movie) arguments.getSerializable(getString(R.string.serializable_movie));

                TextView title = (TextView) rootView.findViewById(R.id.movie_title);
                title.setText(extraMovie.getOriginalTitle());

                TextView releaseDate = (TextView) rootView.findViewById(R.id.release_date);
                releaseDate.setText(extraMovie.getReleaseDate().substring(0, 4)); // only year

                TextView userRating = (TextView) rootView.findViewById(R.id.rating);
                userRating.setText(extraMovie.getUserRating() + "/10");

                TextView plotSynopsis = (TextView) rootView.findViewById(R.id.plot_synopsis);
                plotSynopsis.setText(extraMovie.getPlotSynopsis());

                ImageView poster = (ImageView) rootView.findViewById(R.id.poster);
                Picasso.with(getActivity())
                        .load(extraMovie.getImageUrl())
                        .placeholder(R.drawable.poster_stub)
                        .error(R.drawable.poster_stub)
                        .into(poster);


                trailersRequest = new MovieDBTrailersRequest(extraMovie.getId(), getString(R.string.api_key));
                reviewsRequest = new MovieDBReviewsRequest(extraMovie.getId(), getString(R.string.api_key));

            }

            ButterKnife.bind(this, rootView);
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            ButterKnife.unbind(this);
        }

        @Override
        public void onStart() {
            super.onStart();
            getSpiceManager().execute(trailersRequest, "movieDB", DurationInMillis.ONE_MINUTE, new TrailersListRequestListener());
            getSpiceManager().execute(reviewsRequest, "movieDB", DurationInMillis.ONE_MINUTE, new ReviewsListRequestListener());
        }

        @Override
        public void onStop() {
            super.onStop();
        }

        public final class TrailersListRequestListener implements RequestListener<TrailerList> {

            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestSuccess(final TrailerList trailerList) {
                updateTrailersData(trailerList);
            }
        }

        private class ReviewsListRequestListener implements RequestListener<ReviewsList> {
            @Override
            public void onRequestFailure(SpiceException spiceException) {
                Toast.makeText(getActivity(), "failure", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRequestSuccess(ReviewsList reviewsList) {
                updateReviewsData(reviewsList);
            }
        }


        private ArrayAdapter<String> trailersAdapter;
        private void updateTrailersData(TrailerList trailers) {
            trailersAdapter = new ArrayAdapter<>(
                    getActivity(),
                    R.layout.trailer_element,
                    R.id.trailer_link_textview,
                    trailers.getTrailerLinks()
            );

            trailersListView.setAdapter(trailersAdapter);
        }

        private ArrayAdapter<String> reviewsAdapter;
        private void updateReviewsData(ReviewsList reviews) {
            reviewsAdapter = new ArrayAdapter<>(
                    getActivity(),
                    R.layout.review_element,
                    R.id.review_textview,
                    reviews.getReviews()
            );

            reviewsListView.setAdapter(reviewsAdapter);
        }

    }