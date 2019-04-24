package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.response.TripDetailResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class TripDetailRepository {

Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);


public LiveData<Response<TripDetailResponse>>getTripDetail(int id){
    MutableLiveData<Response<TripDetailResponse>>responseMutableLiveData=new MutableLiveData<>();
    endPointsLazy.getValue().getTripDetail(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(tripDetailResponseResponse -> responseMutableLiveData.setValue(tripDetailResponseResponse),Throwable::printStackTrace);
    return responseMutableLiveData;
}


}
