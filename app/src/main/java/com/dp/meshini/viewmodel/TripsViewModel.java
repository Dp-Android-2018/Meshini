package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.PastUpcomingRequestsRepository;
import com.dp.meshini.servise.model.pojo.PastUpcomingRequest;
import com.dp.meshini.servise.model.response.PastUpcomingRequestsResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class TripsViewModel extends AndroidViewModel {

    Lazy<PastUpcomingRequestsRepository>requestsRepositoryLazy=inject(PastUpcomingRequestsRepository.class);
    public TripsViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Response<PastUpcomingRequestsResponse>>getPastRequests(int page){
        return requestsRepositoryLazy.getValue().getPastRequests(page);
    }

    public LiveData<Response<PastUpcomingRequestsResponse>>getUpcomingRequests(int page){
        return requestsRepositoryLazy.getValue().getUpcomingRequests(page);
    }
}
