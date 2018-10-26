package com.silvershadow.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.silvershadow.myapplication.DataLoading.MovieDataHolder;
import com.silvershadow.myapplication.Movie;
import com.silvershadow.myapplication.R;
import com.silvershadow.myapplication.SupportContract;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> {

    private List<Movie> currentMovies = new ArrayList<>();

    public MoviesAdapter(){
        setMoviesToPopular();
    }


    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnailIV;
        TextView titleTV;

        public MoviesViewHolder(View itemView) {
            super(itemView);
            thumbnailIV = itemView.findViewById(R.id.thumbnail_iv);
            titleTV = itemView.findViewById(R.id.thumbnail_title_tv);
        }

    }


    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        holder.titleTV.setText(currentMovies.get(position).getName());
        Picasso.get().load(SupportContract.getImgURLstr("w200")).into(holder.thumbnailIV);
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
        return 0;
    }

    public void setMoviesToPopular(){
        currentMovies = MovieDataHolder.getPopular();

    }
    public void setMoviesToTopRated(){
        currentMovies = MovieDataHolder.getTopRated();

    }
}
