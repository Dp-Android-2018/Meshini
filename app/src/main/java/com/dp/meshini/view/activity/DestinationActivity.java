package com.dp.meshini.view.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityDestantionBinding;
import com.dp.meshini.servise.model.request.CreateTripRequest;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.utils.DateTimePicker;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.utils.SwipeToDeleteCallback;
import com.dp.meshini.utils.ValidationUtils;
import com.dp.meshini.view.adapter.DestinationAdapter;
import com.dp.meshini.view.callback.OnDateTimeSelected;
import com.dp.meshini.viewmodel.DestinationViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import kotlin.Lazy;

import static com.dp.meshini.utils.ConstantsFile.Constants.START_PLACE_PICKER;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SELECTED_ADDRESS;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SELECTED_LANG;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SELECTED_LAT;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class DestinationActivity extends BaseActivity implements OnDateTimeSelected {
    ActivityDestantionBinding binding;
    DestinationAdapter destantionAdapter;
    CreateTripRequest request;
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);
    Lazy<DestinationViewModel> viewModelLazy = inject(DestinationViewModel.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_destantion);
        setLocality();
        request = (CreateTripRequest) getIntent().getSerializableExtra(ConstantsFile.IntentConstants.CREATE_TRIP_REQUEST);
        System.out.println("request : " + request.getEndDate());
        initRecyclerViewAdapter();
    }

    public void setLocality() {
        if (sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")) {
            binding.ivBack.setRotation(180);
        }
    }

    public void initRecyclerViewAdapter() {
        destantionAdapter = new DestinationAdapter(this,request.getCountryId());
        binding.rvDestination.setNestedScrollingEnabled(false);
        binding.rvDestination.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDestination.setAdapter(destantionAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(destantionAdapter));
        itemTouchHelper.attachToRecyclerView(binding.rvDestination);
    }

    public void updateRecyclerViewSize(View view) {
        destantionAdapter.incrementRecyclerViewSize();
        destantionAdapter.notifyDataSetChanged();
    }


    @SuppressLint("ResourceType")
    public void showNoteDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialog);
        View v = View.inflate(this, R.layout.write_note_dialog, null);
        builder.setView(v);
        builder.setCancelable(true);
        EditText note = v.findViewById(R.id.et_note);
        Button button = v.findViewById(R.id.bt_confirm);
        Dialog dialog = builder.create();
        button.setOnClickListener(v1 -> {
            if (!ValidationUtils.isEmpty(note.getText().toString())) {
                request.setNotes(note.getText().toString());
            }
            dialog.dismiss();
            dialog.cancel();
        });
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        // WindowManager.LayoutParams layoutParams = window.getAttributes();
        //layoutParams.gravity = Gravity.BOTTOM;
        // window.setAttributes(layoutParams);
        dialog.show();
    }


    public void pickupTime(View view) {
        DateTimePicker.getInstance().showTimePickerDialog(DestinationActivity.this, this);
    }

    @Override
    public void onDateReady(String date) {

    }

    @Override
    public void onTimeReady(String time) {
        request.setPickupTime(time);
    }

    public void search(View view) {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivityForResult(intent, START_PLACE_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == START_PLACE_PICKER) {
            if (resultCode == RESULT_OK) {
                request.setPickupLat(data.getDoubleExtra(SELECTED_LAT, 0));
                request.setPickupLong(data.getDoubleExtra(SELECTED_LANG, 0));
                request.setPickupAddress(data.getStringExtra(SELECTED_ADDRESS));
                binding.etPickUpLocation.setText(request.getPickupAddress());
                System.out.println("selected address : " + request.getPickupAddress());
                System.out.println("selected lat : " + request.getPickupLat());
                System.out.println("selected lang : " + request.getPickupLong());
            }
        }
    }

    public void createRequest(View view) {
        System.out.println("size places on request: " + destantionAdapter.getSelectedPlaces().size());
        request.setPlaceIds(destantionAdapter.getSelectedPlaces());
        if(request.getPickupTime()==null){
            showSnackbar(getString(R.string.pickup_time_error_message));
        }else if(request.getPlaceIds().get(0)==0) {
            showSnackbar(getString(R.string.select_place_error_message));
        }else {
            ProgressDialogUtils.getInstance().showProgressDialog(this);
            viewModelLazy.getValue().createTrip(request).observe(this, createTripResponseResponse -> {
                if (createTripResponseResponse.isSuccessful()) {
                    showSnackbar(createTripResponseResponse.body().getMessage());
                    Intent intent = new Intent(DestinationActivity.this, RequestSubmittedActivity.class);
                    intent.putExtra(ConstantsFile.IntentConstants.TRIP_ID, createTripResponseResponse.body().getId());
                    startActivity(intent);
                    finish();
                } else {
                    Gson gson = new GsonBuilder().create();
                    ErrorResponse errorResponse = new ErrorResponse();

                    try {
                        errorResponse = gson.fromJson(createTripResponseResponse.errorBody().string(), ErrorResponse.class);
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
                ProgressDialogUtils.getInstance().cancelDialog();
            });
        }
    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }

    public void back(View view) {
        finish();
    }
}
