package com.dp.meshini.view.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityContainerBinding;
import com.dp.meshini.repositories.ChangeLanguageRepository;
import com.dp.meshini.servise.model.pojo.ActiveTripFirebase;
import com.dp.meshini.servise.model.pojo.ClientData;
import com.dp.meshini.servise.model.request.CreateCommentRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.FirebaseDataBase;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.view.callback.ActiveTripCallback;
import com.dp.meshini.view.callback.ActiveTripDataCallback;
import com.dp.meshini.view.fragment.CallGuideFragment;
import com.dp.meshini.view.fragment.MapsFragment;
import com.dp.meshini.viewmodel.ContainerViewModel;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;
import java.util.Observable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableBoolean;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;

import de.hdodenhof.circleimageview.CircleImageView;
import kotlin.Lazy;
import retrofit2.Response;

import static com.dp.meshini.utils.ConstantsFile.Constants.ERROR_DIALOG_REQUEST;
import static com.dp.meshini.utils.ConstantsFile.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static com.dp.meshini.utils.ConstantsFile.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.ACTIVE_TRIP_FIREBASE;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.OPEN_ACTIVE_TRIP;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ContainerActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        ActiveTripCallback, ActiveTripDataCallback,
        View.OnClickListener {
    boolean doubleBackToExitPressedOnce = false;
    CallGuideFragment callGuideFragment;
    MapsFragment mapsFragment;
    int activeTripRequestId;
    MenuItem nav_item;
    ObservableBoolean hasActiveTrip;
    ActivityContainerBinding binding;
    Lazy<ChangeLanguageRepository>changeLanguageRepositoryLazy=inject(ChangeLanguageRepository.class);
    Lazy<FirebaseDataBase> firebaseDataBaseLazy = inject(FirebaseDataBase.class);
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);
    Lazy<ContainerViewModel> viewModelLazy = inject(ContainerViewModel.class);
    Lazy<CreateCommentRequest> createCommentRequestLazy = inject(CreateCommentRequest.class);
    CreateCommentRequest createCommentRequest = createCommentRequestLazy.getValue();
    ClientData data = sharedPreferenceHelpersLazy.getValue().getSaveUserObject();
    private boolean mLocationPermissionGranted = false;
    private static final String TAG = "MainActivity";
    Bundle bundle;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_container);
        //Toolbar toolbar = binding.toolbar;
        mapsFragment = MapsFragment.newInstance();
        hasActiveTrip=new ObservableBoolean(false);
        callGuideFragment = new CallGuideFragment();
        callGuideFragment.loadData(ContainerActivity.this);
        bundle = new Bundle();
        showCallGuide();
        setSupportActionBar((Toolbar) binding.toolbar);
        //ProgressDialogUtils.getInstance().showProgressDialog(this);
        Menu menuNav = binding.navView.getMenu();
        nav_item = menuNav.findItem(R.id.active_trip);
        FirebaseDataBase firebaseDataBase = firebaseDataBaseLazy.getValue();
        System.out.println("user id : " + data.getUserId());
        firebaseDataBase.setUserId(data.getUserId(), this);
        firebaseDataBase.setActiveTripDataCallback(this);
        setNavigationDrawer();
    }

    public void showCallGuide() {
        navigationFragments(callGuideFragment);
    }


    public void setNavigationDrawer() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, (Toolbar) binding.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            private float scaleFactor = 6f;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                nav_item.setEnabled(hasActiveTrip.get());
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                binding.layoutRoot.setTranslationX(slideX);
                binding.layoutRoot.setScaleX(1 - (slideOffset / scaleFactor));
                binding.layoutRoot.setScaleY(1 - (slideOffset / scaleFactor));
            }
        };

        toggle.setToolbarNavigationClickListener(view -> binding.drawerLayout.openDrawer(GravityCompat.START));
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.drawerLayout.setDrawerElevation(0f);
        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.menu);
        binding.navView.setNavigationItemSelectedListener(this::onNavigationItemSelected);
        View itemView = binding.navView.getHeaderView(0);
        View account = itemView.findViewById(R.id.v_account);
        CircleImageView profilePic = account.findViewById(R.id.iv_profile_pic);
        TextView name = account.findViewById(R.id.tv_name);
        TextView rate = account.findViewById(R.id.tv_rate);
        System.out.println("photo url : " + data.getProfilePicture());
        Picasso.get().load(data.getProfilePicture()).placeholder(R.mipmap.logo).into(profilePic);
        name.setText(data.getFirstName() + " " + data.getLastName());
        //rate.setText("("+data.get);
        account.setOnClickListener(v -> {
            Intent intent = new Intent(ContainerActivity.this, ProfileActivity.class);
            startActivity(intent);
        });


    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        System.out.println("Item Element Id :" + item.getItemId());
        switch (item.getItemId()) {
            case R.id.your_trip: {
                intent = new Intent(ContainerActivity.this, TripsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.my_requests: {
                intent = new Intent(ContainerActivity.this, PendingRequestsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.active_trip: {
                showMap();
                break;
            }
//            case R.id.payment: {
//                intent = new Intent(ContainerActivity.this, PaymentActivity.class);
//                startActivity(intent);
//                break;
//            }
            case R.id.help: {
                showSnackbar("There is no help");
                break;
            }
            case R.id.language: {
                showLanguageDialog();
                break;
            }
            case R.id.rate: {
                playStore(this);
                break;
            }
            case R.id.logout: {
                logout();
                break;
            }
        }
        binding.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showLanguageDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialog);
        View v = View.inflate(this, R.layout.language_dialog, null);
        builder.setView(v);
        builder.setCancelable(true);
        Button arabic=v.findViewById(R.id.tv_arabic);
        Button english=v.findViewById(R.id.tv_english);
        Button french=v.findViewById(R.id.tv_french);
        if(sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")){
            System.out.println("lang = " +"ar");
            arabic.setEnabled(false);
            arabic.setClickable(false);
        }else if(sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("en")){
            System.out.println("lang = " +"en");
            english.setEnabled(false);
            english.setClickable(false);
        }else if(sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("fr")) {
            System.out.println("lang = " +"fr");
            french.setEnabled(false);
            french.setClickable(false);
        }
        arabic.setOnClickListener(this);
        english.setOnClickListener(this);
        french.setOnClickListener(this);
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    public void logout() {
        String lang = sharedPreferenceHelpersLazy.getValue().getAppLanguage();
        sharedPreferenceHelpersLazy.getValue().clearSharedPref();
        sharedPreferenceHelpersLazy.getValue().saveAppLanguage(lang);
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }

    @Override
    public void onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        showSnackbar(getString(R.string.press_again));
        new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, 2000);
    }

    public void showMap() {
        mapsFragment.setArguments(bundle);
        navigationFragments(mapsFragment);
    }

    public void navigationFragments(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.v_container, fragment);
        fragmentTransaction.commit();
    }

    public boolean isServicesOK() {
        Log.d(TAG, "isServicesOK: checking google services version");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ContainerActivity.this);

        if (available == ConnectionResult.SUCCESS) {
            //everything is fine and the user can make map requests
            Log.d(TAG, "isServicesOK: Google Play Services is working");
            return true;
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            //an error occured but we can resolve it
            Log.d(TAG, "isServicesOK: an error occured but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(ContainerActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();
        } else {
            Toast.makeText(this, "You can't make map requests", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    public boolean isMapsEnabled() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();
            return false;
        }
        return true;
    }

    private void buildAlertMessageNoGps() {
        final android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setMessage("This application requires GPS to work properly, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    Intent enableGpsIntent = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(enableGpsIntent, PERMISSIONS_REQUEST_ENABLE_GPS);
                });
        final android.app.AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: called.");
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ENABLE_GPS: {
                if (mLocationPermissionGranted) {
                    showCallGuide();
                } else {
                    getLocationPermission();
                }
            }
        }
    }


    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
            showCallGuide();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (checkMapServices()) {
            if (mLocationPermissionGranted) {
                if(getIntent()!=null) {
                    if (getIntent().getBooleanExtra(OPEN_ACTIVE_TRIP, false)&&hasActiveTrip.get()) {
                        showMap();
                    }
                }else {
                    showCallGuide();
                }
            } else {
                getLocationPermission();
            }
        }
    }

    private boolean checkMapServices() {
        if (isServicesOK()) {
            if (isMapsEnabled()) {
                return true;
            }
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void hasActiveTrip(boolean activeTrip) {
        System.out.println("avtive trip : " + activeTrip);
        hasActiveTrip.set(activeTrip);
        nav_item.setEnabled(activeTrip);
        if (!activeTrip && sharedPreferenceHelpersLazy.getValue().getRequestId() != 0) {
            showRateDialog();
        }
    }

    @Override
    public void ActiveTripData(ActiveTripFirebase activeTripFirebase) {
        System.out.println("next destination : " + activeTripFirebase.getNext_destination());
        bundle.putSerializable(ACTIVE_TRIP_FIREBASE, activeTripFirebase);
        //sharedPreferenceHelpersLazy.getValue().saveRequestId(activeTripFirebase.getRequest_id());
        activeTripRequestId = activeTripFirebase.getRequest_id();
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showRateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = View.inflate(this, R.layout.rate_dialog, null);
        builder.setView(v);
        builder.setCancelable(false);
        CircleImageView guideImage = v.findViewById(R.id.civ_guide_image);
        RatingBar ratingBar = v.findViewById(R.id.ratingBar2);
        EditText comment = v.findViewById(R.id.et_comment);
        Button makeComment = v.findViewById(R.id.bt_done);
        Picasso.get().load(sharedPreferenceHelpersLazy.getValue().getGuideImage()).placeholder(getDrawable(R.mipmap.logo)).into(guideImage);
        Dialog dialog = builder.create();
        makeComment.setOnClickListener(v1 -> createComment(comment.getText().toString(), ratingBar.getRating(), dialog));
        dialog.show();
    }

    public void createComment(String comment, float rate, Dialog dialog) {
        if (rate == 0) {
            showSnackbar(getString(R.string.make_rate_message));
        } else {
            createCommentRequest.setRate(rate);
            createCommentRequest.setComment(comment);
            int requestId = sharedPreferenceHelpersLazy.getValue().getRequestId();
            viewModelLazy.getValue().createComment(createCommentRequest, requestId).observe(this, stringMessageResponseResponse -> {
                if (stringMessageResponseResponse.isSuccessful()) {
                    sharedPreferenceHelpersLazy.getValue().saveRequestId(0);
                    sharedPreferenceHelpersLazy.getValue().saveGuidImageUrl("");
                    showSnackbar(stringMessageResponseResponse.body().getMessage());
                    dialog.dismiss();
                    dialog.cancel();
                    showCallGuide();
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
    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.drawerLayout, message, Snackbar.LENGTH_LONG).show();
    }

    public void playStore(Context context){
        try {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" +context.getPackageName())));
        } catch (android.content.ActivityNotFoundException anfe) {
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" +context.getPackageName())));
        }
    }

    @Override
    public void onClick(View v) {
        String appLang = null;
        switch (v.getId()){
            case R.id.tv_arabic:{
                appLang="ar";
                sharedPreferenceHelpersLazy.getValue().saveAppLanguage("ar");
                break;
            }
            case R.id.tv_english:{
                appLang="en";
                sharedPreferenceHelpersLazy.getValue().saveAppLanguage("en");
                break;
            }
            case R.id.tv_french:{
                appLang="fr";

                break;
            }
        }

        String finalAppLang = appLang;
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        changeLanguageRepositoryLazy.getValue().changeLanguage(appLang).observe(this, new Observer<Response<StringMessageResponse>>() {
            @Override
            public void onChanged(Response<StringMessageResponse> stringMessageResponseResponse) {
                ProgressDialogUtils.getInstance().cancelDialog();
                if(stringMessageResponseResponse.isSuccessful()) {
                    sharedPreferenceHelpersLazy.getValue().saveAppLanguage(finalAppLang);
                    changeLanguage();
                }
                showSnackbar(stringMessageResponseResponse.body().getMessage());
            }
        });
    }

    public void changeLanguage(){
        Intent intent=new Intent(this,SplashActivity.class);
        startActivity(intent);
        finishAffinity();
    }
}
