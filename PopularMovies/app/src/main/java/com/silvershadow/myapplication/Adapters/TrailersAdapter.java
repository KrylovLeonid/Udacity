package com.silvershadow.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.R;

public  class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder>{

    private Movie currentMovie;


    protected class TrailersViewHolder extends RecyclerView.ViewHolder {
        TextView reviewName;



        protected TrailersViewHolder(View itemView) {
            super(itemView);
            reviewName = itemView.findViewById(R.id.tv_trailer_name);

        }
    }

    public TrailersAdapter(Movie movie) {
        super();
        currentMovie = movie;

    }

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.trailer_item_view, parent,false);
        return new TrailersViewHolder(view);
        }

    @Override
    public void onBindViewHolder (@NonNull final TrailersViewHolder holder, final int position) {
        holder.reviewName.setText(currentMovie.getTrailers().get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(Intent.ACTION_VIEW, currentMovie.getTrailers().get(holder.getAdapterPosition()).getTrailerUri());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return currentMovie.getTrailers().size();
    }
}
