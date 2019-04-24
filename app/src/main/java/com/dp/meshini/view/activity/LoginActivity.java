package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityLoginBinding;
import com.dp.meshini.notification.FirebaseToken;
import com.dp.meshini.servise.model.request.LoginRequest;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.viewmodel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import java.io.IOException;

import kotlin.Lazy;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class LoginActivity extends BaseActivity {

    ActivityLoginBinding binding;
    Lazy<LoginViewModel> loginViewModelLazy = inject(LoginViewModel.class);
    Lazy<LoginRequest> loginRequestLazy = inject(LoginRequest.class);
    Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);

    }

    public void getLoginDataFromView(View view) {
        String phoneOrMail = binding.etMailPhone.getText().toString();
        String password = binding.etPassword.getText().toString();
        System.out.println("login : "+phoneOrMail);
        System.out.println("password : "+password);
        validateData(phoneOrMail, password);

    }

    public void validateData(String maile, String password) {
        if (ValidationUtils.isEmpty(maile)) {
            showSnackbar(getString(R.string.enter_mail_phone_error_message));
            return;
        }
        if (ValidationUtils.isEmpty(password)) {
            showSnackbar(getString(R.string.enter_password_error_message));
            return;
        }

        // TODO: 3/6/2019 call view model
        LoginRequest loginRequest = loginRequestLazy.getValue();
        loginRequest.setLogin(maile);
        loginRequest.setPassword(password);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        FirebaseToken.getInstance().getFirebaseToken().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                loginRequest.setDeviceToken(s);
                System.out.println("device token : "+s);
                callRequest(loginRequest);
            }
        });
    }

    public void callRequest(LoginRequest request){
        //ProgressDialogUtils.getInstance().showProgressDialog(this);
        loginViewModelLazy.getValue().loginResponse(request).observe(this, loginRegisterResponseResponse -> {
            ProgressDialogUtils.getInstance().cancelDialog();
            if(loginRegisterResponseResponse.isSuccessful()) {
                sharedPreferenceHelpersLazy.getValue().saveDataToPrefs(loginRegisterResponseResponse.body().getClientData());
                //sharedPreferenceHelpersLazy.getValue().saveAppLanguage("en");
                if (loginRegisterResponseResponse.body().getClientData().isActivated()) {
                    Intent intent = new Intent(this, ContainerActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Intent intent = new Intent(this, MailActivationActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }else {
                Gson gson = new GsonBuilder().create();
                ErrorResponse errorResponse = new ErrorResponse();
                try {
                    errorResponse = gson.fromJson(loginRegisterResponseResponse.errorBody().string(), ErrorResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String error = "";
                for (String string : errorResponse.getErrors()) {
                    error += string;
                    error += "\n";
                }
                showSnackbar(error);
            }
        });
    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }

    public void forgetPassword(View view) {
        Intent intent = new Intent(this, ForgetPasswordActivity.class);
        startActivity(intent);
    }

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
