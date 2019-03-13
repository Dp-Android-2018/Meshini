package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.ResetPasswordRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ResetPasswordRepository {

    Lazy<EndPoints> endPointsLazy = inject(EndPoints.class);

    public LiveData<Response<StringMessageResponse>> resetPassword(ResetPasswordRequest request) {
        MutableLiveData<Response<StringMessageResponse>> response = new MutableLiveData<>();
        endPointsLazy.getValue().resetPassword(request.getToken(), request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(stringMessageResponseResponse -> response.setValue(stringMessageResponseResponse), Throwable::printStackTrace);
        return response;
    }
}
