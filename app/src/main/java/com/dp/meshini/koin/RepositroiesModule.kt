package com.dp.meshini.koin

import com.dp.meshini.repositories.CitiesRepository
import com.dp.meshini.repositories.CountriesRepository
import com.dp.meshini.repositories.LoginRepository
import com.dp.meshini.repositories.RegisterRepository
import com.dp.meshini.servise.model.request.LoginRequest
import com.dp.meshini.servise.model.request.RegisterRequest
import org.koin.dsl.module.module

@JvmField
val DependencyModule = module {

    single { LoginRepository() }
    single { RegisterRepository() }
    single { CountriesRepository() }
    single { CitiesRepository() }
    factory { LoginRequest() }
    factory { RegisterRequest() }

}