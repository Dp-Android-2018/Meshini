package com.dp.meshini.koin

import android.content.Context
import com.dp.meshini.servise.endpoint.EndPoints
import com.dp.meshini.servise.model.response.DefaultResponse
import com.dp.meshini.servise.model.response.LoginRegisterResponse
import com.dp.meshini.utils.ConstantsFile
import com.google.gson.JsonObject
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


@JvmField
val NetworkModule = module {
    single { provideOkHttpClient(androidApplication()) }
    single { provideRetrofit(androidApplication(), get()) }
    single { getRerofitEndPoint(get()) }
}


internal fun provideOkHttpClient(context: Context): OkHttpClient {
    val okHttpClient = OkHttpClient.Builder()
    okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
    okHttpClient.addInterceptor { chain ->
        val original = chain.request()

        // Request customization: add request headers
        val requestBuilder = original.newBuilder()
        //requestBuilder.header("Accept-Language", CustomUtils.getInstance().getAppLanguage(MyApp.getInstance()));
        requestBuilder.header("x-api-key", ConstantsFile.Constants.API_KEY)
        requestBuilder.header("Content-Type", ConstantsFile.Constants.CONTENT_TYPE)
        requestBuilder.header("Accept", ConstantsFile.Constants.CONTENT_TYPE)
        //requestBuilder.head("Authorization", );
        // <-- this is the important line

        val request = requestBuilder.build()
        val response = chain.proceed(request)
        if (response.code() == 404 || response.code() == 401) {
            EventBus.getDefault().post(response.message())
            println("Message Event : "+(response.message()))
        }
        response
    }

    return okHttpClient.build()
}

fun provideRetrofit(context: Context, okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .baseUrl(ConstantsFile.Urls.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
}

internal fun getRerofitEndPoint(retrofit: Retrofit): EndPoints {
    return retrofit.create(EndPoints::class.java)
}