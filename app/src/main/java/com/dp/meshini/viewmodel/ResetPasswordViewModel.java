package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.ResetPasswordRepository;
import com.dp.meshini.servise.model.request.ResetPasswordRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ResetPasswordViewModel extends AndroidViewModel {

    Lazy<ResetPasswordRepository>resetPasswordRepositoryLazy=inject(ResetPasswordRepository.class);

    public ResetPasswordViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Response<StringMessageResponse>>resetPasswprd(ResetPasswordRequest request){
        return resetPasswordRepositoryLazy.getValue().resetPassword(request);

    }
}
