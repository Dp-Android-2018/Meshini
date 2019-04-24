package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.ActiveTripDetailRepository;
import com.dp.meshini.repositories.ActiveTripRepository;
import com.dp.meshini.repositories.CreateCommentRepository;
import com.dp.meshini.repositories.TripDetailRepository;
import com.dp.meshini.servise.model.request.CreateCommentRequest;
import com.dp.meshini.servise.model.response.ActiveTripDetailResponse;
import com.dp.meshini.servise.model.response.ActiveTripResponse;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.servise.model.response.TripDetailResponse;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ContainerViewModel extends AndroidViewModel {

    Lazy<ActiveTripDetailRepository>tripDetailRepositoryLazy=inject(ActiveTripDetailRepository.class);
    Lazy<CreateCommentRepository> createCommentRepositoryLazy=inject(CreateCommentRepository.class);
    public ContainerViewModel(@NonNull Application application) {
        super(application);
    }


    public LiveData<Response<ActiveTripDetailResponse>>getTripDetail(){
        return tripDetailRepositoryLazy.getValue().activeTripDetail();
    }


    public LiveData<Response<StringMessageResponse>>createComment(CreateCommentRequest request,int requestId){
        return createCommentRepositoryLazy.getValue().createComment(request,requestId);
    }

}
