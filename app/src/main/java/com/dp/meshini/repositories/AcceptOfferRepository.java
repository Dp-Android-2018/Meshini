package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.AcceptOfferRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class AcceptOfferRepository {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);


    public LiveData<Response<StringMessageResponse>>accetpOffer(AcceptOfferRequest request){
        MutableLiveData<Response<StringMessageResponse>>response=new MutableLiveData<>();
        endPointsLazy.getValue().acceptOffer(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringMessageResponseResponse -> response.setValue(stringMessageResponseResponse),Throwable::printStackTrace);
        return response;
    }
}
