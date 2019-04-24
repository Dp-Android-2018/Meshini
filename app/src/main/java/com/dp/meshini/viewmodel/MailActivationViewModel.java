package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.ActivatePhoneRepository;
import com.dp.meshini.repositories.SendActivationCodeRepository;
import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.request.ActivatePhoneRequest;
import com.dp.meshini.servise.model.request.SendActivationCodeRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class MailActivationViewModel extends AndroidViewModel {


    Lazy<SendActivationCodeRequest>sendActivationCodeRequestLazy=inject(SendActivationCodeRequest.class);
    Lazy<SendActivationCodeRepository>sendActivationCodeRepositoryLazy=inject(SendActivationCodeRepository.class);
    Lazy<ActivatePhoneRequest>activatePhoneRequestLazy=inject(ActivatePhoneRequest.class);
    Lazy<ActivatePhoneRepository>activatePhoneRepositoryLazy=inject(ActivatePhoneRepository.class);
    public MailActivationViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<StringMessageResponse>> sendPhoneCode(String phone){
        SendActivationCodeRequest sendActivationCodeRequest=sendActivationCodeRequestLazy.getValue();
        sendActivationCodeRequest.setLogin(phone);
        return sendActivationCodeRepositoryLazy.getValue().sendActivationCode(sendActivationCodeRequest);
    }

    public LiveData<Response<StringMessageResponse>>checkCode(String code,String phone){
        ActivatePhoneRequest activatePhoneRequest=activatePhoneRequestLazy.getValue();
        activatePhoneRequest.setCode(code);
        activatePhoneRequest.setPhone(phone);
        return activatePhoneRepositoryLazy.getValue().checkActivationCode(activatePhoneRequest);
    }
}
