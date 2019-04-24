package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.dp.meshini.R;
import com.dp.meshini.utils.SharedPreferenceHelpers;

import androidx.appcompat.app.AppCompatActivity;
import kotlin.Lazy;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class HomeActivity extends BaseActivity {

    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void getStart(View view){
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
        startActivity(intent);
        finishAffinity();

    }
}
