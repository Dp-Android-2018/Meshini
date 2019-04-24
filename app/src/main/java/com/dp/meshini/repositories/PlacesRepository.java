package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.pojo.Place;
import com.dp.meshini.servise.model.response.PlacesResponse;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PlacesRepository  {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<PlacesResponse>>getPlaces(int id){
        MutableLiveData<Response<PlacesResponse>>places=new MutableLiveData<>();
        endPointsLazy.getValue().getPlaces(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(placesResponseResponse -> places.setValue(placesResponseResponse),Throwable::printStackTrace);
        return places;
    }

}
