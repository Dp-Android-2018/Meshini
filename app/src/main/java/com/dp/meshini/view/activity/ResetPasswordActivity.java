package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityChangePasswordBinding;
import com.dp.meshini.databinding.ActivityResetPasswordBinding;
import com.dp.meshini.servise.model.request.ResetPasswordRequest;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.viewmodel.ResetPasswordViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import kotlin.Lazy;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ResetPasswordActivity extends AppCompatActivity {

    ActivityResetPasswordBinding binding;
    Lazy<ResetPasswordRequest> resetPasswordRequestLazy = inject(ResetPasswordRequest.class);
    Lazy<ResetPasswordViewModel> resetPasswordViewModelLazy = inject(ResetPasswordViewModel.class);
    String login;
    String token;
    ResetPasswordRequest resetPasswordRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reset_password);
        login = getIntent().getStringExtra(ConstantsFile.IntentConstants.CLIENT_PHONE);
        token = getIntent().getStringExtra(ConstantsFile.IntentConstants.TOKEN);
        System.out.println("toke is : " + token);
        resetPasswordRequest = resetPasswordRequestLazy.getValue();
        resetPasswordRequest.setLogin(login);
        resetPasswordRequest.setToken(token);
    }

    public void getDataFromView(View view) {
        String password = binding.etPassword.getText().toString();
        String passwordConfirmation = binding.etPasswordConfirmation.getText().toString();
        if (ValidationUtils.isEmpty(password)) {
            showSnackbar(getString(R.string.enter_password_error_message));
            return;
        }
        if (password.length() < 8) {
            showSnackbar(getString(R.string.password_length_message_error));
            return;
        }
        if (!passwordConfirmation.equals(password)) {
            showSnackbar(getString(R.string.password_confirmation_error_message));
            return;
        }

        resetPasswordRequest.setPassword(password);
        resetPasswordViewModelLazy.getValue().resetPasswprd(resetPasswordRequest).observe(this, stringMessageResponseResponse -> {
            if (stringMessageResponseResponse.isSuccessful()) {
                showSnackbar(stringMessageResponseResponse.body().getMessage());
                Intent intent = new Intent(ResetPasswordActivity.this, ContainerActivity.class);
                startActivity(intent);
                finishAffinity();
            } else {
                JSONObject jObjError = null;
                try {
                    jObjError = new JSONObject(stringMessageResponseResponse.errorBody().string());
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String error = null;
                try {
                    error = String.valueOf(jObjError.getString("error"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                showSnackbar(error);
            }

        });

    }


    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }
}
