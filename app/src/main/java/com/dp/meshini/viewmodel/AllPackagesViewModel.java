package com.dp.meshini.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dp.meshini.repositories.PackagesRepository;
import com.dp.meshini.servise.model.request.GetPackagesRequest;
import com.dp.meshini.servise.model.response.PackagesResponse;

import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class AllPackagesViewModel extends AndroidViewModel {
    Lazy<PackagesRepository>packagesRepositoryLazy=inject(PackagesRepository.class);

    public AllPackagesViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<PackagesResponse>>getPackagesResponse(GetPackagesRequest request,int page){
        return packagesRepositoryLazy.getValue().getPackages(request,page);
    }
}
