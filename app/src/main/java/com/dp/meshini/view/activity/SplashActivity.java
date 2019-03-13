package com.dp.meshini.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Lazy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dp.meshini.R;
import com.dp.meshini.utils.SharedPreferenceHelpers;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class SplashActivity extends AppCompatActivity {
Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent;
        if(sharedPreferenceHelpersLazy.getValue().getSaveUserObject()!=null){
            if(sharedPreferenceHelpersLazy.getValue().getSaveUserObject().isActivated()){
                intent=new Intent(this,ContainerActivity.class);
            }else {
                intent=new Intent(this,MailActivationActivity.class);
            }

        }else {
            intent=new Intent(this,LoginActivity.class);
        }

        new Handler().postDelayed(() -> {
            startActivity(intent);
            finishAffinity();
        },3000);
    }
}
