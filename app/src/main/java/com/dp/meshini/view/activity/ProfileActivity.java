package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityProfileBinding;
import com.dp.meshini.servise.model.pojo.ClientData;
import com.dp.meshini.servise.model.request.UpdateProfileRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.viewmodel.ProfileViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ProfileActivity extends BaseActivity {

    ActivityProfileBinding binding;
    Lazy<ProfileViewModel>profileViewModelLazy=inject(ProfileViewModel.class);
    Lazy<UpdateProfileRequest>requestLazy=inject(UpdateProfileRequest.class);
    ClientData clientData;
    Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_profile);
        clientData=sharedPreferenceHelpersLazy.getValue().getSaveUserObject();
        binding.etFirstName.setText(clientData.getFirstName());
        binding.etLastName.setText(clientData.getLastName());
        binding.etEmail.setText(clientData.getEmail());
        binding.etPhone.setText(clientData.getPhone());
    }

    public void save(View view){
        if(ValidationUtils.isEmpty(binding.etFirstName.getText().toString())){
            showSnackbar(getString(R.string.enter_first_name_error_message));
            return;
        }
        if(ValidationUtils.isEmpty(binding.etLastName.getText().toString())){
            showSnackbar(getString(R.string.enter_last_name_error_message));
            return;
        }
        if (ValidationUtils.isEmpty(binding.etPhone.getText().toString())){
            showSnackbar(getString(R.string.enter_phone_error_message));
            return;
        }

        UpdateProfileRequest request=requestLazy.getValue();
        request.setFirstName(binding.etFirstName.getText().toString());
        request.setLastName(binding.etLastName.getText().toString());
        request.setPhone(binding.etPhone.getText().toString());
        profileViewModelLazy.getValue().updateProfile(request).observe(this, new Observer<Response<StringMessageResponse>>() {
            @Override
            public void onChanged(Response<StringMessageResponse> stringMessageResponseResponse) {
                if(stringMessageResponseResponse.isSuccessful()){
                    showSnackbar(stringMessageResponseResponse.body().getMessage());
                    clientData.setFirstName(binding.etFirstName.getText().toString());
                    clientData.setLastName(binding.etLastName.getText().toString());
                    clientData.setPhone(binding.etPhone.getText().toString());
                    sharedPreferenceHelpersLazy.getValue().saveDataToPrefs(clientData);
                    new Handler().postDelayed(() -> {
                        Intent intent=new Intent(ProfileActivity.this,ContainerActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    },Snackbar.LENGTH_LONG);
                }
            }
        });

    }

    public void showSnackbar(String message){
        Snackbar.make(binding.clRoot,message,Snackbar.LENGTH_LONG).show();
    }

    public void changePassword(View view){
        Intent intent=new Intent(this,ChangePasswordActivity.class);
        startActivity(intent);
    }
}
