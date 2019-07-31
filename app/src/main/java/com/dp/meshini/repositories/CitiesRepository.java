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

public class CitiesRepository {
    Lazy<EndPoints> endPointsLazy = inject(EndPoints.class);

    public LiveData<Response<CountryCityResponse>> getCities(int countryId) {
        MutableLiveData<Response<CountryCityResponse>> cityResponse = new MutableLiveData<>();
        endPointsLazy.getValue().getCities(countryId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(countryCityResponseResponse -> cityResponse.setValue(countryCityResponseResponse), Throwable::printStackTrace);
        return cityResponse;
    }
}

