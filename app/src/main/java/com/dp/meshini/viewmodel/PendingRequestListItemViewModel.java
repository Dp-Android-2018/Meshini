package com.dp.meshini.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dp.meshini.servise.model.pojo.PendingRequest;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.view.activity.OffersActivity;
import com.dp.meshini.view.callback.DeletePendingRequest;

import static com.dp.meshini.utils.ConstantsFile.Constants.PENDING_TRIP;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIP_ID;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIP_TYPE;

public class PendingRequestListItemViewModel {

    Context context;
    PendingRequest pendingRequest;
    DeletePendingRequest deletePendingRequest;

    public PendingRequestListItemViewModel(Context context, PendingRequest pendingRequest,DeletePendingRequest deletePendingRequest) {
        this.context = context;
        this.pendingRequest = pendingRequest;
        this.deletePendingRequest=deletePendingRequest;
    }

    public void setPendingRequest(PendingRequest pendingRequest) {
        this.pendingRequest = pendingRequest;
    }

    public String getCountry() {
        return  pendingRequest.getCountry();
    }

    public String getPickupLocation() {
        return  pendingRequest.getPickupAddress();
    }

    public String getDate() {
        return pendingRequest.getDate();
    }

    public String getVehicle() {
        return pendingRequest.getVehicleType();
    }

    public void onItemClick(View view){
        Intent intent=new Intent(context, OffersActivity.class);
        intent.putExtra(TRIP_ID,pendingRequest.getId());
        context.startActivity(intent);
        ((Activity)context).finish();
    }

    public void delete(View view){
        deletePendingRequest.onItemDelete(pendingRequest.getId());
    }
}
