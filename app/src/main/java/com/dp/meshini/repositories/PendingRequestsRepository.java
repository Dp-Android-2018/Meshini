package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.response.PendingRequestsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PendingRequestsRepository {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<PendingRequestsResponse>>getPendingRequests(int page){
        MutableLiveData<Response<PendingRequestsResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().getPendingRequests(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pendingRequestsResponseResponse -> responseMutableLiveData.setValue(pendingRequestsResponseResponse),Throwable::printStackTrace);
        return responseMutableLiveData;
    }
}
