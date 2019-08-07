package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.dp.meshini.servise.model.pojo.PackageClientReview;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewsAdabter extends RecyclerView.Adapter<ReviewsViewHolder>{

    List<PackageClientReview> reviews;

    public ReviewsAdabter(List<PackageClientReview> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.review_list_item,parent,false);
        return new ReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Picasso.get().load(reviews.get(position).getClientImage()).into(holder.imageView);
        holder.comment.setText(reviews.get(position).getComment());
        holder.ratingBar.setRating(reviews.get(position).getRating());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
class ReviewsViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView comment;
    RatingBar ratingBar;
    public ReviewsViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.iv_pic);
        comment=itemView.findViewById(R.id.tv_comment);
        ratingBar=itemView.findViewById(R.id.ratingBar);
    }
}
