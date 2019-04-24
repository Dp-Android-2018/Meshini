package com.dp.meshini.view.activity;

import android.os.Bundle;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityPendingRequestsBinding;
import com.dp.meshini.servise.model.pojo.PendingRequest;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.servise.model.response.PendingRequestsResponse;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.view.adapter.PendingRequestAdapter;
import com.dp.meshini.view.callback.DeletePendingRequest;
import com.dp.meshini.viewmodel.PendingRequestsViewModel;
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
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PendingRequestsActivity extends BaseActivity implements DeletePendingRequest {

    ActivityPendingRequestsBinding pendingRequestsBinding;
    Lazy<PendingRequestsViewModel> viewModelLazy = inject(PendingRequestsViewModel.class);
    Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    PendingRequestAdapter pendingRequestAdapter;
    private int page ;
    private String next;
    boolean isLoading;
    List<PendingRequest>pendingRequests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pendingRequestsBinding = DataBindingUtil.setContentView(this, R.layout.activity_pending_requests);
        setLocality();
        page=1;
        pendingRequests=new ArrayList<>();
        setPendingRequestsRcView();
    }

    public void setLocality() {
        if (sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")) {
            pendingRequestsBinding.ivBack.setRotation(180);
        }
    }
    public void setPendingRequestsRcView() {
        pendingRequestsBinding.rvPendingRequests.setLayoutManager(new LinearLayoutManager(this));
        pendingRequestAdapter = new PendingRequestAdapter(this);
        pendingRequestsBinding.rvPendingRequests.addOnScrollListener(onScrollListener());
        pendingRequestsBinding.rvPendingRequests.setAdapter(pendingRequestAdapter);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        viewModelLazy.getValue().getPendingRequests(page).observe(this, pendingRequestsResponseResponse -> {
            if (pendingRequestsResponseResponse.isSuccessful()) {
                if(pendingRequestsResponseResponse.body().getPendingRequests().size()<=0){
                    pendingRequestsBinding.rvPendingRequests.setVisibility(View.GONE);
                    pendingRequestsBinding.tvNoOffers.setVisibility(View.VISIBLE);
                }else {
                    pendingRequestsBinding.rvPendingRequests.setVisibility(View.VISIBLE);
                    pendingRequestsBinding.tvNoOffers.setVisibility(View.GONE);
                    pendingRequests.addAll(pendingRequestsResponseResponse.body().getPendingRequests());
                    pendingRequestAdapter.setPendingRequests(pendingRequests);
                    pendingRequestAdapter.notifyDataSetChanged();
                    next = pendingRequestsResponseResponse.body().getLinks().getNext();
                }
            }
            ProgressDialogUtils.getInstance().cancelDialog();
        });
    }


    public RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == (pendingRequests.size() - 1)) {
                    if (dy > 0) {
                        if (next != null && isLoading == false){
                            page++;
                            isLoading=true;
                            ProgressDialogUtils.getInstance().showProgressDialog(PendingRequestsActivity.this);
                            viewModelLazy.getValue().getPendingRequests(page).observe(PendingRequestsActivity.this, new Observer<Response<PendingRequestsResponse>>() {
                                @Override
                                public void onChanged(Response<PendingRequestsResponse> pendingRequestsResponseResponse) {
                                    if(pendingRequestsResponseResponse.isSuccessful()){
                                        pendingRequests.addAll(pendingRequestsResponseResponse.body().getPendingRequests());
                                        pendingRequestAdapter.setPendingRequests(pendingRequests);
                                        pendingRequestAdapter.notifyDataSetChanged();
                                        next=pendingRequestsResponseResponse.body().getLinks().getNext();
                                        isLoading=false;
                                        ProgressDialogUtils.getInstance().cancelDialog();
                                    }
                                }
                            });
                        }
                    }
                }
            }
        };
    }

    @Override
    public void onItemDelete(int requestId) {
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        viewModelLazy.getValue().deleteRequest(requestId).observe(this, stringMessageResponseResponse -> {
            if (stringMessageResponseResponse.isSuccessful()) {
                showSnackbar(stringMessageResponseResponse.body().getMessage());
                for (int i=0;i<pendingRequests.size();i++){
                    if(pendingRequests.get(i).getId()==requestId){
                        pendingRequests.remove(i);
                        //pendingRequestAdapter.notifyItemRemoved(i);
                        pendingRequestAdapter.setPendingRequests(pendingRequests);
                        pendingRequestAdapter.notifyDataSetChanged();
                        pendingRequestsBinding.rvPendingRequests.setAdapter(pendingRequestAdapter);
                        pendingRequestsBinding.rvPendingRequests.invalidate();
                        ProgressDialogUtils.getInstance().cancelDialog();
                        break;
                    }

                    //pendingRequestsBinding.rvPendingRequests.notifyAll();

                }
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
        });
    }

    public void showSnackbar(String message) {
        Snackbar.make(pendingRequestsBinding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }

    public void back(View view){
        finish();
    }
}
