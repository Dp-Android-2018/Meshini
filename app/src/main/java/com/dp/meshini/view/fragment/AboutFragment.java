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
public class AboutFragment extends Fragment {


    String about;
    public AboutFragment( String about) {
        // Required empty public constructor
        this.about=about;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        TextView textView;
        View view= inflater.inflate(R.layout.fragment_about, container, false);
        textView=view.findViewById(R.id.tv_data);
        textView.setMovementMethod(new ScrollingMovementMethod());
        textView.setText(about);
        return view;
    }

}
