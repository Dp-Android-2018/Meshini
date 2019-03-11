package com.dp.meshini.koin

import com.dp.meshini.utils.ConnectionReceiver
import org.koin.dsl.module.module

@JvmField
val CustomModules= module {

    single {ConnectionReceiver()}
}