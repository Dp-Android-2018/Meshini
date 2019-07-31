package com.dp.meshini.view.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dp.meshini.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DeatilesFragment extends Fragment {

    String detailes;
    public DeatilesFragment(String detailes) {
        // Required empty public constructor
        this.detailes=detailes;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView;
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detailes, container, false);
        textView=view.findViewById(R.id.tv_data);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(detailes);
        return view;
    }

}
