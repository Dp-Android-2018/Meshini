package com.dp.meshini.modules;

import android.content.Context;

import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.utils.ConstantsFile;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkModule {

    OkHttpClient provideOkHttpClient() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
        okHttpClient.addInterceptor(chain -> {
            Request original = chain.request();

            // Request customization: add request headers
            Request.Builder requestBuilder = original.newBuilder();
            requestBuilder.header("Accept-Language", CustomUtils.getInstance().getAppLanguage(MyApp.getInstance()));
            requestBuilder.header("x-api-key", ConstantsFile.Constants.API_KEY);
            requestBuilder.header("Content-Type", ConstantsFile.Constants.CONTENT_TYPE);
            requestBuilder.header("Accept", ConstantsFile.Constants.CONTENT_TYPE);
            requestBuilder.head("Authorization", );
            // <-- this is the important line

            Request request = requestBuilder.build();
            return chain.proceed(request);
        });
        OkHttpClient client = okHttpClient.build();
        return client;
    }

    public Retrofit provideRetrofit(Context context, OkHttpClient okHttpClient) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantsFile.Urls.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();
        return retrofit;
    }

    EndPoints getRerofitEndPoint(Retrofit retrofit) {
        return retrofit.create(EndPoints.class);
    }
}
