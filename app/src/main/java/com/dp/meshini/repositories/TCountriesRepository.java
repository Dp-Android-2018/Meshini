package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.pojo.CountryCityPojo;
import com.dp.meshini.servise.model.response.CountryCityResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class TCountriesRepository {
    Lazy<EndPoints> endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<CountryCityResponse>> getTCountries(){
        MutableLiveData<Response<CountryCityResponse>> countryResponse=new MutableLiveData<>();
        endPointsLazy.getValue().getTCountries()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryCityResponseResponse -> countryResponse.setValue(countryCityResponseResponse),Throwable::printStackTrace);
        return countryResponse;
    }
}
