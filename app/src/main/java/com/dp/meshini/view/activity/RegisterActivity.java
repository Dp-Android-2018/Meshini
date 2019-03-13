package com.dp.meshini.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityRegisterBinding;
import com.dp.meshini.servise.model.pojo.CountryCityPojo;
import com.dp.meshini.servise.model.request.RegisterRequest;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.view.adapter.SpinnerAdapter;
import com.dp.meshini.viewmodel.RegisterViewModel;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import kotlin.Lazy;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class RegisterActivity extends AppCompatActivity {

    ActivityRegisterBinding binding;
    Lazy<RegisterViewModel> registerViewModelLazy = inject(RegisterViewModel.class);
    Lazy<RegisterRequest> registerRequestLazy = inject(RegisterRequest.class);
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);
    SpinnerAdapter countrySpinnerAdapter;
    SpinnerAdapter citySpinnerAdapter;
    RegisterRequest registerRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        registerRequest = registerRequestLazy.getValue();
        setCountrySpinner();


    }

    public void setCountrySpinner() {
        registerViewModelLazy.getValue().getCountries().observe(this, countryCityPojos -> {
            countrySpinnerAdapter = new SpinnerAdapter(RegisterActivity.this, countryCityPojos);
            binding.spCountry.setAdapter(countrySpinnerAdapter);
            binding.spCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CountryCityPojo selectedCountry = (CountryCityPojo) parent.getItemAtPosition(position);
                    setCitySpinner(selectedCountry.getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    showSnackbar("please select country");
                }
            });
        });
    }

    public void setCitySpinner(int countryId) {
        registerViewModelLazy.getValue().getCities(countryId).observe(this, countryCityPojos -> {
            citySpinnerAdapter = new SpinnerAdapter(RegisterActivity.this, countryCityPojos);
            binding.spCity.setAdapter(citySpinnerAdapter);
            binding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    CountryCityPojo selectedCity = (CountryCityPojo) parent.getItemAtPosition(position);
                    registerRequest.setCityId(selectedCity.getId());
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        });
    }

    public void getDataFromView(View view) {
        String firstName = binding.etFirstName.getText().toString();
        String lastName = binding.etLastName.getText().toString();
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        String phone = binding.etPhone.getText().toString();
        if (ValidationUtils.isEmpty(firstName)) {
            showSnackbar(getString(R.string.enter_first_name_error_message));
            return;
        }
        if (ValidationUtils.isEmpty(lastName)) {
            showSnackbar(getString(R.string.enter_last_name_error_message));
            return;
        }
        if (ValidationUtils.isEmpty(email)) {
            showSnackbar(getString(R.string.enter_mail_error_message));
            return;
        }
        if (ValidationUtils.isEmpty(password)) {
            showSnackbar(getString(R.string.enter_password_error_message));
            return;
        }
        if (ValidationUtils.isEmpty(phone)) {
            showSnackbar(getString(R.string.enter_phone_error_message));
            return;
        }
        if (!ValidationUtils.isMail(email)) {
            showSnackbar(getString(R.string.invalid_mail_error_message));
            return;
        }
        if (password.length() < 8) {
            showSnackbar(getString(R.string.password_length_message_error));
            return;
        }

        // TODO: 3/6/2019 check country and city then call view model
        if (registerRequest.getCityId() == 0) {
            showSnackbar(getString(R.string.select_city_error_message));
        }
        registerRequest.setFirstName(firstName);
        registerRequest.setLastName(lastName);
        registerRequest.setEmail(email);
        registerRequest.setPassword(password);
        registerRequest.setPhone(phone);

        registerViewModelLazy.getValue().register(registerRequest).observe(this, loginRegisterResponseResponse -> {
            if (loginRegisterResponseResponse.code() == ConstantsFile.Constants.SUCCESS_CODE) {
                // TODO: 3/10/2019 save data to sharedpref
                sharedPreferenceHelpersLazy.getValue().saveDataToPrefs(loginRegisterResponseResponse.body().getClientData());
                Intent intent = new Intent(RegisterActivity.this, MailActivationActivity.class);
                startActivity(intent);
            }
        });

    }

    public void back(View view) {
        finish();
    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }
}
