package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dp.meshini.R;
import com.dp.meshini.servise.model.pojo.Place;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class TripScheduleAdapter extends RecyclerView.Adapter<TripScheduleViewHolder> {

    List<String> places;
    List<Place>placeList;
    boolean fullObject;
    public void setPlaceList(List<Place> placeList,boolean fullObject) {
        this.placeList = placeList;
        this.fullObject=fullObject;
    }

    public void setPlaces(List<String> places) {
        this.places = places;
        fullObject=false;
    }

    @NonNull
    @Override
    public TripScheduleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.trip_schedule_list_item,parent ,false);
        return new TripScheduleViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TripScheduleViewHolder holder, int position) {
        if (fullObject){
            holder.place.setText(placeList.get(position).getName());
            if (placeList.get(position).isDone()){
                holder.place.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.checked,0);
            }
        }else {
            holder.place.setText(places.get(position));
        }
    }

    @Override
    public int getItemCount() {
        if(fullObject){
            return placeList==null?0:placeList.size();
        }else {
            return places == null ? 0 : places.size();
        }
    }

}

class TripScheduleViewHolder extends RecyclerView.ViewHolder{
    TextView place;
    public TripScheduleViewHolder(@NonNull View itemView) {
        super(itemView);
        place=itemView.findViewById(R.id.tv_place);
    }
}
