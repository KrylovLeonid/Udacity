package com.silvershadow.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silvershadow.myapplication.R;
import com.silvershadow.myapplication.ViewModel.SingleMovieViewModel;

public  class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder>{

    private SingleMovieViewModel model;
    private OnTrailerClickListener mListener;

    public interface OnTrailerClickListener{
        void onClick(Uri trailerUri, Context context);
    }


    protected class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView trailerName;

        private TrailersViewHolder(View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.tv_trailer_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mListener.onClick(model.getTrailers().getValue().get(getAdapterPosition()).getTrailerUri(), v.getContext());
        }
    }

    public TrailersAdapter( SingleMovieViewModel model, OnTrailerClickListener listener) {
        super();
        this.model = model;
        mListener = listener;

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
        holder.trailerName.setText(model.getTrailers().getValue().get(holder.getAdapterPosition()).getName());
    }

    @Override
    public int getItemCount() {
        return model.getTrailers().getValue().size() ;
    }
}
