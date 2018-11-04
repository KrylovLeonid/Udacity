package com.silvershadow.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.silvershadow.myapplication.Adapters.MoviesAdapter;
import com.silvershadow.myapplication.Adapters.TrailersAdapter;
import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.Entities.Review;
import com.silvershadow.myapplication.Entities.Trailer;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieDetailsActivity extends AppCompatActivity {

    private Movie currentMovie;

    ImageView backgroundIV;
    ImageView smallIV;
    TextView titleTV;
    TextView votesTV;
    TextView averageRatingTV;
    TextView releaseDateTV;
    TextView descriptionTV;

    RecyclerView trailersRV;
    RecyclerView reviewsRV;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        Intent intent = getIntent();
        currentMovie = (Movie) intent.getSerializableExtra(MoviesAdapter.MOVIE);



        connectViews();
        populateViews();

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

    }

    private void setAdapters(){
        LinearLayoutManager lm = new LinearLayoutManager(this);
        trailersRV.setLayoutManager(lm);
        trailersRV.setHasFixedSize(true);
        TrailersAdapter trailersAdapter = new TrailersAdapter(currentMovie);
        trailersRV.setAdapter(trailersAdapter);

    }

}
