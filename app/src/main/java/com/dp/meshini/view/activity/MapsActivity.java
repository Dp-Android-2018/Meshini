package com.dp.meshini.view.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.dp.meshini.R;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.compat.Place;
import com.google.android.libraries.places.compat.Places;
import com.google.android.libraries.places.compat.ui.PlaceSelectionListener;
import com.google.android.libraries.places.compat.ui.SupportPlaceAutocompleteFragment;
import com.google.firebase.firestore.GeoPoint;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SELECTED_ADDRESS;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SELECTED_LANG;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SELECTED_LAT;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    double lat;
    double lang;
    String selectedAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        ImageView currentLocation = findViewById(R.id.iv_get_current_location);
        Button selectThisLocation = findViewById(R.id.bt_select_this_location);
        currentLocation.setOnClickListener(v -> getLastKnownLocation());
        selectThisLocation.setOnClickListener(v -> returndata());
        search();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getLastKnownLocation();
        //autoComplete();
    }

    private void getLastKnownLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = mFusedLocationClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                //Write your implemenation here
                Log.d("AndroidClarified", location.getLatitude() + " " + location.getLongitude());
                ProgressDialogUtils.getInstance().cancelDialog();

                //Location location = task.getResult();
                System.out.println("location is : " + location);
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                //Log.d(TAG, "onComplete: latitude: " + geoPoint.getLatitude());
                //Log.d(TAG, "onComplete: longitude: " + geoPoint.getLongitude());
                lang = geoPoint.getLongitude();
                lat = geoPoint.getLatitude();
                setCameraView();
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocation(lat, lang, 1);
                    Address obj = addresses.get(0);

                    selectedAddress = obj.getAddressLine(0);
                    selectedAddress += "\n" + obj.getCountryName();
                    //selectedAddress += "\n" + obj.getCountryCode();
                    selectedAddress += "\n" + obj.getAdminArea();
                    //selectedAddress += "\n" + obj.getPostalCode();
                    //selectedAddress += "\n" + obj.getSubAdminArea();
                    selectedAddress += "\n" + obj.getLocality();
                    //selectedAddress += "\n" + obj.getSubThoroughfare();

                    System.out.println("full address : " + selectedAddress);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

//        mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//
//            }
//        });

    }

    public void returndata() {
        Intent resultIntent = new Intent();
// TODO Add extras or a data URI to this intent as appropriate.
        resultIntent.putExtra(SELECTED_ADDRESS, selectedAddress);
        resultIntent.putExtra(SELECTED_LAT, lat);
        resultIntent.putExtra(SELECTED_LANG, lang);
        setResult(Activity.RESULT_OK, resultIntent);
        finish();
    }

//    public void autoComplete() {
//        Places. (getApplicationContext(), "AIzaSyAZ0yLGIxJ6oP59MMa-4BW-KX2BUnM2oQo");
//
//// Create a new Places client instance.
//        PlacesClient placesClient = Places.createClient(this);
//        AutocompleteSessionToken token = AutocompleteSessionToken.newInstance();
//        // Create a RectangularBounds object.
//        RectangularBounds bounds = RectangularBounds.newInstance(
//                new LatLng(-33.880490, 151.184363),
//                new LatLng(-33.858754, 151.229596));
//        // Use the builder to create a FindAutocompletePredictionsRequest.
//        FindAutocompletePredictionsRequest request = FindAutocompletePredictionsRequest.builder()
//// Call either setLocationBias() OR setLocationRestriction().
//                .setLocationBias(bounds)
//                //.setLocationRestriction(bounds)
//                .setCountry("au")
//                // .setTypeFilter(AutocompleteFilter.TypeFilter.ADDRESS)
//                .setSessionToken(token)
//                //.setQuery(query)
//                .build();
//
//        placesClient.findAutocompletePredictions(request).addOnSuccessListener((response) -> {
//            for (AutocompletePrediction prediction : response.getAutocompletePredictions()) {
//                Log.i("place:", prediction.getPlaceId());
//                Log.i("place :", prediction.getPrimaryText(null).toString());
//            }
//        }).addOnFailureListener((exception) -> {
//            if (exception instanceof ApiException) {
//                ApiException apiException = (ApiException) exception;
//                Log.e("place ", "Place not found: " + apiException.getStatusCode());
//            }
//        });
//    }

    public void search() {
        SupportPlaceAutocompleteFragment autocompleteFragment = (SupportPlaceAutocompleteFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);

// Specify the types of place data to return.
        // autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS));

// Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                lat = place.getLatLng().latitude;
                lang = place.getLatLng().longitude;
                selectedAddress = (String) place.getName();
                setCameraView();
            }

            @Override
            public void onError(Status status) {
                System.out.println("An error occurred: " + status);

            }
        });
//        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
//            @Override
//            public void onPlaceSelected(Place place) {
//                // TODO: Get info about the selected place.
////                System.out.println("place id : "+place.getAddress());
////                System.out.println("place name : "+place.getName());
////                System.out.println("Place address : "+place.getAddress());
////                System.out.println("place lat lang : "+place.getLatLng());
//
//            }
//
//            @Override
//            public void onError(Status status) {
//                // TODO: Handle the error.
//            }
//        });
    }

    public void setCameraView() {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lang)));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lang), 17.0f));
    }
}
