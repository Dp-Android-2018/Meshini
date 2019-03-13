package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.ActivatePhoneRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ActivatePhoneRepository {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<StringMessageResponse>>checkActivationCode(ActivatePhoneRequest request){
        MutableLiveData<Response<StringMessageResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().activatePhone(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringMessageResponseResponse -> responseMutableLiveData.setValue(stringMessageResponseResponse),Throwable::printStackTrace);
        return responseMutableLiveData;
    }

}
