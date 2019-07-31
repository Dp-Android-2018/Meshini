package com.dp.meshini.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dp.meshini.repositories.PackageDetailRepository;
import com.dp.meshini.servise.model.pojo.PackageClientReview;
import com.dp.meshini.servise.model.pojo.PackageDetail;
import com.dp.meshini.servise.model.response.PackageDetailResponse;

import java.util.List;

import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PackageDetailViewModel extends AndroidViewModel {

    Lazy<PackageDetailRepository>detailRepositoryLazy=inject(PackageDetailRepository.class);

    public PackageDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<PackageDetailResponse>>getPackageDeatil(int packageId){
        return detailRepositoryLazy.getValue().getPackageDetail(packageId);
    }


}
