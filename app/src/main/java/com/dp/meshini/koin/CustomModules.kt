package com.dp.meshini.koin

import com.dp.meshini.utils.ConnectionReceiver
import com.dp.meshini.utils.SharedPrefrence
import com.dp.meshini.utils.SharedPreferenceHelpers
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

@JvmField
val CustomModules= module {

    single {ConnectionReceiver()}
    single { SharedPrefrence(androidContext()) }
    single { SharedPreferenceHelpers() }
}