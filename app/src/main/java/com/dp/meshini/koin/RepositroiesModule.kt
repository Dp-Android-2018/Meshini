package com.dp.meshini.koin

import com.dp.meshini.repositories.*
import com.dp.meshini.servise.model.request.*
import org.koin.dsl.module.module

@JvmField
val DependencyModule = module {

    single { LoginRepository() }
    single { RegisterRepository() }
    single { CountriesRepository() }
    single { CitiesRepository() }
    single { SendActivationCodeRepository() }
    single { ActivatePhoneRepository() }
    single { ResetPasswordRepository() }
    single { ForgetPasswordRepository() }
    single { UpdateProfileRepository() }
    single { ChangePasswordRepository() }
    single { TCountriesRepository() }
    single { LanguagesRepository() }
    single { PlacesRepository() }
    single { CreateTripRepository() }
    single { OffersRepository() }
    single { PendingRequestsRepository() }
    single { AcceptOfferRepository() }
    single { TripDetailRepository() }
    single { DeleteRequestRepository() }
    single { PastUpcomingRequestsRepository() }
    single { ActiveTripRepository() }
    single { ActiveTripDetailRepository() }
    single { CreateCommentRepository() }
    single { ChangeLanguageRepository() }
    single { ChatRepository() }
    single { PackagesRepository() }
    single { PackageDetailRepository() }


    factory { LoginRequest() }
    factory { RegisterRequest() }
    factory { SendActivationCodeRequest() }
    factory { ActivatePhoneRequest() }
    factory { ResetPasswordRequest() }
    factory { ForgetPasswordRequest() }
    factory { UpdateProfileRequest() }
    factory { ChangePasswordRequest() }
    factory { CreateTripRequest() }
    factory { AcceptOfferRequest()}
    factory { CreateCommentRequest() }
    factory { ChangeLanguageRequest() }
    factory { GetPackagesRequest() }

}