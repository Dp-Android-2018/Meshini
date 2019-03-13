package com.dp.meshini.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import kotlin.Lazy;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityChangePasswordBinding;
import com.dp.meshini.servise.model.request.ChangePasswordRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.viewmodel.ChangePasswordViewModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ChangePasswordActivity extends AppCompatActivity {

    ActivityChangePasswordBinding binding;
    Lazy<ChangePasswordViewModel>viewModelLazy=inject(ChangePasswordViewModel.class);
    Lazy<ChangePasswordRequest>requestLazy=inject(ChangePasswordRequest.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_change_password);
    }

    public void change(View view){
        String oldPassword=binding.etOldPassword.getText().toString();
        String newPassword=binding.etNewPassword.getText().toString();
        String confirmNewPassword=binding.etConfNewPassword.getText().toString();

        if(ValidationUtils.isEmpty(oldPassword)||
            ValidationUtils.isEmpty(newPassword)||
            ValidationUtils.isEmpty(confirmNewPassword)){
            showSnackbar(getString(R.string.no_data_error_message));
            return;
        }
        if(newPassword.length()<8){
            showSnackbar(getString(R.string.password_length_message_error));
            return;
        }
        if (!confirmNewPassword.equals(newPassword)){
            showSnackbar(getString(R.string.password_confirmation_error_message));
            return;
        }

        ChangePasswordRequest request=requestLazy.getValue();
        request.setOldPassword(oldPassword);
        request.setNewPassword(newPassword);
        viewModelLazy.getValue().changePassword(request).observe(this, new Observer<Response<StringMessageResponse>>() {
            @Override
            public void onChanged(Response<StringMessageResponse> stringMessageResponseResponse) {
                if (stringMessageResponseResponse.isSuccessful()){
                    showSnackbar(stringMessageResponseResponse.body().getMessage());
                }else {
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
            }
        });
    }

    public void showSnackbar(String message){
        Snackbar.make(binding.clRoot,message,Snackbar.LENGTH_LONG).show();
    }
}
