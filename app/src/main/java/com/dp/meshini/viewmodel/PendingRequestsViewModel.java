package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.DeleteRequestRepository;
import com.dp.meshini.repositories.PendingRequestsRepository;
import com.dp.meshini.servise.model.response.PendingRequestsResponse;
import com.dp.meshini.servise.model.response.StringMessageResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PendingRequestsViewModel extends AndroidViewModel {

    Lazy<PendingRequestsRepository>pendingRequestsRepositoryLazy=inject(PendingRequestsRepository.class);
    Lazy<DeleteRequestRepository>deleteRequestRepositoryLazy=inject(DeleteRequestRepository.class);

    public PendingRequestsViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<PendingRequestsResponse>>getPendingRequests(int page){
        return pendingRequestsRepositoryLazy.getValue().getPendingRequests(page);
    }


    public LiveData<Response<StringMessageResponse>>deleteRequest(int id){
        return deleteRequestRepositoryLazy.getValue().deleteRequest(id);
    }
}
