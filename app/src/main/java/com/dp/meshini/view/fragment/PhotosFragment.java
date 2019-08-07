package com.dp.meshini.view.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.meshini.R;
import com.dp.meshini.databinding.FragmentPhotosBinding;
import com.dp.meshini.view.adapter.PhotosAdabter;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class PhotosFragment extends Fragment {


    List<String> photos;
    FragmentPhotosBinding binding;
    PhotosAdabter adabter;
    public PhotosFragment(List<String> photos) {
        // Required empty public constructor
        this.photos=photos;
        adabter=new PhotosAdabter(photos);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_photos,container,false);
        binding.rvPhotos.setLayoutManager(new GridLayoutManager(getContext(),2));
        binding.rvPhotos.setAdapter(adabter);
        return binding.getRoot();
    }

}
