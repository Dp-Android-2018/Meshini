package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.dp.meshini.databinding.PastUpcommingSharedTripListItemBinding;
import com.dp.meshini.servise.model.pojo.PastUpcommingSharedListItem;
import com.dp.meshini.viewmodel.PastUpcomingSharedListItemViewModel;

import java.util.List;

public class SharedTripsHistoryAdapter extends RecyclerView.Adapter<ListSharedTripViewHolder>  {

    List<PastUpcommingSharedListItem> sharedListItems;
    public String tripType;

    public SharedTripsHistoryAdapter() {
    }

    public void setSharedListItems(List<PastUpcommingSharedListItem> sharedListItems) {
        this.sharedListItems = sharedListItems;
    }

    public void setTripType(String tripType) {
        this.tripType = tripType;
        System.out.println("trip type in history adapter : "+tripType);
    }

    @NonNull
    @Override
    public ListSharedTripViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PastUpcommingSharedTripListItemBinding binding= DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.past_upcomming_shared_trip_list_item,parent,false);

        return new ListSharedTripViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSharedTripViewHolder holder, int position) {
        holder.bindItem(sharedListItems.get(position),tripType);
    }

    @Override
    public int getItemCount() {
        return sharedListItems==null?0:sharedListItems.size();
    }


}

class ListSharedTripViewHolder extends RecyclerView.ViewHolder{
    PastUpcommingSharedTripListItemBinding binding;
    PastUpcomingSharedListItemViewModel viewModel;
    public ListSharedTripViewHolder(@NonNull PastUpcommingSharedTripListItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bindItem(PastUpcommingSharedListItem item,String tripType){
        System.out.println("trip type in view holder : "+tripType);
        if(binding.getViewModel()==null){
            viewModel=new PastUpcomingSharedListItemViewModel(item,binding.getRoot().getContext(),tripType);
            binding.setViewModel(viewModel);
        }else {
            binding.getViewModel().setItem(item);
            binding.getViewModel().setTripType(tripType);
        }
    }
}
