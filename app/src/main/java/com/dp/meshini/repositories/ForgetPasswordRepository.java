package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.ActivatePhoneRequest;
import com.dp.meshini.servise.model.request.ForgetPasswordRequest;
import com.dp.meshini.servise.model.request.ResetPasswordRequest;
import com.dp.meshini.servise.model.response.ForgetPasswordResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ForgetPasswordRepository {

    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);

    public LiveData<Response<ForgetPasswordResponse>>fogetPassword(ForgetPasswordRequest request){
        MutableLiveData<Response<ForgetPasswordResponse>>response=new MutableLiveData<>();
        endPointsLazy.getValue().forgetPassword(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(forgetPasswordResponseResponse -> response.setValue(forgetPasswordResponseResponse),Throwable::printStackTrace);

        return response;
    }
}
