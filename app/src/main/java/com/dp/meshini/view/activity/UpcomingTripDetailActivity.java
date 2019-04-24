package com.dp.meshini.view.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityUpcomingTripDetailBinding;
import com.dp.meshini.servise.model.pojo.TripDetail;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.view.adapter.TripScheduleAdapter;
import com.dp.meshini.viewmodel.UpcomingTripDetailViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import kotlin.Lazy;

import static com.dp.meshini.utils.ConstantsFile.Constants.OFFER;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class UpcomingTripDetailActivity extends AppCompatActivity {

    private int offerId;
    private String tripType;
    ActivityUpcomingTripDetailBinding binding;
    TripDetail tripDetail;
    TripScheduleAdapter tripScheduleAdapter;
    Lazy<UpcomingTripDetailViewModel> viewModelLazy = inject(UpcomingTripDetailViewModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_upcoming_trip_detail);
        offerId = getIntent().getIntExtra(ConstantsFile.IntentConstants.OFFER_ID, 0);
        tripType=getIntent().getStringExtra(ConstantsFile.IntentConstants.TRIP_TYPE);
        System.out.println("Trip type : "+tripType);
        //System.out.println("trip type : "+tripType);
        if(tripType.equals(ConstantsFile.Constants.PAST_TRIP)){
            binding.btCallGuide.setVisibility(View.GONE);
            binding.btCancelTrip.setVisibility(View.GONE);
            binding.btStartChat.setVisibility(View.GONE);
        }
        tripScheduleAdapter = new TripScheduleAdapter();
        binding.rvTripSchedule.setAdapter(tripScheduleAdapter);
        binding.rvTripSchedule.setLayoutManager(new LinearLayoutManager(this));
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        viewModelLazy.getValue().getTripDetail(offerId).observe(this, tripDetailResponseResponse -> {
            if (tripDetailResponseResponse.isSuccessful()) {
                tripDetail = tripDetailResponseResponse.body().getTripDeatail();
                binding.ratingBar.setRating(tripDetail.getServiceProvider().getRating());
                binding.tvRate.setText("(" + tripDetail.getServiceProvider().getRating() + ") rate");
                Picasso.get().load(tripDetail.getServiceProvider().getProfileImageUrl()).into(binding.ivSpProfileImage);
                binding.tvCountry.setText("Country: " + tripDetail.getCountry());
                binding.tvLocation.setText("Pickup location: " + tripDetail.getPickupAddress());
                binding.tvDateTime.setText("Date :" + tripDetail.getDate());
                binding.tvVehicle.setText("Vehicle: " + tripDetail.getVehicleType());
                binding.tvNumTrips.setText("" + tripDetail.getServiceProvider().getTripsCount());
                tripScheduleAdapter.setPlaces(tripDetail.getPlaces());
                tripScheduleAdapter.notifyDataSetChanged();

            } else {
                Gson gson = new GsonBuilder().create();
                ErrorResponse errorResponse = new ErrorResponse();
                try {
                    errorResponse = gson.fromJson(tripDetailResponseResponse.errorBody().string(), ErrorResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String error = "";
                for (String string : errorResponse.getErrors()) {
                    error += string;
                    error += "\n";
                }
                showSnackbar(error);
            }
            ProgressDialogUtils.getInstance().cancelDialog();
        });
    }

    public void deleteRequest(View view) {
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        viewModelLazy.getValue().deleteRequest(offerId).observe(this, stringMessageResponseResponse -> {
            if (stringMessageResponseResponse.isSuccessful()) {
                showSnackbar(stringMessageResponseResponse.body().getMessage());
                new Handler().postDelayed(() -> {
                    Intent intent = new Intent(UpcomingTripDetailActivity.this, TripsActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }, Snackbar.LENGTH_LONG);
            } else {
                Gson gson = new GsonBuilder().create();
                ErrorResponse errorResponse = new ErrorResponse();
                try {
                    errorResponse = gson.fromJson(stringMessageResponseResponse.errorBody().string(), ErrorResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String error = "";
                for (String string : errorResponse.getErrors()) {
                    error += string;
                    error += "\n";
                }
                showSnackbar(error);
            }
            ProgressDialogUtils.getInstance().cancelDialog();
        });
    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }

    public void callGuide(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+tripDetail.getServiceProvider().getPhone()));
        startActivity(intent);
    }

    public void back(){
        if(tripType.equals(OFFER)){
            Intent intent=new Intent(this,ContainerActivity.class);
            startActivity(intent);
            finishAffinity();
        }else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        back();
    }
}
