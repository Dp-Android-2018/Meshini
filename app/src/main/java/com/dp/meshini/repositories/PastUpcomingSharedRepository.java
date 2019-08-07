package com.dp.meshini.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.response.PastUpcomingSharedResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PastUpcomingSharedRepository {
    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<PastUpcomingSharedResponse>>getUpcomingSharedHistory(int pageId){
        MutableLiveData<Response<PastUpcomingSharedResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().getUpcomingSharedTrips(pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pastUpcomingSharedResponseResponse -> responseMutableLiveData.setValue(pastUpcomingSharedResponseResponse), throwable -> throwable.printStackTrace());
        return responseMutableLiveData;
    }

    public LiveData<Response<PastUpcomingSharedResponse>>getPastSharedHistory(int pageId){
        MutableLiveData<Response<PastUpcomingSharedResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().getPastSharedTrips(pageId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pastUpcomingSharedResponseResponse -> responseMutableLiveData.setValue(pastUpcomingSharedResponseResponse), throwable -> throwable.printStackTrace());
        return responseMutableLiveData;
    }
}
