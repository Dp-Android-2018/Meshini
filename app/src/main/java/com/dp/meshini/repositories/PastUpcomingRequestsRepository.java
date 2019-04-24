package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.response.PastUpcomingRequestsResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PastUpcomingRequestsRepository {

    Lazy<EndPoints> endPointsLazy = inject(EndPoints.class);

    public LiveData<Response<PastUpcomingRequestsResponse>> getPastRequests(int page) {
        MutableLiveData<Response<PastUpcomingRequestsResponse>> responseMutableLiveData = new MutableLiveData<>();
        endPointsLazy.getValue().getPastRequests(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pastUpcomingRequestsResponseResponse -> responseMutableLiveData.setValue(pastUpcomingRequestsResponseResponse), Throwable::printStackTrace);
        return responseMutableLiveData;
    }

    public LiveData<Response<PastUpcomingRequestsResponse>> getUpcomingRequests(int page) {
        MutableLiveData<Response<PastUpcomingRequestsResponse>> responseMutableLiveData = new MutableLiveData<>();
        endPointsLazy.getValue().getUpcomingRequests(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pastUpcomingRequestsResponseResponse -> responseMutableLiveData.setValue(pastUpcomingRequestsResponseResponse), Throwable::printStackTrace);
        return responseMutableLiveData;
    }
}
