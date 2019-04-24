package com.dp.meshini.repositories;

import android.app.Application;
import android.widget.Toast;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.pojo.CountryCityPojo;
import com.dp.meshini.servise.model.response.CountryCityResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class LanguagesRepository {

    Lazy<EndPoints> endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<CountryCityResponse>> getLanguages(){
        MutableLiveData<Response<CountryCityResponse>> countryResponse=new MutableLiveData<>();
        endPointsLazy.getValue().getLanguages()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryCityResponseResponse -> {
                        countryResponse.setValue(countryCityResponseResponse);
                },Throwable::printStackTrace);
        return countryResponse;
    }
}
