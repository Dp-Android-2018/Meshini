package com.dp.meshini.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityPaymentBinding;
import com.dp.meshini.repositories.PaymentRepository;
import com.dp.meshini.servise.model.pojo.Payment;
import com.dp.meshini.servise.model.request.PaymentRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.dp.meshini.view.adapter.PaymentSpinnerAdapter;
import com.dp.meshini.view.adapter.StringSpinnerAdapter;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.features.ReturnMode;
import com.esafirm.imagepicker.model.Image;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import kotlin.Lazy;
import retrofit2.Response;

import static com.dp.meshini.utils.ConstantsFile.IntentConstants.DOUBLE_PRICE;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.PAY_METHODS;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.ROOMS_TYPE;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SHARED_TRIP_ID;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SINGLE_PRICE;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIPLE_PRICE;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PaymentActivity extends AppCompatActivity {

    double singlePrice;
    double doublePrice;
    double triplePrice;
    String selectedRoom;
    List<Payment>payments;
    List<String>rooms;
    Bundle bundle;
    int packageId;
    private StorageReference mStorageRef;
    ActivityPaymentBinding binding;
    List<String>noPersons;
    PaymentRequest request;
    Lazy<PaymentRepository>repositoryLazy=inject(PaymentRepository.class);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_payment);
        bundle=getIntent().getBundleExtra("bundle");
        payments=new ArrayList<>();
        rooms=new ArrayList<>();
        request=new PaymentRequest();
        noPersons=new ArrayList<>();
        for (int i=1;i<=100;i++){
            noPersons.add(String.valueOf(i));
        }
        request.setPackageId(packageId);
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://meshini-project-1550483518935.appspot.com/meshiniUser");
        payments.addAll(bundle.getParcelableArrayList(PAY_METHODS));
        rooms.addAll(bundle.getStringArrayList(ROOMS_TYPE));
        singlePrice=bundle.getDouble(SINGLE_PRICE);
        doublePrice=bundle.getDouble(DOUBLE_PRICE);
        triplePrice=bundle.getDouble(TRIPLE_PRICE);
        packageId=bundle.getInt(SHARED_TRIP_ID);
        System.out.println("** payment size : "+payments.size());
        System.out.println("** rooms  size : "+rooms.size());
        System.out.println("** package id : "+packageId);


        request.setPackageId(packageId);
        setPaySpinner();
        setRoomsSpinner();
        //setNoPersonSpinner();

    }

    public void setPaySpinner(){
        PaymentSpinnerAdapter adapter=new PaymentSpinnerAdapter(this,payments);
        binding.spPaymentMethod.setAdapter(adapter);
        binding.spPaymentMethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setPaymentType(payments.get(position).getName());
                binding.tvAccNo.setText(payments.get(position).getAccountNumber());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setRoomsSpinner(){
        StringSpinnerAdapter adapter=new StringSpinnerAdapter(this,rooms);
        binding.spRoomType.setAdapter(adapter);
        binding.spRoomType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                request.setRoomType(rooms.get(position));
                selectedRoom=rooms.get(position);
                setNoPersonSpinner();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void setNoPersonSpinner(){
        StringSpinnerAdapter adapter=new StringSpinnerAdapter(this,noPersons);
        binding.spNoPerson.setAdapter(adapter);
        binding.spNoPerson.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int persons=Integer.parseInt(noPersons.get(position));
                request.setNoOfPersons(persons);
                System.out.println("**selected room is : "+selectedRoom);
                switch (selectedRoom){
                    case "single room":{
                        System.out.println("** single price : "+singlePrice);
                        request.setTotalAmounts(singlePrice*persons);
                        break;
                    }case "double room":{
                        System.out.println("** double price : "+doublePrice);
                        request.setTotalAmounts(doublePrice*persons);
                        break;
                    }case "triple room":{
                        System.out.println("** triple price : "+triplePrice);
                        request.setTotalAmounts(triplePrice*persons);
                        break;
                    }

                }
                binding.tvTotal.setText(getString(R.string.total)+request.getTotalAmounts()+getString(R.string.egp));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void uploadImage(View view) {
        ImagePicker.create(this)
                .returnMode(ReturnMode.ALL) // set whether pick and / or camera action should return immediate result or not.
                .folderMode(true) // folder mode (false by default)
                .toolbarFolderTitle("Folder") // folder selection title
                .toolbarImageTitle("Tap to select") // image selection title
                .toolbarArrowColor(Color.BLACK) // Toolbar 'up' arrow color
                .single() // single mode
                .showCamera(true) // show camera or not (true by default)
                .imageDirectory("Camera") // directory name for captured image  ("Camera" folder by default)
                .enableLog(false) // disabling log
                .start(); // start image picker activity with request code
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            //List<Image> images = ImagePicker.getImages(data)
            // or get a single image only
            Image image = ImagePicker.getFirstImageOrNull(data);
            System.out.println("image path" + image.getPath());
            File file = new File(image.getPath());
            URI uri = file.toURI();
            uploadFireBasePic(Uri.parse(uri.toString()));

        }
        super.onActivityResult(requestCode, resultCode, data);

    }
    public void uploadFireBasePic(Uri selectedImageUri) {
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        final UploadTask photoRef = mStorageRef.child(selectedImageUri.getLastPathSegment()).putFile(selectedImageUri);
        System.out.println("uri is : " + selectedImageUri);
        photoRef.addOnSuccessListener(taskSnapshot -> {
            System.out.println("ERROR UPLOADING : Success");
            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(uri -> {
                System.out.println("image url  :" + uri);
                //request.setProfilePictureUrl(uri.toString());
                //clientData.setProfilePicture(uri.toString());
                Picasso.get().load(uri).into(binding.ivReset);
                request.setPictureUrl(uri.toString());
                ProgressDialogUtils.getInstance().cancelDialog();
            });
        });
        photoRef.addOnFailureListener(e -> {
            System.out.println("ERROR UPLOADING :" + e.getMessage());
        });
    }

    public void book(View view){
        ProgressDialogUtils.getInstance().showProgressDialog(this);
        repositoryLazy.getValue().book(request).observe(this, stringMessageResponseResponse -> {
            ProgressDialogUtils.getInstance().cancelDialog();
            if(stringMessageResponseResponse.isSuccessful()){
                Snackbar.make(binding.clRoot,stringMessageResponseResponse.body().getMessage(),Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
