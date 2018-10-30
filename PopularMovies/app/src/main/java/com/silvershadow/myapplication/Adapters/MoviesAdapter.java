package com.silvershadow.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.silvershadow.myapplication.DataLoading.MovieDataHolder;
import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.MovieDetailsActivity;
import com.silvershadow.myapplication.R;
import com.silvershadow.myapplication.SupportContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    public static String MOVIE ="Movie";

    public enum SortType{
        TOP_RATED,
        POPULAR
    }

    private static List<Movie> currentMovies;


    public MoviesAdapter(){
        currentMovies = new ArrayList<>();

    }


    class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnailIV;
        TextView titleTV;

        private MoviesViewHolder(View itemView) {
            super(itemView);
            thumbnailIV = itemView.findViewById(R.id.thumbnail_iv);
            titleTV = itemView.findViewById(R.id.thumbnail_title_tv);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, final int position) {
        holder.titleTV.setText(currentMovies.get(position).getTitle());
        holder.titleTV.setGravity(TextView.TEXT_ALIGNMENT_GRAVITY);
        Picasso.get().load(SupportContract.getImgURLstr("w200") + currentMovies.get(position).getThumbImg()).into(holder.thumbnailIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MovieDetailsActivity.class);
                intent.putExtra(MOVIE, currentMovies.get(holder.getAdapterPosition()));
                context.startActivity(intent);


            }
        });

    }

    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.movie_item,parent,false);

        return new MoviesViewHolder(view);
    }



    @Override
    public int getItemCount() {
        return currentMovies.size();

    }

    public static void setMoviesToPopular(){
        currentMovies = MovieDataHolder.getPopular();




    }
    public static  void setMoviesToTopRated(){
        currentMovies = MovieDataHolder.getTopRated();


    }
}
