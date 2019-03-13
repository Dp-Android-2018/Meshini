package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.ChangePasswordRepository;
import com.dp.meshini.servise.model.request.ChangePasswordRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ChangePasswordViewModel extends AndroidViewModel {

    Lazy<ChangePasswordRepository>repositoryLazy=inject(ChangePasswordRepository.class);
    public ChangePasswordViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<StringMessageResponse>> changePassword(ChangePasswordRequest request){
        return repositoryLazy.getValue().changePassword(request);
    }
}
