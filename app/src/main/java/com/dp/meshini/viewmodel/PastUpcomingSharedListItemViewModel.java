package com.dp.meshini.viewmodel;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.dp.meshini.R;
import com.dp.meshini.servise.endpoint.EndPoints;
import com.dp.meshini.servise.model.pojo.PastUpcommingSharedListItem;
import com.dp.meshini.servise.model.request.PackageRateRequest;
import com.dp.meshini.servise.model.response.StringMessageResponse;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.view.activity.DetailedSharedTripActivity;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import kotlin.Lazy;
import retrofit2.Response;

import static com.dp.meshini.utils.ConstantsFile.IntentConstants.SHARED_TRIP_ID;
import static com.dp.meshini.utils.ConstantsFile.IntentConstants.TRIP_TYPE;
import static org.koin.java.standalone.KoinJavaComponent.inject;

public class PastUpcomingSharedListItemViewModel {

    PastUpcommingSharedListItem item;
    Context context;
    String tripType;
    Lazy<EndPoints>endPointsLazy=inject(EndPoints.class);
    PackageRateRequest request;
    public PastUpcomingSharedListItemViewModel(PastUpcommingSharedListItem item,Context context,String tripType) {
        this.item = item;
        this.context=context;
        this.tripType=tripType;
        System.out.println("trip type is : "+tripType);
    }

    public void setItem(PastUpcommingSharedListItem item) {
        this.item = item;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    public String getCompanyName(){
        return item.getCompanyName();
    }

    public String getImage(){
        return item.getCompanyImage();
    }

    public String getDate(){
        return "Trip date: "+item.getStartDate();
    }

    public String getCities(){
        String cities="";
        for(String city:item.getCityName()){
            cities+=city;
            cities+="/";
        }
        System.out.println("Cities is : "+cities);
        return "cities: "+cities;
    }

    public String getPrice(){
        return "Price per person: "+item.getPricePerPerson()+" EGP";
    }

    public String getRoomType(){
        return "Rooms type : "+item.getRoomType();
    }

    public String getPayType(){
        return "Payment type : "+item.getPaymentType();
    }

    public String getPayStatus(){
        return "Payment Status : "+item.getStatus();
    }

    public String getNoPerson(){
        return "Number of persons : "+item.getNoOfPersons();
    }

    public String getTotalPrice(){
        return "Total price :"+item.getTotalAmounts()+"EGP";
    }

    public void detailes(View view){
        System.out.println("trip type in view Model : "+tripType);
        Intent intent=new Intent(context, DetailedSharedTripActivity.class);
        intent.putExtra(SHARED_TRIP_ID,item.getPackageId());
        intent.putExtra(TRIP_TYPE,tripType);
        context.startActivity(intent);
    }

    public void showRateDialog(View view) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = View.inflate(context, R.layout.rate_dialog, null);
        builder.setView(v);
        builder.setCancelable(false);
        CircleImageView guideImage = v.findViewById(R.id.civ_guide_image);
        RatingBar ratingBar = v.findViewById(R.id.ratingBar2);
        EditText comment = v.findViewById(R.id.et_comment);
        Button makeComment = v.findViewById(R.id.bt_done);
        Picasso.get().load(item.getCompanyImage()).placeholder(context.getDrawable(R.mipmap.logo)).into(guideImage);
        Dialog dialog = builder.create();
        makeComment.setOnClickListener(v1 -> createComment(comment.getText().toString(), ratingBar.getRating(), dialog));
        dialog.show();
    }

    private void createComment(String comment, float rating, Dialog dialog) {
        request=new PackageRateRequest();
        request.setCompanyId(item.getCompanyId());
        request.setRating(rating);
        request.setReview(comment);

        endPointsLazy.getValue().packageRate(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Response<StringMessageResponse>>() {
                    @Override
                    public void accept(Response<StringMessageResponse> stringMessageResponseResponse) throws Exception {
                        if(stringMessageResponseResponse.isSuccessful()){
                            dialog.dismiss();
                            Toast.makeText(context,"Commented successfully",Toast.LENGTH_LONG).show();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                    }
                });
    }
}
