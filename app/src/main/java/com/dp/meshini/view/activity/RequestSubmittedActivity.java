package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.dp.meshini.R;
import com.dp.meshini.utils.ConstantsFile;

import androidx.appcompat.app.AppCompatActivity;

public class RequestSubmittedActivity extends AppCompatActivity {

    int tripId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_submitted);
        tripId = getIntent().getIntExtra(ConstantsFile.IntentConstants.TRIP_ID, 0);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RequestSubmittedActivity.this,OffersActivity.class);
                intent.putExtra(ConstantsFile.IntentConstants.TRIP_ID,tripId);
                startActivity(intent);
                finishAffinity();
            }
        }, 2000);
    }
}
