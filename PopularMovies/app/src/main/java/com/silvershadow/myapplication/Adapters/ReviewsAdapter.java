package com.silvershadow.myapplication.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silvershadow.myapplication.R;
import com.silvershadow.myapplication.ViewModel.SingleMovieViewModel;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    SingleMovieViewModel model;

    public ReviewsAdapter(SingleMovieViewModel m) {
        model = m;
    }

    public class ReviewsViewHolder extends RecyclerView.ViewHolder {
        TextView author;
        TextView content;

        public ReviewsViewHolder(View itemView) {
            super(itemView);
            author = itemView.findViewById(R.id.tv_review_author);
            content = itemView.findViewById(R.id.tv_review_content);
        }
    }



    @NonNull
    @Override
    public ReviewsAdapter.ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.review_item_view,parent,false);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ReviewsViewHolder holder, int position) {
        holder.author.setText(model.getReviews().getValue().get(holder.getAdapterPosition()).getAuthor());
        holder.content.setText(model.getReviews().getValue().get(holder.getAdapterPosition()).getContent());
    }

    @Override
    public int getItemCount() {
        return model.getReviews().getValue().size();
    }


}
