package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.response.ActiveTripResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ActiveTripRepository {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);


    public LiveData<Response<ActiveTripResponse>>getActiveTrip(){
        MutableLiveData<Response<ActiveTripResponse>> responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().getActiveTrip()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activeTripResponseResponse -> responseMutableLiveData.setValue(activeTripResponseResponse),Throwable::printStackTrace);
        return responseMutableLiveData;
    }
}
