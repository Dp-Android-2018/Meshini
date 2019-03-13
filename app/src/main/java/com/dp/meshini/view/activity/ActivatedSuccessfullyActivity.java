package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dp.meshini.R;

import androidx.appcompat.app.AppCompatActivity;

public class ActivatedSuccessfullyActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activatied_successfully);

        new Handler().postDelayed(() -> {
            Intent intent=new Intent(ActivatedSuccessfullyActivity.this,ContainerActivity.class);
            startActivity(intent);
            finishAffinity();
        },2000);
    }
}
