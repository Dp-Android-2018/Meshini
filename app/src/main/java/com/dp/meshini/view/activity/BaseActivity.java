package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.dp.meshini.R;
import com.dp.meshini.application.MyApp;
import com.dp.meshini.databinding.ActivityBaseBinding;
import com.dp.meshini.utils.ConnectionReceiver;
import com.google.android.material.snackbar.Snackbar;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;


public class BaseActivity extends AppCompatActivity implements ConnectionReceiver.ConnectionReceiverListener {

    private ActivityBaseBinding baseBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseBinding = DataBindingUtil.setContentView(this, R.layout.activity_base);
        MyApp.Companion.getInstance().setConnectionListener(this);
        checkConnection();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }


    public void displayMessage(String message) {
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_LONG).show();
        //Snackbar.make(baseBinding.parent, message, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        MyApp.Companion.getInstance().setConnectionListener(this);
       // MyApplication.getInstance().setConnectionListener(this);
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

            Intent I = new Intent(BaseActivity.this, HomeActivity.class);
            startActivity(I);
            finish();
            finishAffinity();
            // dismiss the dialog or refresh the activity
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(String event) {
        displayMessage(event);
    }


    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }
}
