package com.dp.meshini.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityForgetPasswordBinding;
import com.dp.meshini.utils.ValidationUtils;
import com.google.android.material.snackbar.Snackbar;

public class ForgetPasswordActivity extends AppCompatActivity {

    ActivityForgetPasswordBinding binding;
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

    }

    public void back(View view){
        finish();
    }
}
