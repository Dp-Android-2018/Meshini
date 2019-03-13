package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.UpdateProfileRepository;
import com.dp.meshini.servise.model.request.UpdateProfileRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ProfileViewModel extends AndroidViewModel {

    Lazy<UpdateProfileRepository>repositoryLazy=inject(UpdateProfileRepository.class);
    public ProfileViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<StringMessageResponse>>updateProfile(UpdateProfileRequest request){
        return repositoryLazy.getValue().updateProfile(request);
    }

}
