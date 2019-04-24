package com.dp.meshini.view.activity;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
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

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class ProfileActivity extends BaseActivity {

    ActivityProfileBinding binding;
    Lazy<ProfileViewModel> profileViewModelLazy = inject(ProfileViewModel.class);
    Lazy<UpdateProfileRequest> requestLazy = inject(UpdateProfileRequest.class);
    ClientData clientData;
    private StorageReference mStorageRef;
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);
    UpdateProfileRequest request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);
        setLocality();
        mStorageRef = FirebaseStorage.getInstance().getReferenceFromUrl("gs://meshini-project-1550483518935.appspot.com/meshiniUser");
        request = requestLazy.getValue();
        clientData = sharedPreferenceHelpersLazy.getValue().getSaveUserObject();
        binding.etFirstName.setText(clientData.getFirstName());
        binding.etLastName.setText(clientData.getLastName());
        binding.etEmail.setText(clientData.getEmail());
        binding.etPhone.setText(clientData.getPhone());
        System.out.println("photo url : " + clientData.getProfilePicture());
        Picasso.get().load(clientData.getProfilePicture()).placeholder(R.mipmap.logo).into(binding.ivPersonalImage);
    }

    public void setLocality() {
        if (sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")) {
            binding.ivBack.setRotation(180);
        }
    }
    public void save(View view) {
        if (ValidationUtils.isEmpty(binding.etFirstName.getText().toString())) {
            showSnackbar(getString(R.string.enter_first_name_error_message));
            return;
        }
        if (ValidationUtils.isEmpty(binding.etLastName.getText().toString())) {
            showSnackbar(getString(R.string.enter_last_name_error_message));
            return;
        }
        if (ValidationUtils.isEmpty(binding.etPhone.getText().toString())) {
            showSnackbar(getString(R.string.enter_phone_error_message));
            return;
        }

        request.setFirstName(binding.etFirstName.getText().toString());
        request.setLastName(binding.etLastName.getText().toString());
        request.setPhone(binding.etPhone.getText().toString());
        profileViewModelLazy.getValue().updateProfile(request).observe(this, new Observer<Response<StringMessageResponse>>() {
            @Override
            public void onChanged(Response<StringMessageResponse> stringMessageResponseResponse) {
                if (stringMessageResponseResponse.isSuccessful()) {
                    showSnackbar(stringMessageResponseResponse.body().getMessage());
                    clientData.setFirstName(binding.etFirstName.getText().toString());
                    clientData.setLastName(binding.etLastName.getText().toString());
                    clientData.setPhone(binding.etPhone.getText().toString());

                    sharedPreferenceHelpersLazy.getValue().saveDataToPrefs(clientData);
                    new Handler().postDelayed(() -> {
                        Intent intent = new Intent(ProfileActivity.this, ContainerActivity.class);
                        startActivity(intent);
                        finishAffinity();
                    }, Snackbar.LENGTH_LONG);
                }
            }
        });

    }

    public void showSnackbar(String message) {
        Snackbar.make(binding.clRoot, message, Snackbar.LENGTH_LONG).show();
    }

    public void changePassword(View view) {
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
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

        final UploadTask photoRef = mStorageRef.child(selectedImageUri.getLastPathSegment()).putFile(selectedImageUri);
        System.out.println("uri is : " + selectedImageUri);
        photoRef.addOnSuccessListener(taskSnapshot -> {
            System.out.println("ERROR UPLOADING : Success");
            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(uri -> {
                System.out.println("image url  :" + uri);
                request.setProfilePictureUrl(uri.toString());
                clientData.setProfilePicture(uri.toString());
                Picasso.get().load(uri).placeholder(R.mipmap.logo).into(binding.ivPersonalImage);

            });
        });
        photoRef.addOnFailureListener(e -> {
            System.out.println("ERROR UPLOADING :" + e.getMessage());
        });
    }

    public void back(View view) {
        finish();
    }
}
