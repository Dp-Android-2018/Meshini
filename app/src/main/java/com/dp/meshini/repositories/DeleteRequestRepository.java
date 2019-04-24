package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class DeleteRequestRepository {
    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);


    public LiveData<Response<StringMessageResponse>>deleteRequest(int id){
        MutableLiveData<Response<StringMessageResponse>>responseMutableLiveData=new MutableLiveData<>();
        endPointsLazy.getValue().deleteRequest(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringMessageResponseResponse -> responseMutableLiveData.setValue(stringMessageResponseResponse),Throwable::printStackTrace);
        return responseMutableLiveData;
    }

}
