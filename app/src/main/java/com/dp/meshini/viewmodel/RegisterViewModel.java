package com.dp.meshini.viewmodel;

import android.app.Application;

import com.dp.meshini.repositories.CitiesRepository;
import com.dp.meshini.repositories.CountriesRepository;
import com.dp.meshini.repositories.RegisterRepository;
import com.dp.meshini.servise.model.pojo.CountryCityPojo;
import com.dp.meshini.servise.model.request.RegisterRequest;
import com.dp.meshini.servise.model.response.CountryCityResponse;
import com.dp.meshini.servise.model.response.LoginRegisterResponse;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class RegisterViewModel extends AndroidViewModel {

    Lazy<RegisterRepository> registerRepositoryLazy=inject(RegisterRepository.class);
    Lazy<CountriesRepository>countriesRepositoryLazy=inject(CountriesRepository.class);
    Lazy<CitiesRepository>citiesRepositoryLazy=inject(CitiesRepository.class);
    public RegisterViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Response<LoginRegisterResponse>>register(RegisterRequest registerRequest){
        return registerRepositoryLazy.getValue().register(registerRequest);
    }

    public LiveData<List<CountryCityPojo>>getCountries(){
        return countriesRepositoryLazy.getValue().getCountries();
    }

    public LiveData<Response<CountryCityResponse>>getCities(int countryId){
        return citiesRepositoryLazy.getValue().getCities(countryId);
    }


}
