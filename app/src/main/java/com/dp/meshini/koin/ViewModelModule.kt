package com.dp.meshini.koin

import com.dp.meshini.viewmodel.LoginViewModel
import com.dp.meshini.viewmodel.RegisterViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

@JvmField
val ViewModelModule = module {

    viewModel { LoginViewModel(androidApplication()) }
    viewModel { RegisterViewModel(androidApplication()) }

}