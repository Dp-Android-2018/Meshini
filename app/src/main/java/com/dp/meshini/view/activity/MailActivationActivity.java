package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityMailActivationBinding;
import com.dp.meshini.servise.model.pojo.ClientData;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.viewmodel.MailActivationViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class MailActivationActivity extends AppCompatActivity {

    ActivityMailActivationBinding binding;
    ClientData clientData;
    Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    Lazy<MailActivationViewModel>mailActivationViewModelLazy=inject(MailActivationViewModel.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_mail_activation);
        clientData=sharedPreferenceHelpersLazy.getValue().getSaveUserObject();
        binding.tvMail.setText(clientData.getEmail());
    }

    public void mobileActivation(View view){
        mailActivationViewModelLazy.getValue().sendPhoneCode(clientData.getPhone()).observe(this, stringMessageResponseResponse -> {
            if(stringMessageResponseResponse.code()== ConstantsFile.Constants.SUCCESS_CODE){
                Intent intent=new Intent(MailActivationActivity.this,PhoneActivationActivity.class);
                intent.putExtra(ConstantsFile.IntentConstants.CLIENT_PHONE,clientData.getPhone());
                intent.putExtra(ConstantsFile.IntentConstants.SOURCE_ACTIVITY, ConstantsFile.Constants.MAIL_ACTIVATION_ACTIVITY);
                startActivity(intent);
            }
        });
    }
}
