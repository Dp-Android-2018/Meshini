package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.dp.meshini.application.MyApplication;
import com.dp.meshini.utils.ConnectionReceiver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class BaseActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().setConnectionListener(this);
        checkConnection();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().setConnectionListener(this);
    }

    public void checkConnection() {
        boolean isConnected = ConnectionReceiver.isConnected();
        System.out.println("Internet Connection Status :" + isConnected);
        if (!isConnected) {
            //show a No Internet Alert or Dialog
            Intent I = new Intent(BaseActivity.this, NoInternetConnectionActivity.class);
            startActivity(I);
            finish();
            finishAffinity();
        }
    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        if (!isConnected) {

            //show a No Internet Alert or Dialog
            Intent I = new Intent(BaseActivity.this, NoInternetConnectionActivity.class);
            startActivity(I);
            finish();
            finishAffinity();

        } else {

            Intent I = new Intent(BaseActivity.this, MainActivity.class);
            startActivity(I);
            finish();
            finishAffinity();
            // dismiss the dialog or refresh the activity
        }
    }
}
