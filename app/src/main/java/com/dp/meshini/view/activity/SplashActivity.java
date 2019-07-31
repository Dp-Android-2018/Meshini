package com.dp.meshini.view.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;

import com.dp.meshini.R;
import com.dp.meshini.utils.SharedPreferenceHelpers;

import java.util.Locale;

import kotlin.Lazy;

import static com.dp.meshini.utils.ConstantsFile.IntentConstants.OPEN_ACTIVE_TRIP;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIP_ID;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class SplashActivity extends BaseActivity {
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent;
        System.out.println("Lang App shpref : "+sharedPreferenceHelpersLazy.getValue().getAppLanguage());

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra("title") != null) {
                System.out.println("title is : " + getIntent().getStringExtra("title"));
                Intent i = null;
                if (getIntent().getStringExtra("title").equals("offer-received")) {
                    int requestId = Integer.parseInt(getIntent().getStringExtra("request_id"));
//                    ClientData response = sharedPreferenceHelpersLazy.getValue().getSaveUserObject();
//                    response.setActivated(true);
                    //sharedPreferenceHelpersLazy.getValue().saveDataToPrefs(response);
                    i = new Intent(getApplicationContext(), OffersActivity.class);
                    i.putExtra(TRIP_ID, requestId);
                    // Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                } else if (getIntent().getStringExtra("title").equals("trip_finished")) {
                    i = new Intent(getApplicationContext(), SplashActivity.class);
                    //Notify(i, remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                } else if (getIntent().getStringExtra("title").equals("trip-started") ||
                        getIntent().getStringExtra("title").equals("destination-finished") ||
                        getIntent().getStringExtra("title").equals("destination-started")) {
                    i = new Intent(getApplicationContext(), ContainerActivity.class);
                    i.putExtra(OPEN_ACTIVE_TRIP, true);
                }
                startActivity(i);
                finishAffinity();
            }
        }

        if (sharedPreferenceHelpersLazy.getValue().getSaveUserObject() != null) {
            if (sharedPreferenceHelpersLazy.getValue().getSaveUserObject().isActivated()) {
                intent = new Intent(this, ContainerActivity.class);
            } else {
                intent = new Intent(this, MailActivationActivity.class);
            }

        } else {
            intent = new Intent(this, LoginActivity.class);
        }

        new Handler().postDelayed(() -> {
            startActivity(intent);
            finishAffinity();
        }, 3000);

        changeAppLanguage();
    }

    public void changeAppLanguage() {
        String AppLang=sharedPreferenceHelpersLazy.getValue().getAppLanguage();
        System.out.println("Lang App var :" + AppLang);
        Locale locale = new Locale(AppLang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
    }
}
