package com.silvershadow.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.silvershadow.myapplication.Adapters.MoviesAdapter;
import com.silvershadow.myapplication.DataLoading.MovieDataHolder;
import com.squareup.picasso.Picasso;

public class MovieDetailsActivity extends AppCompatActivity {

    ImageView backgroundIV;
    ImageView smallIV;
    TextView titleTV;
    TextView votesTV;
    TextView averageRatingTV;
    TextView releaseDateTV;
    TextView descriptionTV;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_details_activity);
        Intent intent = getIntent();
        int position = intent.getIntExtra(MoviesAdapter.POSITION ,0);
        MoviesAdapter.SortType type = (MoviesAdapter.SortType) intent.getSerializableExtra(MoviesAdapter.SORT_TYPE);


        connectViews();
        populateViews(position,type);

    }


    private void connectViews(){
         backgroundIV = findViewById(R.id.iv_header_pic);
         smallIV = findViewById(R.id.iv_movie_pic);

         titleTV = findViewById(R.id.tv_movie_title);
         votesTV = findViewById(R.id.tv_movie_votes);
         averageRatingTV = findViewById(R.id.tv_average_rating);
         releaseDateTV = findViewById(R.id.tv_release_date);
         descriptionTV = findViewById(R.id.tv_movie_details);
    }


    private void populateViews(int position, MoviesAdapter.SortType sortType){
        Movie currentMovie = getMovie(position,sortType);
        Picasso.get().load(SupportContract.getImgURLstr("w400")+ currentMovie.getHeaderImg()).into(backgroundIV);
        Picasso.get().load(SupportContract.getImgURLstr("w400") + currentMovie.getThumbImg()).into(smallIV);
        titleTV.setText(currentMovie.getTitle());
        votesTV.setText(currentMovie.getVotes());
        averageRatingTV.setText(currentMovie.getAverageRating());
        releaseDateTV.setText(currentMovie.getReleaseDate());
        descriptionTV.setText(currentMovie.getDescription());
        setTitle(currentMovie.getTitle());

    }

    private Movie getMovie(int position, MoviesAdapter.SortType sortType){
        Movie movie;
        switch(sortType){
            case POPULAR:
                movie = MovieDataHolder.getPopular().get(position);
                break;
            case TOP_RATED:
                movie = MovieDataHolder.getTopRated().get(position);
                break;
                default:
                    movie = null;
        }
        return movie;
    }
}
