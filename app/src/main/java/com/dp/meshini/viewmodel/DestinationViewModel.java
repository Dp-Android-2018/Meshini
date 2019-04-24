package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.CreateTripRepository;
import com.dp.meshini.servise.model.request.CreateTripRequest;
import com.dp.meshini.servise.model.response.CreateTripResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class DestinationViewModel extends AndroidViewModel {

    Lazy<CreateTripRepository>tripRepositoryLazy=inject(CreateTripRepository.class);
    public DestinationViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<CreateTripResponse>>createTrip(CreateTripRequest request){
        return tripRepositoryLazy.getValue().createTrip(request);
    }


}
