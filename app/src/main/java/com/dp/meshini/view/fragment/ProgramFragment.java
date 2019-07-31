package com.dp.meshini.view.fragment;


import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.meshini.R;
import com.dp.meshini.databinding.FragmentProgramBinding;
import com.dp.meshini.servise.model.pojo.Program;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramFragment extends Fragment {

    FragmentProgramBinding binding;
    Program program;
    double price;
    public ProgramFragment(Program program,double price) {
        // Required empty public constructor
        this.program=program;
        this.price=price;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,R.layout.fragment_program,container,false);
        binding.tvHotel.setText(program.getHotelName());
        binding.tvBed.setText(rooms());
        binding.tvBreakfast.setText(program.getMeals());
        binding.tvTransport.setText(program.getTransportations());
        binding.tvCost.setText("start from "+price+" EGP");
        return binding.getRoot();
    }


    public String rooms(){
        String s="";
        for(String x:program.getAvailableRooms()){
            s+=x+"-";
        }
        return s;
    }
}
