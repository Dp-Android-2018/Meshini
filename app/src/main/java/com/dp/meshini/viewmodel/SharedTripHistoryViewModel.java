package com.dp.meshini.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.dp.meshini.repositories.PastUpcomingSharedRepository;
import com.dp.meshini.servise.model.response.PastUpcomingSharedResponse;

import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class SharedTripHistoryViewModel extends AndroidViewModel {

    Lazy<PastUpcomingSharedRepository>repositoryLazy=inject(PastUpcomingSharedRepository.class);
    public SharedTripHistoryViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<PastUpcomingSharedResponse>> getUpcommingTrips(int pageId){
        return repositoryLazy.getValue().getUpcomingSharedHistory(pageId);
    }

    public LiveData<Response<PastUpcomingSharedResponse>> getPastTrips(int pageId){
        return repositoryLazy.getValue().getPastSharedHistory(pageId);
    }

}
