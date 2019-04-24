package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.response.ActiveTripDetailResponse;
import com.dp.meshini.servise.model.response.ActiveTripResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ActiveTripDetailRepository {

    Lazy<EndPoints> endPointsLazy = inject(EndPoints.class);

    public LiveData<Response<ActiveTripDetailResponse>>activeTripDetail(){
        MutableLiveData<Response<ActiveTripDetailResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().activeTripDetail()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(activeTripDetailResponseResponse -> responseMutableLiveData.setValue(activeTripDetailResponseResponse),Throwable::printStackTrace);
        return responseMutableLiveData;
    }

}
