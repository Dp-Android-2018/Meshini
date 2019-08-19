package com.dp.meshini.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.meshini.R;
import com.dp.meshini.databinding.FragmentProgramBinding;
import com.dp.meshini.servise.model.pojo.Payment;
import com.dp.meshini.servise.model.pojo.Program;
import com.dp.meshini.servise.model.pojo.Room;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.view.activity.PaymentActivity;
import com.dp.meshini.view.adapter.DestinationSharedAdapter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static com.dp.meshini.utils.ConstantsFile.IntentConstants.DOUBLE_PRICE;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.PAY_METHODS;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.ROOMS_TYPE;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SHARED_TRIP_ID;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SINGLE_PRICE;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIPLE_PRICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgramFragment extends Fragment {

    FragmentProgramBinding binding;
    Program program;
    double price;
    double singlePrice;
    double doublePrice;
    double triplePrice;
    int packageId;
    List<Payment>payments;
    DestinationSharedAdapter adapter;
    String tripType;
    Bundle bundle;

    public ProgramFragment(Program program, double price, double singlePrice, double doublePrice, double triplePrice, List<Payment> payments,int packageID, String tripType) {
        // Required empty public constructor
        this.program=program;
        this.price=price;
        this.tripType=tripType;
        this.singlePrice=singlePrice;
        this.doublePrice=doublePrice;
        this.triplePrice=triplePrice;
        this.payments=payments;
        this.packageId=packageID;
        bundle=new Bundle();
        bundle.putInt(SHARED_TRIP_ID,packageID);
        bundle.putSerializable(ROOMS_TYPE, (ArrayList<Room>) program.getAvailableRooms());
        bundle.putSerializable(PAY_METHODS, (Serializable) payments);

        adapter=new DestinationSharedAdapter(program.getCityName());
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
        setRecyclerView();
        handelButtonPay();
        return binding.getRoot();
    }

    public void setRecyclerView(){
        binding.rvDestinations.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvDestinations.setAdapter(adapter);

    }


    public String rooms(){
        String s="";
        for(Room x:program.getAvailableRooms()){
            s+=x.getRoomType()+"-";
        }
        return s;
    }

    public void handelButtonPay(){
        System.out.println("trip type case : "+tripType);
        switch (tripType){
            case ConstantsFile.Constants.UPCOMING_TRIP:{
                binding.btPay.setVisibility(View.GONE);
                break;
            }case ConstantsFile.Constants.PAST_TRIP:{
                binding.btPay.setVisibility(View.GONE);
                break;
            }default:{
                binding.btPay.setText(R.string.go_to_payment);
                // TODO: 8/6/2019 start pay ment cycle
            }
        }

        binding.btPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), PaymentActivity.class);
                intent.putExtra("bundle",bundle);
                startActivity(intent);
            }
        });
    }
}
