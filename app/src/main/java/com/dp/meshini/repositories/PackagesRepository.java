package com.dp.meshini.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.GetPackagesRequest;
import com.dp.meshini.servise.model.response.PackagesResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PackagesRepository {

    Lazy<EndPoints> endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<PackagesResponse>> getPackages(GetPackagesRequest request,int page){
        MutableLiveData<Response<PackagesResponse>> responseLiveData=new MutableLiveData<>();
        String s=null;
        if(request.getCityId()>0){
            s=String.valueOf(request.getCityId());
        }
        endPointsLazy.getValue().getPackages(request.getCountryId(),request.getStartDate(),s,request.getSeatsNumber(),page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(packagesResponseResponse -> responseLiveData.setValue(packagesResponseResponse),Throwable::printStackTrace);
        return responseLiveData;
    }
}
