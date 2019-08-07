package com.dp.meshini.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.dp.meshini.R;
import com.dp.meshini.databinding.FragmentCallGuideBinding;
import com.dp.meshini.servise.model.pojo.CountryCityPojo;
import com.dp.meshini.servise.model.request.CreateTripRequest;
import com.dp.meshini.servise.model.request.GetPackagesRequest;
import com.dp.meshini.servise.model.response.CountryCityResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.DateTimePicker;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.view.activity.AvailableTripsActivity;
import com.dp.meshini.view.activity.DestinationActivity;
import com.dp.meshini.view.adapter.SpinnerAdapter;
import com.dp.meshini.view.callback.OnDateTimeSelected;
import com.dp.meshini.viewmodel.RequestTripViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import kotlin.Lazy;
import retrofit2.Response;

import static com.dp.meshini.utils.ConstantsFile.Constants.BIKE_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.CAR_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.CAR_TYPE;
import static com.dp.meshini.utils.ConstantsFile.Constants.GOLFCAR_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.GOLFCAR_TYPE;
import static com.dp.meshini.utils.ConstantsFile.Constants.JETSKI_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.JETSKI_TYPE;
import static com.dp.meshini.utils.ConstantsFile.Constants.MOTORBIKE_TYPE;
import static com.dp.meshini.utils.ConstantsFile.Constants.ONFOOT_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.ON_FOOT_TYPE;
import static com.dp.meshini.utils.ConstantsFile.Constants.STEAGECOACH_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.STEAGECOACH_TYPE;
import static com.dp.meshini.utils.ConstantsFile.Constants.TUKTUK_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.TUKTUK_TYPE;
import static com.dp.meshini.utils.ConstantsFile.Constants.VAN_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.VAN_TYPE;
import static com.dp.meshini.utils.ConstantsFile.Constants.YACHAT_ID;
import static com.dp.meshini.utils.ConstantsFile.Constants.YACHAT_TYPE;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class CallGuideFragment extends Fragment implements OnDateTimeSelected, View.OnClickListener {

    FragmentCallGuideBinding binding;
    SpinnerAdapter tCountrySpinnerAdapter;
    SpinnerAdapter languageSpinnerAdapter;
    SpinnerAdapter citiesSpinnerAdapter;
    Spinner spinnerCities;
    String flagSelectDate;
    EditText etFrom;
    EditText etTo;
    Lazy<RequestTripViewModel> requestTripViewModelLazy = inject(RequestTripViewModel.class);
    Lazy<CreateTripRequest> createTripRequestLazy = inject(CreateTripRequest.class);
    Lazy<GetPackagesRequest> packagesRequestLazy = inject(GetPackagesRequest.class);
    CreateTripRequest request = createTripRequestLazy.getValue();
    List<CountryCityPojo> languages;
    List<CountryCityPojo> countries;
    List<CountryCityPojo> cities;
    GetPackagesRequest packagesRequest = packagesRequestLazy.getValue();
    private Context mContext;
    private int selectedTab = -1;
    private ImageView ivCar;
    private ImageView ivVan;
    private ImageView ivGolfCar;
    private ImageView ivStagecoach;
    private ImageView ivYacht;
    private ImageView ivJetSki;
    private ImageView ivMotorbike;
    private ImageView ivOnfoot;
    private ImageView ivTuktuk;
    private Button btSharedTrip;
    private EditText et_no_seats;
    private Button btGuide;
    private boolean sharedTripSelected;
    private RadioGroup radioGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_call_guide, container, false);
        binding.btRequestGuide.setOnClickListener(v -> showDialog());
        countries = new ArrayList<>();
        languages = new ArrayList<>();
        cities = new ArrayList<>();
        sharedTripSelected = false;
        return binding.getRoot();
    }

    public void showDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CustomDialog);
        View v = View.inflate(mContext, R.layout.request_guide_dialog, null);
        builder.setView(v);
        Spinner spinnerLanguage;
        Button button;
        settCountrySpinnerAdapter(v.findViewById(R.id.sp_country));
        etFrom = v.findViewById(R.id.et_from_date);
        etTo = v.findViewById(R.id.et_to_date);
        ivCar = v.findViewById(R.id.iv_car);
        ivVan = v.findViewById(R.id.iv_van);
        ivGolfCar = v.findViewById(R.id.iv_golf_car);
        ivStagecoach = v.findViewById(R.id.iv_stagecoach);
        ivYacht = v.findViewById(R.id.iv_yacht);
        ivJetSki = v.findViewById(R.id.iv_jet_ski);
        ivMotorbike = v.findViewById(R.id.iv_motorbike);
        ivOnfoot = v.findViewById(R.id.iv_onfoot);
        ivTuktuk = v.findViewById(R.id.iv_tuktuk);
        btSharedTrip = v.findViewById(R.id.bt_shared_trip);
        btGuide = v.findViewById(R.id.bt_guide);
        radioGroup = v.findViewById(R.id.radioGroup);
        et_no_seats = v.findViewById(R.id.et_no_seats);
        spinnerLanguage = v.findViewById(R.id.sp_language);
        spinnerCities = v.findViewById(R.id.sp_cities);
        button=v.findViewById(R.id.bt_schedule_trip);
        setLanguageSpinnerAdapter(spinnerLanguage);
        ivCar.setOnClickListener(this);
        ivVan.setOnClickListener(this);
        ivGolfCar.setOnClickListener(this);
        ivStagecoach.setOnClickListener(this);
        ivYacht.setOnClickListener(this);
        ivJetSki.setOnClickListener(this);
        ivMotorbike.setOnClickListener(this);
        ivOnfoot.setOnClickListener(this);
        ivTuktuk.setOnClickListener(this);

        btSharedTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedTripSelected = true;
                ivCar.setVisibility(View.GONE);
                ivVan.setVisibility(View.GONE);
                ivGolfCar.setVisibility(View.GONE);
                ivStagecoach.setVisibility(View.GONE);
                ivYacht.setVisibility(View.GONE);
                ivJetSki.setVisibility(View.GONE);
                ivMotorbike.setVisibility(View.GONE);
                ivOnfoot.setVisibility(View.GONE);
                ivTuktuk.setVisibility(View.GONE);
                etTo.setVisibility(View.GONE);
                spinnerLanguage.setVisibility(View.GONE);
                spinnerCities.setVisibility(View.VISIBLE);
                radioGroup.setVisibility(View.GONE);
                et_no_seats.setVisibility(View.VISIBLE);
                btGuide.setBackgroundResource(R.drawable.guide_button_shape);
                btGuide.setTextColor(Color.BLACK);
                btSharedTrip.setBackgroundResource(R.drawable.shared_trip_button_shape_clicked);
                btSharedTrip.setTextColor(Color.WHITE);
                button.setText(R.string.search_for_trips);
            }
        });

        btGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedTripSelected = false;
                ivCar.setVisibility(View.VISIBLE);
                ivVan.setVisibility(View.VISIBLE);
                ivGolfCar.setVisibility(View.VISIBLE);
                ivStagecoach.setVisibility(View.VISIBLE);
                ivYacht.setVisibility(View.VISIBLE);
                ivJetSki.setVisibility(View.VISIBLE);
                ivMotorbike.setVisibility(View.VISIBLE);
                ivOnfoot.setVisibility(View.VISIBLE);
                ivTuktuk.setVisibility(View.VISIBLE);
                etTo.setVisibility(View.VISIBLE);
                spinnerLanguage.setVisibility(View.VISIBLE);
                spinnerCities.setVisibility(View.GONE);
                radioGroup.setVisibility(View.VISIBLE);
                et_no_seats.setVisibility(View.GONE);
                button.setText(R.string.put_your_trip_schedule);


                btGuide.setBackgroundResource(R.drawable.guide_button_shape_clicked);
                btGuide.setTextColor(Color.WHITE);
                btSharedTrip.setBackgroundResource(R.drawable.shared_trip_button_shape);
                btSharedTrip.setTextColor(Color.BLACK);
            }
        });


        getDate();
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
        btGuide.performClick();
        dialog.show();
    }

    public void settCountrySpinnerAdapter(Spinner tCountrySpinner) {
        tCountrySpinnerAdapter = new SpinnerAdapter(mContext, countries);
        tCountrySpinner.setAdapter(tCountrySpinnerAdapter);
        tCountrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setCountryId(countries.get(position).getId());
                packagesRequest.setCountryId(countries.get(position).getId());
                requestTripViewModelLazy.getValue().getCities(countries.get(position).getId()).observe(CallGuideFragment.this, new Observer<Response<CountryCityResponse>>() {
                    @Override
                    public void onChanged(Response<CountryCityResponse> countryCityResponseResponse) {
                        if (countryCityResponseResponse.isSuccessful()) {
                            cities.addAll(countryCityResponseResponse.body().getCountryCityList());
                            citiesSpinnerAdapter = new SpinnerAdapter(mContext, cities);
                            spinnerCities.setAdapter(citiesSpinnerAdapter);
                            spinnerCities.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    System.out.println("City id : " + (cities.get(position).getId()));
                                    packagesRequest.setCityId(cities.get(position).getId());
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        setVehicleType(v);
    }

    private void setVehicleType(View view) {
        switch (view.getId()) {
            case R.id.iv_car:
                selectedTab = CAR_ID;
                break;
            case R.id.iv_van:
                selectedTab = VAN_ID;
                break;
            case R.id.iv_golf_car:
                selectedTab = GOLFCAR_ID;
                break;
            case R.id.iv_tuktuk:
                selectedTab = TUKTUK_ID;
                break;
            case R.id.iv_stagecoach:
                selectedTab = STEAGECOACH_ID;
                break;
            case R.id.iv_yacht:
                selectedTab = YACHAT_ID;
                break;
            case R.id.iv_jet_ski:
                selectedTab = JETSKI_ID;
                break;
            case R.id.iv_motorbike:
                selectedTab = BIKE_ID;
                break;
            case R.id.iv_onfoot:
                selectedTab = ONFOOT_ID;
                break;

        }
        handleImageResource();
        setVehicleTypToRegister();
    }

    private void handleImageResource() {
        ivCar.setImageResource(selectedTab == CAR_ID ? R.drawable.car_black : R.drawable.car_white);
        ivVan.setImageResource(selectedTab == VAN_ID ? R.drawable.van_black : R.drawable.van_white);
        ivGolfCar.setImageResource(selectedTab == GOLFCAR_ID ? R.drawable.golf_car_black : R.drawable.golf_car_white);
        ivTuktuk.setImageResource(selectedTab == TUKTUK_ID ? R.drawable.tuktuk_black : R.drawable.tuktuk_white);
        ivStagecoach.setImageResource(selectedTab == STEAGECOACH_ID ? R.drawable.stagecoach_black : R.drawable.stagecoach_white);
        ivYacht.setImageResource(selectedTab == YACHAT_ID ? R.drawable.boat_black : R.drawable.boat_white);
        ivJetSki.setImageResource(selectedTab == JETSKI_ID ? R.drawable.jet_ski_black : R.drawable.jet_ski_white);
        ivMotorbike.setImageResource(selectedTab == BIKE_ID ? R.drawable.motorcycle_black : R.drawable.motorcycle_white);
        ivOnfoot.setImageResource(selectedTab == ONFOOT_ID ? R.drawable.trekking_black : R.drawable.onfoot_white);
    }


    private void setVehicleTypToRegister() {
        switch (selectedTab) {
            case CAR_ID:
                request.setVehicleType(CAR_TYPE);
                break;
            case VAN_ID:
                request.setVehicleType(VAN_TYPE);
                break;
            case GOLFCAR_ID:
                request.setVehicleType(GOLFCAR_TYPE);
                break;
            case TUKTUK_ID:
                request.setVehicleType(TUKTUK_TYPE);
                break;
            case STEAGECOACH_ID:
                request.setVehicleType(STEAGECOACH_TYPE);
                break;
            case YACHAT_ID:
                request.setVehicleType(YACHAT_TYPE);
                break;
            case JETSKI_ID:
                request.setVehicleType(JETSKI_TYPE);
                break;
            case BIKE_ID:
                request.setVehicleType(MOTORBIKE_TYPE);
                break;
            case ONFOOT_ID:
                request.setVehicleType(ON_FOOT_TYPE);
                break;
        }
    }

    public void setLanguageSpinnerAdapter(Spinner languageSpinner) {
        languageSpinnerAdapter = new SpinnerAdapter(mContext, languages);
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

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    public void getDate() {
        etFrom.setOnClickListener(v -> {
            flagSelectDate = "from";
            DateTimePicker.getInstance().showDatePickerDialog(mContext, this);
        });

        etTo.setOnClickListener(v -> {
            flagSelectDate = "to";
            DateTimePicker.getInstance().showDatePickerDialog(mContext, this);
        });
    }


    @Override
    public void onDateReady(String date) {
        if (flagSelectDate.equals("from")) {
            etFrom.setText(date);
            request.setStartDate(date);
            packagesRequest.setStartDate(date);
        } else {
            etTo.setText(date);
            request.setEndDate(date);
        }
    }

    @Override
    public void onTimeReady(String time) {

    }

    public void tripSchedule(View view) {
        if (sharedTripSelected) {
            Intent intent = new Intent(mContext, AvailableTripsActivity.class);
            intent.putExtra(ConstantsFile.IntentConstants.SHARED_TRIP, packagesRequest);
            startActivity(intent);
        } else {
            if (ValidationUtils.isEmpty(etFrom.getText().toString()) ||
                    ValidationUtils.isEmpty(etTo.getText().toString())) {
                Snackbar.make(view, getString(R.string.select_date_time_error_message), Snackbar.LENGTH_LONG).show();
            } else if (request.getVehicleType() == null) {

                Snackbar.make(view, getString(R.string.select_vehicle_error_message), Snackbar.LENGTH_LONG).show();
            } else {
                Intent intent = new Intent(mContext, DestinationActivity.class);
                intent.putExtra(ConstantsFile.IntentConstants.CREATE_TRIP_REQUEST, request);
                startActivity(intent);
            }
        }
    }

    public void loadData(Context context) {
        ProgressDialogUtils.getInstance().showProgressDialog((Activity) context);
        requestTripViewModelLazy.getValue().getTCountries().observe(this, countryCityResponseResponse -> {
            if (countryCityResponseResponse.isSuccessful()) {
                countries.addAll(countryCityResponseResponse.body().getCountryCityList());
            } else {
                Snackbar.make(binding.getRoot(), getString(R.string.authentication_error), Snackbar.LENGTH_LONG).show();
            }
        });

        requestTripViewModelLazy.getValue().getLanguages().observe(this, countryCityResponseResponse -> {
            if (countryCityResponseResponse.isSuccessful()) {
                languages.addAll(countryCityResponseResponse.body().getCountryCityList());
            } else {
                Snackbar.make(binding.getRoot(), getString(R.string.authentication_error), Snackbar.LENGTH_LONG).show();
            }
            ProgressDialogUtils.getInstance().cancelDialog();
        });
    }
}
