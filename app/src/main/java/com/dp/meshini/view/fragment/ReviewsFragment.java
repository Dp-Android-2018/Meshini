package com.dp.meshini.view.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.meshini.R;
import com.dp.meshini.databinding.FragmentReviewsBinding;
import com.dp.meshini.servise.model.pojo.PackageClientReview;
import com.dp.meshini.view.adapter.ReviewsAdabter;
import com.google.maps.model.PlaceDetails;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReviewsFragment extends Fragment {


    List<PackageClientReview> reviews;
    FragmentReviewsBinding binding;
    ReviewsAdabter adabter;
    public ReviewsFragment(List<PackageClientReview> reviews) {
        // Required empty public constructor
        this.reviews=reviews;
        adabter=new ReviewsAdabter(reviews);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_reviews,container,false);
        binding.rvReviews.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvReviews.setAdapter(adabter);
        return binding.getRoot();
    }

}
