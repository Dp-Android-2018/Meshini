package com.dp.meshini.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.PaymentRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PaymentRepository {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<StringMessageResponse>>book(PaymentRequest request){
        MutableLiveData<Response<StringMessageResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().bookReservation(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringMessageResponseResponse -> responseMutableLiveData.setValue(stringMessageResponseResponse), throwable -> throwable.printStackTrace());
        return responseMutableLiveData;
    }
}
