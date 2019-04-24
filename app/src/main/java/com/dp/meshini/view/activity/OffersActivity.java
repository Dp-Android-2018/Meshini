package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityOffersBinding;
import com.dp.meshini.servise.model.pojo.Offer;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.servise.model.response.OffersResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.view.adapter.OffersAdapter;
import com.dp.meshini.viewmodel.OffersViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import androidx.transition.TransitionManager;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class OffersActivity extends BaseActivity {

    int tripId;
    ActivityOffersBinding binding;
    private int page ;
    private String next;
    boolean isLoading;
    List<Offer> offers;
    OffersAdapter offersAdapter;
    Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    Lazy<OffersViewModel> viewModelLazy = inject(OffersViewModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_offers);
        setLocality();
        tripId = getIntent().getIntExtra(ConstantsFile.IntentConstants.TRIP_ID, 0);
        offers = new ArrayList<>();
        page=1;
        setOffersRecycler();
    }

    public void setLocality() {
        if (sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")) {
            binding.ivBack.setRotation(180);
        }
    }

    public void setOffersRecycler() {
        offersAdapter = new OffersAdapter(tripId);
        binding.rvOffers.setAdapter(offersAdapter);
        offersAdapter.setOffers(offers);
        binding.rvOffers.addOnScrollListener(onScrollListener());
       // ((SimpleItemAnimator) binding.rvOffers.getItemAnimator()).setChangeDuration(3000);
        //((SimpleItemAnimator) binding.rvOffers.getItemAnimator()).setSupportsChangeAnimations(true);
        binding.rvOffers.setLayoutManager(new LinearLayoutManager(this));
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        viewModelLazy.getValue().getOffers(tripId,page).observe(this, offersResponseResponse -> {
            if (offersResponseResponse.isSuccessful()) {
                if(offersResponseResponse.body().getOffers().size()<=0){
                    binding.rvOffers.setVisibility(View.GONE);
                    binding.tvNoOffers.setVisibility(View.VISIBLE);
                }else {
                    binding.rvOffers.setVisibility(View.VISIBLE);
                    binding.tvNoOffers.setVisibility(View.GONE);
                    offers.addAll(offersResponseResponse.body().getOffers());
                    // offersAdapter.setOffers(offers);
                    TransitionManager.beginDelayedTransition(binding.rvOffers);
                    offersAdapter.notifyDataSetChanged();
                    next=offersResponseResponse.body().getLinks().getNext();
                }
            }else {
                Gson gson = new GsonBuilder().create();
                ErrorResponse errorResponse = new ErrorResponse();
                try {
                    errorResponse = gson.fromJson(offersResponseResponse.errorBody().string(), ErrorResponse.class);
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

    public void back(View view) {
        Intent intent = new Intent(this, PendingRequestsActivity.class);
        startActivity(intent);
        finish();
    }

    public RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == (offers.size() - 1)) {
                    if (dy > 0) {
                        if (next != null && isLoading == false){
                            page++;
                            isLoading=true;
                            ProgressDialogUtils.getInstance().showProgressDialog(OffersActivity.this);
                            viewModelLazy.getValue().getOffers(tripId,page).observe(OffersActivity.this, new Observer<Response<OffersResponse>>() {
                                @Override
                                public void onChanged(Response<OffersResponse> offersResponseResponse) {
                                    offers.addAll(offersResponseResponse.body().getOffers());
                                   // offersAdapter.setOffers(offers);
                                    offersAdapter.notifyDataSetChanged();
                                    next=offersResponseResponse.body().getLinks().getNext();
                                    isLoading=false;
                                    ProgressDialogUtils.getInstance().cancelDialog();
                                }
                            });
                        }
                    }
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        back(binding.ivBack);
        //super.onBackPressed();
    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }
}
