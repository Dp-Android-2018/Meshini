package com.dp.meshini.repositories;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.response.PackageDetailResponse;
import com.google.api.Endpoint;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PackageDetailRepository {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<PackageDetailResponse>>getPackageDetail(int packageId){
        MutableLiveData<Response<PackageDetailResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().packageDetail(packageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(packageDetailResponseResponse ->
                        responseMutableLiveData.setValue(packageDetailResponseResponse),
                        throwable -> throwable.printStackTrace());
        return responseMutableLiveData;
    }
}
