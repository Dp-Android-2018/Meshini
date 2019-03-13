package com.dp.meshini.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import kotlin.Lazy;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityForgetPasswordBinding;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.viewmodel.ForgetPasswordViewModel;
import com.google.android.material.snackbar.Snackbar;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ForgetPasswordActivity extends AppCompatActivity {

    ActivityForgetPasswordBinding binding;
    Lazy<ForgetPasswordViewModel>forgetPasswordViewModelLazy=inject(ForgetPasswordViewModel.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_forget_password);
    }

    public void getDataFromView(View view){
        String phoneOrMail=binding.etMailPhone.getText().toString();
        if(ValidationUtils.isEmpty(phoneOrMail)){
            Snackbar.make(binding.clRoot,getString(R.string.enter_mail_phone_error_message),Snackbar.LENGTH_LONG).show();
            return;
        }
        // TODO: 3/6/2019 call view model
        forgetPasswordViewModelLazy.getValue().sendPhoneCode(phoneOrMail).observe(this, new Observer<Response<StringMessageResponse>>() {
            @Override
            public void onChanged(Response<StringMessageResponse> stringMessageResponseResponse) {
                switch (stringMessageResponseResponse.code()){
                    case ConstantsFile.Constants.SUCCESS_CODE:{
                        Intent intent=new Intent(ForgetPasswordActivity.this,PhoneActivationActivity.class);
                        intent.putExtra(ConstantsFile.IntentConstants.CLIENT_PHONE,phoneOrMail);
                        intent.putExtra(ConstantsFile.IntentConstants.SOURCE_ACTIVITY,ConstantsFile.Constants.FORGET_PASSWORD_ACTIVITY);
                        startActivity(intent);
                    }
                }
            }
        });

    }

    public void back(View view){
        finish();
    }
}
