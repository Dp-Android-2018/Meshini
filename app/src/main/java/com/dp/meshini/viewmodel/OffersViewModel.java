package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.OffersRepository;
import com.dp.meshini.servise.model.pojo.Offer;
import com.dp.meshini.servise.model.response.OffersResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class OffersViewModel extends AndroidViewModel {

    Lazy<OffersRepository>offersRepositoryLazy=inject(OffersRepository.class);

    public OffersViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<OffersResponse>>getOffers(int tripId,int page){
        return offersRepositoryLazy.getValue().getOffers(tripId,page);
    }
}

