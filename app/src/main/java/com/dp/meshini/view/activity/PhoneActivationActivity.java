package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityPhoneActivationBinding;
import com.dp.meshini.servise.model.pojo.ClientData;
import com.dp.meshini.servise.model.response.ForgetPasswordResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.viewmodel.PhoneActivationViewModel;
import com.google.android.material.snackbar.Snackbar;

import net.yslibrary.android.keyboardvisibilityevent.util.UIUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PhoneActivationActivity extends AppCompatActivity {

    ActivityPhoneActivationBinding binding;
    String phone;
    String source;
    Lazy<PhoneActivationViewModel>phoneActivationViewModelLazy=inject(PhoneActivationViewModel.class);
    Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        phone=getIntent().getStringExtra(ConstantsFile.IntentConstants.CLIENT_PHONE);
        source=getIntent().getStringExtra(ConstantsFile.IntentConstants.SOURCE_ACTIVITY);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_phone_activation);
        binding.tvPhone.setText(phone);
        binding.pinView.setPinViewEventListener((pinview, b) -> {
            String code=pinview.getValue();
            UIUtil.hideKeyboard(PhoneActivationActivity.this);
            checkCode(code);
        });
    }

    public void checkCode(String code){
        if(source.equals(ConstantsFile.Constants.MAIL_ACTIVATION_ACTIVITY)) {
            phoneActivationViewModelLazy.getValue().checkCode(code, phone).observe(PhoneActivationActivity.this, stringMessageResponseResponse -> {
                switch (stringMessageResponseResponse.code()) {
                    case ConstantsFile.Constants.SUCCESS_CODE:{
                        ClientData clientData=sharedPreferenceHelpersLazy.getValue().getSaveUserObject();
                        clientData.setActivated(true);
                        sharedPreferenceHelpersLazy.getValue().saveDataToPrefs(clientData);
                        showSnackbar(getString(R.string.account_activated_successfully_message));
                        new Handler().postDelayed(() ->{
                            Intent intent = new Intent(PhoneActivationActivity.this, ActivatedSuccessfullyActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        },Snackbar.LENGTH_LONG);
                        break;
                    }
                    case ConstantsFile.Constants.UNAUTH0RIZED_CODE: {
                        showSnackbar(getString(R.string.invalid_code_message));
                        break;
                    }
                }
            });
        }else if (source.equals(ConstantsFile.Constants.FORGET_PASSWORD_ACTIVITY)){
            phoneActivationViewModelLazy.getValue().forgetPassword(code,phone).observe(this, new Observer<Response<ForgetPasswordResponse>>() {
                @Override
                public void onChanged(Response<ForgetPasswordResponse> forgetPasswordResponseResponse) {
                    if(forgetPasswordResponseResponse.isSuccessful()){
                        String token;
                        token = forgetPasswordResponseResponse.body().getToken();
                        Intent intent= new Intent(PhoneActivationActivity.this, ResetPasswordActivity.class);
                        intent.putExtra(ConstantsFile.IntentConstants.CLIENT_PHONE,phone);
                        System.out.println("toke in phone activation : "+token);
                        intent.putExtra(ConstantsFile.IntentConstants.TOKEN,token);
                        startActivity(intent);
                        finishAffinity();
                    }else {
                        showSnackbar(getString(R.string.invalid_code_message));
                    }
                }
            });
        }
    }

    public void resendActivationCode(View view){
        phoneActivationViewModelLazy.getValue().sendPhoneCode(phone).observe(this, stringMessageResponseResponse -> {
            switch (stringMessageResponseResponse.code()) {
                case ConstantsFile.Constants.SUCCESS_CODE: {
                    showSnackbar(getString(R.string.activated_code_sent_successfully_message));
                    break;
                }
                case ConstantsFile.Constants.TRY_LATER:{
                    JSONObject jObjError = null;
                    try {
                        jObjError = new JSONObject(stringMessageResponseResponse.errorBody().string());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String error= null;
                    try {
                        error = String.valueOf(jObjError.getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    showSnackbar(error);
                }
            }
        });
    }

    public void showSnackbar(String message){
        Snackbar.make(binding.clRoot,message,Snackbar.LENGTH_LONG).show();
    }

    public void back(View view){
        finish();
    }
}
