package com.dp.meshini.view.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityTripsBinding;
import com.dp.meshini.servise.model.pojo.PastUpcomingRequest;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.servise.model.response.PastUpcomingRequestsResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.view.adapter.TripsAdapter;
import com.dp.meshini.viewmodel.TripsViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class TripsActivity extends BaseActivity {
    ActivityTripsBinding binding;
    TripsAdapter tripsAdapter;
    String tripType;
    private int page = 1;
    private String next;
    boolean isLoading;
    private List<PastUpcomingRequest> requestList;


    Lazy<TripsViewModel> tripsViewModelLazy = inject(TripsViewModel.class);
    Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_trips);
        setLocality();
        setRecycler();
        upComingTripsButton(binding.btUpcoming);

    }


    public void setLocality(){
        if(sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")){
            binding.ivBack.setRotation(180);
        }
    }
    public void setRecycler() {
        requestList = new ArrayList<>();
        tripsAdapter = new TripsAdapter();
        binding.rvTrips.setAdapter(tripsAdapter);
        binding.rvTrips.addOnScrollListener(onScrollListener());
        binding.rvTrips.setLayoutManager(new LinearLayoutManager(this));
    }


    public void pastTripsButton(View view) {
        tripType = ConstantsFile.Constants.PAST_TRIP;
        page=1;
        requestList.clear();
        binding.vUpcoming.setVisibility(View.GONE);
        binding.vPast.setVisibility(View.VISIBLE);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        tripsViewModelLazy.getValue().getPastRequests(page).observe(this, pastUpcomingRequestsResponseResponse -> {
            if (pastUpcomingRequestsResponseResponse.isSuccessful()) {
                if(pastUpcomingRequestsResponseResponse.body().getRequests().size()<=0){
                    binding.rvTrips.setVisibility(View.GONE);
                    binding.tvNoOffers.setVisibility(View.VISIBLE);
                }
                else {
                    binding.rvTrips.setVisibility(View.VISIBLE);
                    binding.tvNoOffers.setVisibility(View.GONE);

                    requestList.addAll(pastUpcomingRequestsResponseResponse.body().getRequests());
                    tripsAdapter.setRequests(requestList);
                    tripsAdapter.setTripType(ConstantsFile.Constants.PAST_TRIP);
                    tripsAdapter.notifyDataSetChanged();
                    next = pastUpcomingRequestsResponseResponse.body().getLinks().getNext();
                }
            }
            ProgressDialogUtils.getInstance().cancelDialog();
        });
    }

    public void upComingTripsButton(View view) {
        tripType = ConstantsFile.Constants.UPCOMING_TRIP;
        page=1;
        requestList.clear();
        binding.vUpcoming.setVisibility(View.VISIBLE);
        binding.vPast.setVisibility(View.GONE);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        tripsViewModelLazy.getValue().getUpcomingRequests(page).observe(this, pastUpcomingRequestsResponseResponse -> {
            if (pastUpcomingRequestsResponseResponse.isSuccessful()) {
                if(pastUpcomingRequestsResponseResponse.body().getRequests().size()<=0){
                    binding.rvTrips.setVisibility(View.GONE);
                    binding.tvNoOffers.setVisibility(View.VISIBLE);
                }
                else {
                    binding.rvTrips.setVisibility(View.VISIBLE);
                    binding.tvNoOffers.setVisibility(View.GONE);
                    requestList.addAll(pastUpcomingRequestsResponseResponse.body().getRequests());
                    tripsAdapter.setRequests(requestList);
                    tripsAdapter.setTripType(ConstantsFile.Constants.UPCOMING_TRIP);
                    tripsAdapter.notifyDataSetChanged();
                    next = pastUpcomingRequestsResponseResponse.body().getLinks().getNext();
                }
            }else {
                Gson gson = new GsonBuilder().create();
                ErrorResponse errorResponse = new ErrorResponse();
                try {
                    errorResponse = gson.fromJson(pastUpcomingRequestsResponseResponse.errorBody().string(), ErrorResponse.class);
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


    public RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == (requestList.size() - 1)) {
                    if (dy > 0) //check for scroll down
                    {
                        if (next != null && isLoading == false) {
                            page++;
                            System.out.println("Load More Data Success :" + page);
                            isLoading = true;
                            if (tripType.equals(ConstantsFile.Constants.PAST_TRIP)) {
                                ProgressDialogUtils.getInstance().showProgressDialog(TripsActivity.this);
                                tripsViewModelLazy.getValue().getPastRequests(page).observe(TripsActivity.this, pastUpcomingRequestsResponseResponse -> {
                                    if (pastUpcomingRequestsResponseResponse.isSuccessful()) {
                                        requestList.addAll(pastUpcomingRequestsResponseResponse.body().getRequests());
                                        tripsAdapter.setRequests(requestList);
                                        tripsAdapter.notifyDataSetChanged();
                                        next = pastUpcomingRequestsResponseResponse.body().getLinks().getNext();
                                        isLoading = false;
                                    }
                                    ProgressDialogUtils.getInstance().cancelDialog();
                                });
                            } else {
                                ProgressDialogUtils.getInstance().showProgressDialog(TripsActivity.this);
                                tripsViewModelLazy.getValue().getUpcomingRequests(page).observe(TripsActivity.this, pastUpcomingRequestsResponseResponse -> {
                                    if (pastUpcomingRequestsResponseResponse.isSuccessful()) {
                                        requestList.addAll(pastUpcomingRequestsResponseResponse.body().getRequests());
                                        tripsAdapter.setRequests(requestList);
                                        tripsAdapter.notifyDataSetChanged();
                                        next = pastUpcomingRequestsResponseResponse.body().getLinks().getNext();
                                        isLoading = false;
                                    }
                                    ProgressDialogUtils.getInstance().cancelDialog();
                                });
                            }
                        }
                    }
                    System.out.println("next val : "+next);
                }
            }
        };
    }

    public void back(View view){
        finish();
    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }
}
