package com.silvershadow.myapplication.Adapters;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.silvershadow.myapplication.ViewModel.AllMoviesViewModel;
import com.silvershadow.myapplication.Entities.Movie;
import com.silvershadow.myapplication.R;
import com.silvershadow.myapplication.SupportContract;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MoviesViewHolder> implements LifecycleOwner {



    private List<Movie> currentMovies;
    private AllMoviesViewModel model;
    private Lifecycle mLifecycle;
    private MovieItemClickListener mListener;

    public interface MovieItemClickListener{
       void onItemClick(Movie movie, Context context);
    }


    public MoviesAdapter(AllMoviesViewModel m, Lifecycle lifecycle, MovieItemClickListener listener){
        model = m;
        mLifecycle = lifecycle;
        mListener = listener;

     }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycle;
    }


    class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView thumbnailIV;
        TextView titleTV;

        private MoviesViewHolder(View itemView) {
            super(itemView);
            thumbnailIV = itemView.findViewById(R.id.thumbnail_iv);
            titleTV = itemView.findViewById(R.id.thumbnail_title_tv);
            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            mListener.onItemClick(currentMovies.get(getAdapterPosition()), v.getContext());
        }
    }


    @Override
    public void onBindViewHolder(@NonNull final MoviesViewHolder holder, final int position) {
        holder.titleTV.setText(currentMovies.get(position).getTitle());
        holder.titleTV.setGravity(TextView.TEXT_ALIGNMENT_GRAVITY);
        Picasso.get().load(SupportContract.getImgURLstr("w200") + currentMovies.get(position).getThumbImg()).into(holder.thumbnailIV);

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
        return currentMovies != null ? currentMovies.size() : 0;

    }

    public void setMoviesToPopular(){
        model.getPopularMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                currentMovies = movies;
                notifyDataSetChanged();
            }
        });
    }
    public void setMoviesToTopRated(){
        model.getTopRatedMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                currentMovies = movies;
                notifyDataSetChanged();
            }
        });
    }
    public void setMoviesToFavorite() {
        model.getFavoriteMoviesDB().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                currentMovies = movies;
                notifyDataSetChanged();
            }
        });
    }

}
