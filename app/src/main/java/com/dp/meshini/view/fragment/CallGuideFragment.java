package com.dp.meshini.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.dp.meshini.R;
import com.dp.meshini.databinding.FragmentCallGuideBinding;
import com.dp.meshini.servise.model.pojo.CountryCityPojo;
import com.dp.meshini.servise.model.request.CreateTripRequest;
import com.dp.meshini.servise.model.response.CountryCityResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.DateTimePicker;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.view.activity.DestinationActivity;
import com.dp.meshini.view.adapter.SpinnerAdapter;
import com.dp.meshini.view.callback.OnDateTimeSelected;
import com.dp.meshini.viewmodel.RequestTripViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.get;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class CallGuideFragment extends Fragment implements OnDateTimeSelected {

    FragmentCallGuideBinding binding;
    SpinnerAdapter tCountrySpinnerAdapter;
    SpinnerAdapter languageSpinnerAdapter;
    String flagSelectDate;
    EditText etFrom;
    EditText etTo;
    Lazy<RequestTripViewModel> requestTripViewModelLazy = inject(RequestTripViewModel.class);
    Lazy<CreateTripRequest> createTripRequestLazy = inject(CreateTripRequest.class);
    CreateTripRequest request = createTripRequestLazy.getValue();
    List<CountryCityPojo>languages;
    List<CountryCityPojo>countries;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_call_guide, container, false);
        binding.btRequestGuide.setOnClickListener(v -> showDialog());
        countries=new ArrayList<>();
        languages=new ArrayList<>();
        return binding.getRoot();
    }

    public void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.CustomDialog);
        View v = View.inflate(getContext(), R.layout.request_guide_dialog, null);
        builder.setView(v);
        settCountrySpinnerAdapter(v.findViewById(R.id.sp_country));
        setLanguageSpinnerAdapter(v.findViewById(R.id.sp_language));
        etFrom = v.findViewById(R.id.et_from_date);
        etTo = v.findViewById(R.id.et_to_date);
        getDate();
        request.setVehicleType(ConstantsFile.Constants.ONFOOT);
        BottomNavigationView bottomBar = v.findViewById(R.id.bottomBar);
        bottomBar.setOnNavigationItemSelectedListener(item -> {
            System.out.println("tabid: " + item.getItemId());
            switch (item.getItemId()) {

                case R.id.trekking: {
                    request.setVehicleType(ConstantsFile.Constants.ONFOOT);
                    break;
                }
                case R.id.car: {
                    request.setVehicleType(ConstantsFile.Constants.CAR);
                    break;
                }
                case R.id.motorbike: {
                    request.setVehicleType(ConstantsFile.Constants.MOTORCYCLE);
                    break;
                }
            }
            return true;
        });
        if (((RadioButton) v.findViewById(R.id.rbt_cash)).isChecked()) {
            request.setPaymentMethod(ConstantsFile.Constants.CASH);
        } else if (((RadioButton) v.findViewById(R.id.rbt_visa)).isChecked()) {
            request.setPaymentMethod(ConstantsFile.Constants.VISA);
        }
        v.findViewById(R.id.bt_schedule_trip).setOnClickListener(v1 -> tripSchedule(v));
        builder.setCancelable(true);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        dialog.show();
    }

    public void settCountrySpinnerAdapter(Spinner tCountrySpinner) {
        tCountrySpinnerAdapter = new SpinnerAdapter(getContext(), countries);
        tCountrySpinner.setAdapter(tCountrySpinnerAdapter);
        tCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setCountryId(countries.get(position).getId());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setLanguageSpinnerAdapter(Spinner languageSpinner) {
        languageSpinnerAdapter = new SpinnerAdapter(getContext(), languages);
        languageSpinner.setAdapter(languageSpinnerAdapter);
        languageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setLanguageId(languages.get(position).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void getDate() {
        etFrom.setOnClickListener(v -> {
            flagSelectDate = "from";
            DateTimePicker.getInstance().showDatePickerDialog(getContext(), this);
        });

        etTo.setOnClickListener(v -> {
            flagSelectDate = "to";
            DateTimePicker.getInstance().showDatePickerDialog(getContext(), this);
        });
    }


    @Override
    public void onDateReady(String date) {
        if (flagSelectDate.equals("from")) {
            etFrom.setText(date);
            request.setStartDate(date);
        } else {
            etTo.setText(date);
            request.setEndDate(date);
        }
    }

    @Override
    public void onTimeReady(String time) {

    }

    public void tripSchedule(View view) {
        if (ValidationUtils.isEmpty(etFrom.getText().toString()) ||
                ValidationUtils.isEmpty(etTo.getText().toString())) {
            Snackbar.make(view, getString(R.string.select_date_time_error_message), Snackbar.LENGTH_LONG).show();
        } else {
            Intent intent = new Intent(getContext(), DestinationActivity.class);
            intent.putExtra(ConstantsFile.IntentConstants.CREATE_TRIP_REQUEST, request);
            startActivity(intent);
        }
    }

    public void loadData(Context context) {
        ProgressDialogUtils.getInstance().showProgressDialog((Activity) context);
        requestTripViewModelLazy.getValue().getTCountries().observe(this, countryCityResponseResponse -> {
            if(countryCityResponseResponse.isSuccessful()){
                countries.addAll(countryCityResponseResponse.body().getCountryCityList());
            }else {
                Snackbar.make(binding.getRoot(),getString(R.string.authentication_error),Snackbar.LENGTH_LONG).show();
            }
        });

        requestTripViewModelLazy.getValue().getLanguages().observe(this, countryCityResponseResponse -> {
            if (countryCityResponseResponse.isSuccessful()) {
                languages.addAll(countryCityResponseResponse.body().getCountryCityList());
            }else {
                Snackbar.make(binding.getRoot(),getString(R.string.authentication_error),Snackbar.LENGTH_LONG).show();
            }
            ProgressDialogUtils.getInstance().cancelDialog();
        });
    }
}
