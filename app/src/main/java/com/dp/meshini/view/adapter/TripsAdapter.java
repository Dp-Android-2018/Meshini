package com.dp.meshini.view.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dp.meshini.R;
import com.dp.meshini.servise.model.pojo.PastUpcomingRequest;
import com.dp.meshini.utils.ConstantsFile;
import com.dp.meshini.view.activity.UpcomingTripDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TripsAdapter extends RecyclerView.Adapter<TripsViewHolder> {

    List<PastUpcomingRequest>requests;
    String tripType;

    public void setRequests(List<PastUpcomingRequest> requests) {
        this.requests = requests;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
    }

    @NonNull
    @Override
    public TripsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_list_item,parent ,false);
        return new TripsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripsViewHolder holder, int position) {
        Picasso.get().load(requests.get(position).getImageUrl()).into(holder.image);
        holder.date.setText(requests.get(position).getDate());
        holder.guideName.setText(requests.get(position).getServiceProviderName());
        holder.offerCost.setText(String.valueOf(requests.get(position).getOfferPrice()));
        holder.itemView.setOnClickListener(v -> {
            Intent intent=new Intent(holder.itemView.getContext(), UpcomingTripDetailActivity.class);
            intent.putExtra(ConstantsFile.IntentConstants.OFFER_ID,requests.get(position).getId());
            intent.putExtra(ConstantsFile.IntentConstants.TRIP_TYPE,tripType);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return requests==null?0:requests.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}
class TripsViewHolder extends RecyclerView.ViewHolder{
    ImageView image;
    TextView date;
    TextView guideName;
    TextView offerCost;
    public TripsViewHolder(@NonNull View itemView) {
        super(itemView);
        image=itemView.findViewById(R.id.iv_image);
        date=itemView.findViewById(R.id.tv_date_value);
        guideName=itemView.findViewById(R.id.tv_guide_name_value);
        offerCost=itemView.findViewById(R.id.tv_offer_cost);
    }
}
