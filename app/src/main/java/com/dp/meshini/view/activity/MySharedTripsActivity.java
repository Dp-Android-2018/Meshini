package com.dp.meshini.view.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityMySharedTripsBinding;
import com.dp.meshini.servise.model.pojo.PastUpcommingSharedListItem;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.servise.model.response.PastUpcomingSharedResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.view.adapter.SharedTripsHistoryAdapter;
import com.dp.meshini.viewmodel.SharedTripHistoryViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class MySharedTripsActivity extends AppCompatActivity {

    ActivityMySharedTripsBinding binding;
    SharedTripsHistoryAdapter adapter;
    String tripType;
    private int page = 1;
    private String next;
    boolean isLoading;
    private List<PastUpcommingSharedListItem> listItems;


    Lazy<SharedTripHistoryViewModel> tripsViewModelLazy = inject(SharedTripHistoryViewModel.class);
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_shared_trips);
        setLocality();
        setRecycler();
        upComingTripsButton(binding.btUpcoming);

    }


    public void setLocality() {
        if (sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")) {
            binding.ivBack.setRotation(180);
        }
    }

    public void setRecycler() {
        listItems = new ArrayList<>();
        adapter = new SharedTripsHistoryAdapter();
        adapter.setTripType(tripType);
        binding.rvTrips.setAdapter(adapter);
        binding.rvTrips.addOnScrollListener(onScrollListener());
        binding.rvTrips.setLayoutManager(new LinearLayoutManager(this));
    }


    public void pastTripsButton(View view) {
        tripType = ConstantsFile.Constants.PAST_TRIP;
        adapter.setTripType(ConstantsFile.Constants.PAST_TRIP);
        page = 1;
        listItems.clear();
        binding.vUpcoming.setVisibility(View.GONE);
        binding.vPast.setVisibility(View.VISIBLE);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        tripsViewModelLazy.getValue().getPastTrips(page).observe(this, new Observer<Response<PastUpcomingSharedResponse>>() {
            @Override
            public void onChanged(Response<PastUpcomingSharedResponse> pastUpcomingSharedResponseResponse) {
                ProgressDialogUtils.getInstance().cancelDialog();
                if (pastUpcomingSharedResponseResponse.isSuccessful()) {
                    if (pastUpcomingSharedResponseResponse.body().getListItems().size() <= 0) {
                        binding.rvTrips.setVisibility(View.GONE);
                        binding.tvNoOffers.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvTrips.setVisibility(View.VISIBLE);
                        binding.tvNoOffers.setVisibility(View.GONE);

                        listItems.addAll(pastUpcomingSharedResponseResponse.body().getListItems());
                        adapter.setSharedListItems(listItems);
                        adapter.notifyDataSetChanged();
                        next = pastUpcomingSharedResponseResponse.body().getLinks().getNext();
                    }
                }
                ProgressDialogUtils.getInstance().cancelDialog();
            }
        });
    }

    public void upComingTripsButton(View view) {
        tripType = ConstantsFile.Constants.UPCOMING_TRIP;
        adapter.setTripType(ConstantsFile.Constants.UPCOMING_TRIP);
        page = 1;
        listItems.clear();
        binding.vUpcoming.setVisibility(View.VISIBLE);
        binding.vPast.setVisibility(View.GONE);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        tripsViewModelLazy.getValue().getUpcommingTrips(page).observe(this, new Observer<Response<PastUpcomingSharedResponse>>() {
            @Override
            public void onChanged(Response<PastUpcomingSharedResponse> pastUpcomingSharedResponseResponse) {
                ProgressDialogUtils.getInstance().cancelDialog();
                if (pastUpcomingSharedResponseResponse.isSuccessful()) {
                    if (pastUpcomingSharedResponseResponse.body().getListItems().size() <= 0) {
                        binding.rvTrips.setVisibility(View.GONE);
                        binding.tvNoOffers.setVisibility(View.VISIBLE);
                    } else {
                        binding.rvTrips.setVisibility(View.VISIBLE);
                        binding.tvNoOffers.setVisibility(View.GONE);
                        listItems.addAll(pastUpcomingSharedResponseResponse.body().getListItems());
                        adapter.setSharedListItems(listItems);
                        adapter.notifyDataSetChanged();
                        next = pastUpcomingSharedResponseResponse.body().getLinks().getNext();

                    }
                } else {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse = new ErrorResponse();
                    try {
                        errorResponse = gson.fromJson(pastUpcomingSharedResponseResponse.errorBody().string(), ErrorResponse.class);
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
            }
        });
    }


    public RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == (listItems.size() - 1)) {
                    if (dy > 0) //check for scroll down
                    {
                        if (next != null && isLoading == false) {
                            page++;
                            System.out.println("Load More Data Success :" + page);
                            isLoading = true;
                            if (tripType.equals(ConstantsFile.Constants.PAST_TRIP)) {
                                ProgressDialogUtils.getInstance().showProgressDialog(MySharedTripsActivity.this);
                                tripsViewModelLazy.getValue().getPastTrips(page).observe(MySharedTripsActivity.this, new Observer<Response<PastUpcomingSharedResponse>>() {
                                    @Override
                                    public void onChanged(Response<PastUpcomingSharedResponse> pastUpcomingSharedResponseResponse) {
                                        if (pastUpcomingSharedResponseResponse.isSuccessful()) {
                                            listItems.addAll(pastUpcomingSharedResponseResponse.body().getListItems());
                                            adapter.setSharedListItems(listItems);
                                            adapter.notifyDataSetChanged();
                                            next = pastUpcomingSharedResponseResponse.body().getLinks().getNext();
                                            isLoading = false;
                                        }
                                        ProgressDialogUtils.getInstance().cancelDialog();
                                    }
                                });
                            } else {
                                ProgressDialogUtils.getInstance().showProgressDialog(MySharedTripsActivity.this);
                                tripsViewModelLazy.getValue().getUpcommingTrips(page).observe(MySharedTripsActivity.this, new Observer<Response<PastUpcomingSharedResponse>>() {
                                    @Override
                                    public void onChanged(Response<PastUpcomingSharedResponse> pastUpcomingSharedResponseResponse) {
                                        if (pastUpcomingSharedResponseResponse.isSuccessful()) {
                                            listItems.addAll(pastUpcomingSharedResponseResponse.body().getListItems());
                                            adapter.setSharedListItems(listItems);
                                            adapter.notifyDataSetChanged();
                                            next = pastUpcomingSharedResponseResponse.body().getLinks().getNext();
                                            isLoading = false;
                                        }
                                        ProgressDialogUtils.getInstance().cancelDialog();
                                    }
                                });
                            }
                        }
                    }
                    System.out.println("next val : " + next);
                }
            }
        };
    }

    public void back(View view) {
        finish();
    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }
}
