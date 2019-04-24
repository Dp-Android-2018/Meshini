package com.dp.meshini.repositories;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.pojo.Offer;
import com.dp.meshini.servise.model.response.OffersResponse;
import com.dp.meshini.utils.ConstantsFile;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class OffersRepository {

    Lazy<EndPoints> endPointsLazy = inject(EndPoints.class);

    public LiveData<Response<OffersResponse>> getOffers(int tripId,int page) {
        MutableLiveData<Response<OffersResponse>> offers = new MutableLiveData<>();
        endPointsLazy.getValue().getOffers(tripId,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(offersResponseResponse -> {
                    if (offersResponseResponse.code() == ConstantsFile.Constants.SUCCESS_CODE) {
                        offers.setValue(offersResponseResponse);
                    }
                }, Throwable::printStackTrace);
        return offers;
    }
}
