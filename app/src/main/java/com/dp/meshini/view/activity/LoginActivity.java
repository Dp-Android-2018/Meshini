package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityLoginBinding;
import com.dp.meshini.servise.model.request.LoginRequest;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.viewmodel.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
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

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void getDataFromView(View view) {
        String phoneOrMail = binding.etMailPhone.getText().toString();
        String password = binding.etPassword.getText().toString();

        validateData(phoneOrMail, password);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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
        loginViewModelLazy.getValue().loginResponse(loginRequest).observe(this, loginRegisterResponseResponse -> {
            switch (loginRegisterResponseResponse.code()) {
                case ConstantsFile.Constants.SUCCESS_CODE: {
                    // TODO: 3/9/2019 save response to sharedpref
                    sharedPreferenceHelpersLazy.getValue().saveDataToPrefs(loginRegisterResponseResponse.body().getClientData());
                    if(loginRegisterResponseResponse.body().getClientData().isActivated()) {
                        Intent intent = new Intent(this, ContainerActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }else {
                        Intent intent = new Intent(this, MailActivationActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }
                    break;
                }
                case ConstantsFile.Constants.INVALID_DATA_CODE: {
                    String errorMessage = "";
                   // errorMessage.join("\n", loginRegisterResponseResponse.body().getErrors());
                    //loginRegisterResponseResponse.body().getErrors();
                    showSnackbar(errorMessage);
                    break;
                }
//                case ConstantsFile.Constants.UNAUTHENTICATED_CODE:{
//
//                }
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
