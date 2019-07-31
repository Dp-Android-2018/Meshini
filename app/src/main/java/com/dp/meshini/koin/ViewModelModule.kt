package com.dp.meshini.koin

import com.dp.meshini.viewmodel.*
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

@JvmField
val ViewModelModule = module {

    viewModel { LoginViewModel(androidApplication()) }
    viewModel { RegisterViewModel(androidApplication()) }
    viewModel { MailActivationViewModel(androidApplication()) }
    viewModel { PhoneActivationViewModel(androidApplication()) }
    viewModel { ForgetPasswordViewModel(androidApplication()) }
    viewModel { ResetPasswordViewModel(androidApplication()) }
    viewModel { ProfileViewModel(androidApplication()) }
    viewModel { ChangePasswordViewModel(androidApplication()) }
    viewModel { RequestTripViewModel(androidApplication()) }
    viewModel { DestinationViewModel(androidApplication()) }
    viewModel { OffersViewModel(androidApplication()) }
    viewModel { PendingRequestsViewModel(androidApplication()) }
    viewModel { UpcomingTripDetailViewModel(androidApplication()) }
    viewModel { TripsViewModel(androidApplication()) }
    viewModel { ContainerViewModel(androidApplication()) }
    viewModel { ChatViewModel(androidApplication()) }
    viewModel { AllPackagesViewModel(androidApplication()) }
    viewModel { PackageDetailViewModel(androidApplication()) }
}