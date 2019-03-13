package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.ActivatePhoneRepository;
import com.dp.meshini.repositories.ForgetPasswordRepository;
import com.dp.meshini.repositories.SendActivationCodeRepository;
import com.dp.meshini.servise.model.request.ActivatePhoneRequest;
import com.dp.meshini.servise.model.request.ForgetPasswordRequest;
import com.dp.meshini.servise.model.request.SendActivationCodeRequest;
import com.dp.meshini.servise.model.response.ForgetPasswordResponse;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PhoneActivationViewModel extends AndroidViewModel {
    Lazy<SendActivationCodeRequest> sendActivationCodeRequestLazy=inject(SendActivationCodeRequest.class);
    Lazy<ActivatePhoneRequest>activatePhoneRequestLazy=inject(ActivatePhoneRequest.class);
    Lazy<SendActivationCodeRepository>sendActivationCodeRepositoryLazy=inject(SendActivationCodeRepository.class);
    Lazy<ActivatePhoneRepository>activatePhoneRepositoryLazy=inject(ActivatePhoneRepository.class);
    Lazy<ForgetPasswordRepository>forgetPasswordRepositoryLazy=inject(ForgetPasswordRepository.class);
    Lazy<ForgetPasswordRequest>forgetPasswordRequestLazy=inject(ForgetPasswordRequest.class);
    public PhoneActivationViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<StringMessageResponse>>checkCode(String code,String phone){
        ActivatePhoneRequest activatePhoneRequest=activatePhoneRequestLazy.getValue();
        activatePhoneRequest.setCode(code);
        activatePhoneRequest.setPhone(phone);
        return activatePhoneRepositoryLazy.getValue().checkActivationCode(activatePhoneRequest);
    }

    public LiveData<Response<ForgetPasswordResponse>>forgetPassword(String code,String phone){
        ForgetPasswordRequest forgetPasswordRequest=forgetPasswordRequestLazy.getValue();
        forgetPasswordRequest.setCode(code);
        forgetPasswordRequest.setLogin(phone);
        return forgetPasswordRepositoryLazy.getValue().fogetPassword(forgetPasswordRequest);
    }

    public LiveData<Response<StringMessageResponse>> sendPhoneCode(String phone){
        SendActivationCodeRequest sendActivationCodeRequest=sendActivationCodeRequestLazy.getValue();
        sendActivationCodeRequest.setLogin(phone);
        return sendActivationCodeRepositoryLazy.getValue().sendActivationCode(sendActivationCodeRequest);
    }
}
