package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.CitiesRepository;
import com.dp.meshini.repositories.LanguagesRepository;
import com.dp.meshini.repositories.TCountriesRepository;
import com.dp.meshini.servise.model.pojo.CountryCityPojo;
import com.dp.meshini.servise.model.response.CountryCityResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class RequestTripViewModel extends AndroidViewModel {

    Lazy<TCountriesRepository> tCountriesRepositoryLazy = inject(TCountriesRepository.class);
    Lazy<LanguagesRepository> languagesRepositoryLazy = inject(LanguagesRepository.class);
    Lazy<CitiesRepository>citiesRepositoryLazy=inject(CitiesRepository.class);

    public RequestTripViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<CountryCityResponse>> getTCountries() {
        return tCountriesRepositoryLazy.getValue().getTCountries();
    }

    public LiveData<Response<CountryCityResponse>> getLanguages() {
        return languagesRepositoryLazy.getValue().getLanguages();
    }

    public LiveData<Response<CountryCityResponse>> getCities(int countryId) {
        return citiesRepositoryLazy.getValue().getCities(countryId);
    }




}
