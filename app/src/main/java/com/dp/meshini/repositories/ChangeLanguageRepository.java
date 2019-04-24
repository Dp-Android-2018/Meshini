package com.dp.meshini.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.ChangeLanguageRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.google.api.Endpoint;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ChangeLanguageRepository {

    Lazy<EndPoints>endpointLazy=inject(EndPoints.class);
    Lazy<ChangeLanguageRequest>changeLanguageRequestLazy=inject(ChangeLanguageRequest.class);

    public LiveData<Response<StringMessageResponse>>changeLanguage(String lang){
        ChangeLanguageRequest request=changeLanguageRequestLazy.getValue();
        request.setAppLanguage(lang);
        MutableLiveData<Response<StringMessageResponse>>responseMutableLiveData=new MutableLiveData<>();
        endpointLazy.getValue().changeLanguage(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> responseMutableLiveData.setValue(response),Throwable::printStackTrace);
        return responseMutableLiveData;
    }
}
