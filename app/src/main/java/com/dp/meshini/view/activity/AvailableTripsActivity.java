package com.dp.meshini.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityAvailableTripsBinding;
import com.dp.meshini.servise.model.pojo.PackageData;
import com.dp.meshini.servise.model.request.GetPackagesRequest;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.servise.model.response.PackagesResponse;
import com.dp.meshini.servise.model.response.PendingRequestsResponse;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.view.adapter.PackagesAdapter;
import com.dp.meshini.viewmodel.AllPackagesViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.Lazy;
import retrofit2.Response;

import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SHARED_TRIP;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class AvailableTripsActivity extends AppCompatActivity {

    ActivityAvailableTripsBinding binding;
    List<PackageData> packageDataList;
    GetPackagesRequest request;
    Lazy<AllPackagesViewModel> viewModelLazy = inject(AllPackagesViewModel.class);
    private int page;
    private String next;
    boolean isLoading;
    PackagesAdapter packagesAdapter;
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_available_trips);
        setLocality();
        request=(GetPackagesRequest)getIntent().getSerializableExtra(SHARED_TRIP);
        packageDataList = new ArrayList<>();
        page = 1;
        setRecyclerView();
    }

    public void setLocality() {
        if (sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")) {
            binding.ivBack.setRotation(180);
        }
    }

    public void setRecyclerView() {
        binding.rvPackages.setLayoutManager(new LinearLayoutManager(this));
        packagesAdapter = new PackagesAdapter();
        binding.rvPackages.addOnScrollListener(onScrollListener());
        binding.rvPackages.setAdapter(packagesAdapter);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        viewModelLazy.getValue().getPackagesResponse(request, page).observe(this, packagesResponseResponse -> {
            ProgressDialogUtils.getInstance().cancelDialog();
            if (packagesResponseResponse.isSuccessful()) {
                if (packagesResponseResponse.body().getPackages().size() <= 0) {
                    binding.rvPackages.setVisibility(View.GONE);
                    binding.tvNoPackages.setVisibility(View.VISIBLE);
                } else {
                    binding.rvPackages.setVisibility(View.VISIBLE);
                    binding.tvNoPackages.setVisibility(View.GONE);
                    packageDataList.addAll(packagesResponseResponse.body().getPackages());
                    packagesAdapter.setPackageDataList(packageDataList);
                    packagesAdapter.notifyDataSetChanged();
                    next = packagesResponseResponse.body().getLinks().getNext();
                }
            }else {
                //ErrorResponse errorResponse=(ErrorResponse) stringMessageResponseResponse.errorBody();
                Gson gson = new GsonBuilder().create();
                ErrorResponse errorResponse=new ErrorResponse();
                try {
                    errorResponse=gson.fromJson(packagesResponseResponse.errorBody().string(),ErrorResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String string:errorResponse.getErrors()){
                    showSnackbar(string);
                }
            }
        });
    }


    public RecyclerView.OnScrollListener onScrollListener() {
        return new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition() == (packageDataList.size() - 1)) {
                    if (dy > 0) {
                        if (next != null && isLoading == false) {
                            page++;
                            isLoading = true;
                            ProgressDialogUtils.getInstance().showProgressDialog(AvailableTripsActivity.this);
                            viewModelLazy.getValue().getPackagesResponse(request, page).observe(AvailableTripsActivity.this, packagesResponseResponse -> {

                                if (packagesResponseResponse.isSuccessful()) {
                                    packageDataList.addAll(packagesResponseResponse.body().getPackages());
                                    packagesAdapter.setPackageDataList(packageDataList);
                                    packagesAdapter.notifyDataSetChanged();
                                    next = packagesResponseResponse.body().getLinks().getNext();
                                    isLoading = false;
                                    ProgressDialogUtils.getInstance().cancelDialog();
                                }
                            });
                        }
                    }
                }
            }
        };
    }

    public void showSnackbar(String message){
        Snackbar.make(binding.clRoot,message,Snackbar.LENGTH_LONG).show();
    }

    public void back(View view){
        finish();
    }
}

