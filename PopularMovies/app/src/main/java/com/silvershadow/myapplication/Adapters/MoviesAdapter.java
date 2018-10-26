package com.silvershadow.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silvershadow.myapplication.R;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesViewHolder> {

    public class MoviesViewHolder extends RecyclerView.ViewHolder{
        ImageView thumbnailIV;
        TextView titelTV;
        public MoviesViewHolder(View itemView) {
            super(itemView);
            thumbnailIV = itemView.findViewById(R.id.thumbnail_iv);
            titelTV = itemView.findViewById(R.id.thumbnail_title_tv);
        }

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
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
