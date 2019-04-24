package com.dp.meshini.repositories;

import android.annotation.SuppressLint;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.CreateTripRequest;
import com.dp.meshini.servise.model.response.CreateTripResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class CreateTripRepository {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);

    @SuppressLint("CheckResult")
    public LiveData<Response<CreateTripResponse>>createTrip(CreateTripRequest request){
        MutableLiveData<Response<CreateTripResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().createTrip(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(createTripResponseResponse -> responseMutableLiveData.setValue(createTripResponseResponse),Throwable::printStackTrace);
        return responseMutableLiveData;
    }
}
