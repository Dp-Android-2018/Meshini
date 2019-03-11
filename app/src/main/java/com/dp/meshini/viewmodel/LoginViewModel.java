package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.LoginRepository;
import com.dp.meshini.servise.model.request.LoginRequest;
import com.dp.meshini.servise.model.response.LoginRegisterResponse;
import com.dp.meshini.view.callback.IoCustomRequest;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class LoginViewModel extends AndroidViewModel {

    Lazy<LoginRepository> loginRepositoryLazy = inject(LoginRepository.class);
    public LoginViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<LoginRegisterResponse>> loginResponse(LoginRequest request){
        return loginRepositoryLazy.getValue().login(request);
    }
}
