package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.SendActivationCodeRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class SendActivationCodeRepository {
    Lazy<EndPoints> endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<StringMessageResponse>>sendActivationCode(SendActivationCodeRequest request) {
        MutableLiveData<Response<StringMessageResponse>>respons=new MutableLiveData<>();
        endPointsLazy.getValue().sendActivationCode(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringMessageResponseResponse -> respons.setValue(stringMessageResponseResponse), Throwable::printStackTrace);
        return respons;
    }
}
