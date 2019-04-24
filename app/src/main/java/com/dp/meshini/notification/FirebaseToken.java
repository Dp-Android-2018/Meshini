package com.dp.meshini.notification;

import com.google.firebase.iid.FirebaseInstanceId;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class FirebaseToken {

    private static FirebaseToken instance;

    private FirebaseToken() {
    }

    public static FirebaseToken getInstance() {
        if (instance == null) {
            instance = new FirebaseToken();
        }
        return instance;
    }

    public LiveData<String> getFirebaseToken() {
        MutableLiveData<String> deviceToken = new MutableLiveData<>();
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        //  Log.w(TAG, "getInstanceId failed", task.getException());
                        System.out.println("device Token 1: Failed");
                        return;
                    }
                    // Get new Instance ID token
                    deviceToken.setValue(task.getResult().getToken());
                    System.out.println("device Token 1:" + deviceToken);
                });
        return deviceToken;
    }

}
