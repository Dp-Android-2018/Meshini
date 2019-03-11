package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.LoginRequest;
import com.dp.meshini.servise.model.response.LoginRegisterResponse;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class LoginRepository {

    Lazy<EndPoints> endPointsLazy = inject(EndPoints.class);

    public LiveData<Response<LoginRegisterResponse>>login(LoginRequest request){
        MutableLiveData<Response<LoginRegisterResponse>>loginResponse=new MutableLiveData<>();
        endPointsLazy.getValue().login(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(loginRegisterResponseResponse -> {
                    loginResponse.setValue(loginRegisterResponseResponse);

                },Throwable::printStackTrace);
        return loginResponse;
    }


}
