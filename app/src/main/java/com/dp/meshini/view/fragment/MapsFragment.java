package com.dp.meshini.view.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.dp.meshini.R;
import com.dp.meshini.databinding.FragmentMapsBinding;
import com.dp.meshini.servise.model.pojo.ActiveTripDetail;
import com.dp.meshini.servise.model.pojo.ActiveTripFirebase;
import com.dp.meshini.servise.model.pojo.FirebasePlace;
import com.dp.meshini.servise.model.pojo.Place;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.utils.FirebaseDataBase;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.utils.SharedPreferenceHelpers;
import com.dp.meshini.view.adapter.TripScheduleAdapter;
import com.dp.meshini.view.callback.ActiveTripDataCallback;
import com.dp.meshini.viewmodel.ContainerViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.GeoPoint;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.GeoApiContext;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Lazy;

import static com.dp.meshini.utils.ConstantsFile.Constants.MAPVIEW_BUNDLE_KEY;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.ACTIVE_TRIP_FIREBASE;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class MapsFragment extends Fragment implements
        OnMapReadyCallback, ActiveTripDataCallback {

    private GoogleMap mGoogleMap;
    MarkerOptions marker=new MarkerOptions();
    Marker currentMarker = null;
    FragmentMapsBinding mapsBinding;
    TripScheduleAdapter tripScheduleAdapter;
    private ActiveTripDetail activeTripDetail;
    private static final String TAG = "MainActivity";
    private FusedLocationProviderClient mFusedLocationClient;
    private GeoApiContext mGeoApiContext;
    private LatLngBounds mMapBoundary;
    private double lat;
    private double lang;
    String nextDestinationName;
    private Context mContext;
    private double nextDestinationLang;
    private double nextDestinationLat;
    LocationListener locationListener;
    private String vehicleType;
    public static MapsFragment mapsFragment;
    private ActiveTripFirebase activeTripFirebase;
    Lazy<FirebaseDataBase> firebaseDataBaseLazy = inject(FirebaseDataBase.class);
    Lazy<ContainerViewModel> containerViewModelLazy = inject(ContainerViewModel.class);
    Lazy<SharedPreferenceHelpers>sharedPreferenceHelpersLazy=inject(SharedPreferenceHelpers.class);
    Bundle bundle;
    public static boolean active;

    public static MapsFragment newInstance() {
        //if(mapsFragment==null) {
            mapsFragment = new MapsFragment();
        //}
        return mapsFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mapsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false);
        initVariables();
        getActiveTripDetail();
        mapsBinding.ivOpenDestinationList.setOnClickListener(v -> showDestinationListDialog());
        mapsBinding.ivGetCurrentLocation.setOnClickListener(v -> getLastKnownLocation());
        initGoogleMap(savedInstanceState);
        return mapsBinding.getRoot();
    }


    public void initVariables() {
        bundle=getArguments();
        activeTripFirebase= (ActiveTripFirebase) bundle.getSerializable(ACTIVE_TRIP_FIREBASE);
        tripScheduleAdapter = new TripScheduleAdapter();
        firebaseDataBaseLazy.getValue().setActiveTripDataCallback(this);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(mContext);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                getLastKnownLocation();
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

    }

    public void drawPath(){
//            mGoogleMap.addMarker(new MarkerOptions().position(new LatLng(nextDestinationLat,nextDestinationLang)));
        mGoogleMap.clear();
        MarkerOptions markerWithIconCar = new MarkerOptions().position(new LatLng(lat, lang))
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_marker));
        mGoogleMap.addMarker(markerWithIconCar);
        if(currentMarker!=null){
            currentMarker.remove();
        }
        if(nextDestinationLang==lang&&nextDestinationLat==lat){
        }else {
            marker.position(new LatLng(nextDestinationLat,nextDestinationLang));
        }

                currentMarker=mGoogleMap.addMarker(marker);
            GoogleDirection.withServerKey(getResources().getString(R.string.google_maps_key))
                    .from(new LatLng(lat,lang))
                    .to(new LatLng(nextDestinationLat,nextDestinationLang))
                    .avoid(AvoidType.FERRIES)
                    .avoid(AvoidType.HIGHWAYS)
                    .execute(new DirectionCallback() {
                        @Override
                        public void onDirectionSuccess(Direction direction, String rawBody) {
                            if (direction.isOK()) {
                                // Do something
                                Route route = direction.getRouteList().get(0);
                                Leg leg = route.getLegList().get(0);
                                ArrayList<LatLng> directionPositionList = leg.getDirectionPoint();
                                PolylineOptions polylineOptions = DirectionConverter.createPolyline(mContext, directionPositionList, 5, Color.BLUE);
                                mGoogleMap.addPolyline(polylineOptions);
                                mapsBinding.tvTime.setText(leg.getDuration().getText());
                                mapsBinding.tvDistance.setText(leg.getDistance().getText());
                            } else {
                                System.out.println("Error direction :" + direction.getErrorMessage());
                            }
                        }

                        @Override
                        public void onDirectionFailure(Throwable t) {
                            showSnackbar("Error onDirectionFailure :" + t.getMessage());
                        }
                    });
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(new LatLng(lat,lang));
        builder.include(new LatLng(nextDestinationLat,nextDestinationLang));
        LatLngBounds bounds = builder.build();
        int padding = 200; // offset from edges of the map in pixels
        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);
        mGoogleMap.animateCamera(cu);
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        }
        googleMap.setMyLocationEnabled(true);
        mGoogleMap.setMaxZoomPreference(20);
        getLastKnownLocation();
        //updateData();
    }

    private void getLastKnownLocation() {
        Log.d(TAG, "getLastKnownLocation: called.");
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Location location = task.getResult();
                GeoPoint geoPoint = new GeoPoint(location.getLatitude(), location.getLongitude());
                Log.d(TAG, "onComplete: latitude: " + geoPoint.getLatitude());
                Log.d(TAG, "onComplete: longitude: " + geoPoint.getLongitude());
                lang = geoPoint.getLongitude();
                lat = geoPoint.getLatitude();

//                MarkerOptions markerWithIconCar = new MarkerOptions().position(new LatLng(lat, lang))
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_car_marker));
//                //addMapMarkers(vehicleType==null?CAR:vehicleType);
//                mGoogleMap.addMarker(markerWithIconCar);
                setCameraView();
            }
        });
    }


    private void setCameraView() {

        // Set a boundary to start
        double bottomBoundary = lat - .1;
        double leftBoundary = lang - .1;
        double topBoundary = lat + .1;
        double rightBoundary = lang + .1;

        mMapBoundary = new LatLngBounds(
                new LatLng(bottomBoundary, leftBoundary),
                new LatLng(topBoundary, rightBoundary)
        );

        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mMapBoundary, 0));
        //drawPath();
    }


    private void initGoogleMap(Bundle savedInstanceState) {
        // *** IMPORTANT ***
        // MapView requires that the Bundle you pass contain _ONLY_ MapView SDK
        // objects or sub-Bundles.
        Bundle mapViewBundle = null;
        if (savedInstanceState != null) {
            mapViewBundle = savedInstanceState.getBundle(MAPVIEW_BUNDLE_KEY);
        }

        mapsBinding.map.onCreate(mapViewBundle);

        mapsBinding.map.getMapAsync(this);

        if (mGeoApiContext == null) {
            mGeoApiContext = new GeoApiContext.Builder()
                    .apiKey(getString(R.string.google_maps_api_key))
                    .build();
        }
    }


    public void showDestinationListDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CustomDialog);
        View v = View.inflate(mContext, R.layout.destination_list_dialog, null);
        builder.setView(v);
        ImageView close = v.findViewById(R.id.iv_close);
        RecyclerView rVDestination = v.findViewById(R.id.rv_destinations);
        Dialog dialog = builder.create();
        close.setOnClickListener(v1 -> dialog.dismiss());
        rVDestination.setAdapter(tripScheduleAdapter);
        rVDestination.setLayoutManager(new LinearLayoutManager(mContext));
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        dialog.show();
    }

    public void getActiveTripDetail() {
        ProgressDialogUtils.getInstance().showProgressDialog(mContext);

        containerViewModelLazy.getValue().getTripDetail().observe(this, tripDetailResponseResponse -> {
            ProgressDialogUtils.getInstance().cancelDialog();
            if (tripDetailResponseResponse.isSuccessful()) {
                activeTripDetail = tripDetailResponseResponse.body().getActiveTripDetail();
                vehicleType = activeTripDetail.getVehicleType();
                sharedPreferenceHelpersLazy.getValue().saveGuidImageUrl(activeTripDetail.getServiceProvider().getProfileImageUrl());
                sharedPreferenceHelpersLazy.getValue().saveRequestId(activeTripDetail.getId());
                updateData();
            } else {
                //ErrorResponse errorResponse=(ErrorResponse) stringMessageResponseResponse.errorBody();
                Gson gson = new GsonBuilder().create();
                ErrorResponse errorResponse = new ErrorResponse();
                try {
                    errorResponse = gson.fromJson(tripDetailResponseResponse.errorBody().string(), ErrorResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                for (String string : errorResponse.getErrors()) {
                    showSnackbar(string);
                }
            }
        });
    }


    @Override
    public void onResume() {
        super.onResume();
        mapsBinding.map.onResume();
        //startUserLocationsRunnable(); // update user locations every 'LOCATION_UPDATE_INTERVAL'
    }

    @Override
    public void onStart() {
        super.onStart();
        active=true;
        mapsBinding.map.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        active=false;
        mapsBinding.map.onStop();
    }


    @Override
    public void onPause() {
        mapsBinding.map.onPause();
        //stopLocationUpdates(); // stop updating user locations
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mapsBinding.map.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapsBinding.map.onLowMemory();
    }

    @Override
    public void ActiveTripData(ActiveTripFirebase activeTripFirebase) {
        if(activeTripFirebase!=null) {
            this.activeTripFirebase = activeTripFirebase;
            updateData();
        }else {
            sharedPreferenceHelpersLazy.getValue().saveRequestId(activeTripDetail.getId());
            //showRateDialog();
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext=context;
    }

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void showRateDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext, R.style.CustomDialog);
        View v = View.inflate(mContext, R.layout.rate_dialog, null);
        builder.setView(v);
        builder.setCancelable(false);
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void  updateData(){
        List<Place> places = new ArrayList<>();
        if(activeTripDetail!=null) {
            places = activeTripDetail.getPlaces();
        }
        for(FirebasePlace place:activeTripFirebase.getPlaces()){
            for (int i=0;i<places.size();i++){
                if(places.get(i).getId()==place.getId()){
                    places.get(i).setDone(place.isDone());
                }
                if(activeTripFirebase.getNext_destination()==-1){
                    nextDestinationName=activeTripDetail.getPickupAddress();
                    setNextDestinationLatLang(activeTripDetail.getPickupLat(),activeTripDetail.getPickupLang());
                    break;
                }else if(places.get(i).getId()==activeTripFirebase.getNext_destination()){
                    setNextDestinationLatLang(places.get(i).getLat(),places.get(i).getLang());
                    nextDestinationName=places.get(i).getName();
                    System.out.println("next destination lat : "+nextDestinationLat);
                    System.out.println("next destination lang : "+nextDestinationLang);
                }
            }
        }
        resetRecyclerView(places);
        drawPath();
        if(activeTripDetail!=null)
        activeTripDetail.setPlaces(places);
        mapsBinding.tvDestinationTitle.setText(nextDestinationName);
        mapsBinding.tvNextDestination.setText(getString(R.string.next_destination)+nextDestinationName);
    }
    public void showSnackbar(String message) {
        Snackbar.make(mapsBinding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }

    public void resetRecyclerView(List<Place>places){
        tripScheduleAdapter.setPlaceList(places,true);
        tripScheduleAdapter.notifyDataSetChanged();
    }

    public void setNextDestinationLatLang(double lat,double lang){
        this.nextDestinationLat=lat;
        this.nextDestinationLang=lang;
    }
}
