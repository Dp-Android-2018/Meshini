package com.dp.meshini.view.holder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.databinding.SharedTripListItemBinding;
import com.dp.meshini.servise.model.pojo.PackageData;
import com.dp.meshini.viewmodel.PackageListItemViewModel;

public class PackageViewHolder extends RecyclerView.ViewHolder {

    SharedTripListItemBinding binding;

    public PackageViewHolder(@NonNull SharedTripListItemBinding binding) {
        super(binding.getRoot());
        this.binding=binding;
    }

    public void bindItem(PackageData packageData){
        if (binding.getViewModel() == null) {
            binding.setViewModel(new PackageListItemViewModel(itemView.getContext(), packageData));
        } else {
            binding.getViewModel().setPackageData(packageData);
        }
    }
}
