package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dp.meshini.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GuideReviewsAdapter extends RecyclerView.Adapter<GuideReviewHolder> {

    List<String>reviews;

    public GuideReviewsAdapter(List<String> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public GuideReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.guide_review_list_item,parent ,false);
        return new GuideReviewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GuideReviewHolder holder, int position) {
        holder.review.setText(reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
class GuideReviewHolder extends RecyclerView.ViewHolder{

    TextView review;
    public GuideReviewHolder(@NonNull View itemView) {
        super(itemView);
        review=itemView.findViewById(R.id.tv_review_content);
    }
}
