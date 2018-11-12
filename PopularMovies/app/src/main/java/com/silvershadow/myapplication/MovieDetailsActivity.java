package com.silvershadow.myapplication;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.silvershadow.myapplication.Adapters.ReviewsAdapter;
import com.silvershadow.myapplication.Adapters.TrailersAdapter;
import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.Entities.Review;
import com.silvershadow.myapplication.Entities.Trailer;
import com.silvershadow.myapplication.ViewModel.SingleMovieViewModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie currentMovie;
    private boolean isFavorite = true;
    private SingleMovieViewModel model;

    ImageView backgroundIV;
    ImageView smallIV;
    TextView titleTV;
    TextView votesTV;
    TextView averageRatingTV;
    TextView releaseDateTV;
    TextView descriptionTV;

    RecyclerView trailersRV;
    RecyclerView reviewsRV;
    TrailersAdapter trailersAdapter;
    ReviewsAdapter reviewsAdapter;

    Button favoriteButton;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        Intent intent = getIntent();
        currentMovie = (Movie)intent.getSerializableExtra(SupportContract.MOVIE_KEY);

        model = ViewModelProviders.of(this).get(SingleMovieViewModel.class);

        model.loadReviews(currentMovie.getId());
        model.loadTrailers(currentMovie.getId());

        model.getTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                trailersAdapter.notifyDataSetChanged();
            }
        });
        model.getReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                reviewsAdapter.notifyDataSetChanged();
            }
        });

        model.getFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                isFavorite = movies != null && movies.contains(currentMovie);
                setFavoriteButtonStyle();
            }
        });

        connectViews();
        populateViews();

        setAdapters();
    }


    private void connectViews(){
         backgroundIV = findViewById(R.id.iv_header_pic);
         smallIV = findViewById(R.id.iv_movie_pic);

         titleTV = findViewById(R.id.tv_movie_title);
         votesTV = findViewById(R.id.tv_movie_votes);
         averageRatingTV = findViewById(R.id.tv_average_rating);
         releaseDateTV = findViewById(R.id.tv_release_date);
         descriptionTV = findViewById(R.id.tv_movie_details);

         trailersRV = findViewById(R.id.rv_trailers);
         reviewsRV = findViewById(R.id.rv_reviews);
         favoriteButton = findViewById(R.id.favorite_button);


    }
    private void populateViews(){
        Picasso.get().load(SupportContract.getImgURLstr("w400")+ currentMovie.getHeaderImg()).into(backgroundIV);
        Picasso.get().load(SupportContract.getImgURLstr("w400") + currentMovie.getThumbImg()).into(smallIV);
        titleTV.setText(currentMovie.getTitle());
        votesTV.setText(currentMovie.getVotes());
        averageRatingTV.setText(currentMovie.getAverageRating());
        releaseDateTV.setText(currentMovie.getReleaseDate());
        descriptionTV.setText(currentMovie.getDescription());
        setTitle(currentMovie.getTitle());

        setFavoriteButton();


    }

    private void setAdapters(){
        LinearLayoutManager trailersLm = new LinearLayoutManager(this);
        trailersRV.setLayoutManager(trailersLm);
        trailersRV.setHasFixedSize(true);
        trailersAdapter = new TrailersAdapter(model, new TrailersAdapter.OnTrailerClickListener() {
            @Override
            public void onClick(Uri trailerUri, Context context) {
                Intent intent = new Intent(Intent.ACTION_VIEW, trailerUri);
                startActivity(intent);
            }
        });
        trailersRV.setAdapter(trailersAdapter);

        LinearLayoutManager reviewsLm = new LinearLayoutManager(this);
        reviewsRV.setLayoutManager(reviewsLm);
        reviewsRV.setHasFixedSize(true);
        reviewsAdapter = new ReviewsAdapter(model);
        reviewsRV.setAdapter(reviewsAdapter);
    }

    private void setFavoriteButton(){
        setFavoriteButtonStyle();
        favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isFavorite){
                    model.removeFavoriteMovie(currentMovie);
                }else{
                    model.addFavoriteMovie(currentMovie);
                }
            }
        });
    }

    private void setFavoriteButtonStyle(){
        if(isFavorite){
            favoriteButton.setText(R.string.remove_from_favorite);
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds (getResources().getDrawable(R.drawable.ic_thumb_down_24dp), null,null,null);
            favoriteButton.setCompoundDrawablePadding(5);
        }else {
            favoriteButton.setText(R.string.add_to_favorite);
            favoriteButton.setCompoundDrawablesWithIntrinsicBounds (getResources().getDrawable(R.drawable.ic_thumb_up_24dp), null,null,null);
            favoriteButton.setCompoundDrawablePadding(5);
        }
    }

}
