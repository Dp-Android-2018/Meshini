package com.dp.meshini.application;

import android.app.Application;
import android.content.Context;

import com.dp.meshini.utils.ConnectionReceiver;


public class MyApplication extends Application {

    private static MyApplication mInstance;
    private static int selectionFirmOrSpa;
    private static Context context;

    public void onCreate() {
        super.onCreate();
        mInstance = this;
        MyApplication.context = getApplicationContext();
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectionListener(ConnectionReceiver.ConnectionReceiverListener listener) {
        ConnectionReceiver.connectionReceiverListener = listener;
    }

}
