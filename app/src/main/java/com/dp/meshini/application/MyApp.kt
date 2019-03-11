package com.dp.meshini.application

import android.app.Application
import com.dp.meshini.koin.CustomModules
import com.dp.meshini.koin.DependencyModule
import com.dp.meshini.koin.NetworkModule
import com.dp.meshini.koin.ViewModelModule
import com.dp.meshini.utils.ConnectionReceiver
import org.koin.android.ext.android.startKoin

 class MyApp : Application() {
    //private var mInstance: MyApp? = null
    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin(this, listOf(DependencyModule,
                NetworkModule, ViewModelModule, CustomModules))
    }

    companion object {
        lateinit var instance: MyApp
            private set
    }

    fun setConnectionListener(listener: ConnectionReceiver.ConnectionReceiverListener) {
        ConnectionReceiver.connectionReceiverListener = listener
    }
}