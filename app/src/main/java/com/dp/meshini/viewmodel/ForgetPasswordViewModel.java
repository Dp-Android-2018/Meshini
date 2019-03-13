package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.SendActivationCodeRepository;
import com.dp.meshini.servise.model.request.SendActivationCodeRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ForgetPasswordViewModel extends AndroidViewModel {
    Lazy<SendActivationCodeRequest> sendActivationCodeRequestLazy=inject(SendActivationCodeRequest.class);
    Lazy<SendActivationCodeRepository>sendActivationCodeRepositoryLazy=inject(SendActivationCodeRepository.class);

    public ForgetPasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<StringMessageResponse>> sendPhoneCode(String login){
        SendActivationCodeRequest sendActivationCodeRequest=sendActivationCodeRequestLazy.getValue();
        sendActivationCodeRequest.setLogin(login);
        return sendActivationCodeRepositoryLazy.getValue().sendActivationCode(sendActivationCodeRequest);
    }
}
