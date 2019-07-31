package com.dp.meshini.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dp.meshini.servise.model.pojo.PackageData;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.view.activity.DetailedSharedTripActivity;

public class PackageListItemViewModel {

    Context context;
    PackageData packageData;

    public PackageListItemViewModel(Context context, PackageData packageData) {
        this.context = context;
        this.packageData = packageData;
    }

    public void setPackageData(PackageData packageData) {
        this.packageData = packageData;
    }

    public String getCompanyName(){
        return packageData.getCompanyName();
    }

    public String getDate(){
        return "Trip date: "+packageData.getStartDate();
    }

    public String getCities(){
        String cities="";
        for(String city:packageData.getCities()){
            cities+=city;
            cities+="/";
        }
        System.out.println("Cities is : "+cities);
        return "cities: "+cities;
    }

    public String getPrice(){
        return "Price per person: "+String.valueOf(packageData.getPrice())+"EGP";
    }

    public String getImage(){
        return packageData.getImageUrl();
    }

    public void onItemClick(View view){
        Intent intent=new Intent(context, DetailedSharedTripActivity.class);
        intent.putExtra(ConstantsFile.IntentConstants.SHARED_TRIP_ID,packageData.getId());
        context.startActivity(intent);
    }
}
