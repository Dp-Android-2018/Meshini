package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.DeleteRequestRepository;
import com.dp.meshini.repositories.TripDetailRepository;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.servise.model.response.TripDetailResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class UpcomingTripDetailViewModel extends AndroidViewModel {

    Lazy<TripDetailRepository>repositoryLazy=inject(TripDetailRepository.class);
    Lazy<DeleteRequestRepository>deleteRequestRepositoryLazy=inject(DeleteRequestRepository.class);
    public UpcomingTripDetailViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Response<TripDetailResponse>>getTripDetail(int id) {
        return repositoryLazy.getValue().getTripDetail(id);
    }

    public LiveData<Response<StringMessageResponse>>deleteRequest(int id){
        return deleteRequestRepositoryLazy.getValue().deleteRequest(id);
    }

}
