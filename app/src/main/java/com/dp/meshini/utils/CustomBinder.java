package com.dp.meshini.utils;

import android.widget.ImageView;

import com.dp.meshini.R;
import com.dp.meshini.view.adapter.GuideReviewsAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CustomBinder {


    @BindingAdapter({"bind:imageUrl"})
    public static void setImageUrl(ImageView imageView, String url){
        if (url!=null && !url.equals(""))
            Picasso.get().load(url).into(imageView);
    }

    @BindingAdapter({"bind:reviews"})
    public static void setReviewsAdapter(RecyclerView view, List<String> reviews) {
        GuideReviewsAdapter adapter=new GuideReviewsAdapter(reviews);
        view.setAdapter(adapter);
    }

    @BindingAdapter({"bind:layoutmanager"})
    public static void setRecyclerLayoutManager(RecyclerView view, RecyclerView.LayoutManager layoutManager){
        view.setLayoutManager(layoutManager);
    }
}
