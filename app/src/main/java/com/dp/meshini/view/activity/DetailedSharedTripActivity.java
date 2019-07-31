package com.dp.meshini.view.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.viewpager.widget.ViewPager;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityDetailedSharedTripBinding;
import com.dp.meshini.servise.model.pojo.PackageDetail;
import com.dp.meshini.servise.model.response.PackageDetailResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.view.adapter.ViewPagerAdapter;
import com.dp.meshini.view.fragment.AboutFragment;
import com.dp.meshini.view.fragment.PhotosFragment;
import com.dp.meshini.view.fragment.ProgramFragment;
import com.dp.meshini.view.fragment.DeatilesFragment;
import com.dp.meshini.view.fragment.ReviewsFragment;
import com.dp.meshini.viewmodel.PackageDetailViewModel;
import com.squareup.picasso.Picasso;

import kotlin.Lazy;
import retrofit2.Response;

import static androidx.fragment.app.FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class DetailedSharedTripActivity extends BaseActivity {

    ActivityDetailedSharedTripBinding binding;
    Lazy<PackageDetailViewModel>viewModelLazy=inject(PackageDetailViewModel.class);
    PackageDetail packageDetail;
    int packageId;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_detailed_shared_trip);
        binding.tabs.setupWithViewPager(binding.viewpager);
        packageId=getIntent().getIntExtra(ConstantsFile.IntentConstants.SHARED_TRIP_ID,0);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        viewModelLazy.getValue().getPackageDeatil(packageId).observe(this, new Observer<Response<PackageDetailResponse>>() {
            @Override
            public void onChanged(Response<PackageDetailResponse> packageDetailResponseResponse) {
                ProgressDialogUtils.getInstance().cancelDialog();
                if(packageDetailResponseResponse.isSuccessful()){
                    packageDetail=packageDetailResponseResponse.body().getPackageDetail();
                    setDataToView();
                    setupViewPager(binding.viewpager);
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(),BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        adapter.addFragment(new ProgramFragment(packageDetail.getProgram(),packageDetail.getMinimumPrice()), getString(R.string.program));
        adapter.addFragment(new DeatilesFragment(packageDetail.getDetails()), getString(R.string.details));
        adapter.addFragment(new PhotosFragment(), getString(R.string.photos));
        adapter.addFragment(new ReviewsFragment(),getString(R.string.reviews));
        adapter.addFragment(new AboutFragment(packageDetail.getAboutCompany()),getString(R.string.about));

        viewPager.setAdapter(adapter);
    }


    public void setDataToView(){
        binding.tvCompanyName.setText(packageDetail.getCompanyName());
        binding.tvCompanyAddress.setText(packageDetail.getCompanyAddress());
        binding.ratingBar.setRating(packageDetail.getCompanyRating());
        Picasso.get().load(packageDetail.getCompanyImageUrl()).into(binding.ivCompanyLogoImage);
    }
}
