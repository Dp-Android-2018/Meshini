package com.dp.meshini.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.dp.meshini.R;
import com.dp.meshini.databinding.OfferListItemBinding;
import com.dp.meshini.repositories.AcceptOfferRepository;
import com.dp.meshini.servise.model.pojo.Offer;
import com.dp.meshini.servise.model.request.AcceptOfferRequest;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.view.activity.UpcomingTripDetailActivity;

import java.util.List;

import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import kotlin.Lazy;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class OfferListItemViewModel {
    Offer offer;
    Context context;
    OfferListItemBinding offerListItemBinding;
    public ObservableInt contentVisibality;
    Lazy<AcceptOfferRequest>acceptOfferRequestLazy=inject(AcceptOfferRequest.class);
    Lazy<AcceptOfferRepository>repositoryLazy=inject(AcceptOfferRepository.class);
    AcceptOfferRequest acceptOfferRequest=acceptOfferRequestLazy.getValue();
    int requestId;
    public OfferListItemViewModel(Offer offer, Context context,OfferListItemBinding offerListItemBinding,int requestId) {
        this.offer = offer;
        this.context = context;
        this.offerListItemBinding=offerListItemBinding;
        this.requestId=requestId;
        contentVisibality=new ObservableInt(View.GONE);
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }


    public String getImage(){
        return offer.getProfileImageUrl();
    }

    public String getName(){
        return offer.getName();
    }

    public String getRate(){
        return context.getString(R.string.rating)+offer.getRating();
    }

    public String getTripsNo(){
        return context.getString(R.string.no_of_trips)+offer.getTripsCount();
    }

    public String getCost(){
        return offer.getPrice()+context.getString(R.string.egp);
    }

    public List<String> getReviews(){
        return offer.getReviews();
    }

    public RecyclerView.LayoutManager getLayoutManager(){
        return new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
    }

    public void onItemClick(View view){
        if(contentVisibality.get()==View.GONE){
            contentVisibality.set(View.VISIBLE);
        }else {
            contentVisibality.set(View.GONE);
        }
    }

    public void accept(View view){
        acceptOfferRequest.setOfferId(offer.getId());
        repositoryLazy.getValue().accetpOffer(acceptOfferRequest).observeForever(stringMessageResponseResponse -> {
            if(stringMessageResponseResponse.isSuccessful()){
                Intent intent=new Intent(context, UpcomingTripDetailActivity.class);
                intent.putExtra(ConstantsFile.IntentConstants.OFFER_ID,requestId);
                intent.putExtra(ConstantsFile.IntentConstants.TRIP_TYPE,ConstantsFile.Constants.OFFER);
                context.startActivity(intent);
                ((Activity)context).finish();
            }else {
            }
        });
    }

}
