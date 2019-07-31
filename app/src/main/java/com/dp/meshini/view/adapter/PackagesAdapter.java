package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.dp.meshini.databinding.SharedTripListItemBinding;
import com.dp.meshini.servise.model.pojo.PackageData;
import com.dp.meshini.view.holder.PackageViewHolder;

import java.util.List;
import java.util.Objects;

public class PackagesAdapter extends RecyclerView.Adapter<PackageViewHolder> {

    List<PackageData>packageDataList;

    public void setPackageDataList(List<PackageData> packageDataList) {
        if (this.packageDataList == null) {
            this.packageDataList = packageDataList;
            notifyItemRangeInserted(0, packageDataList.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return PackagesAdapter.this.packageDataList.size();
                }

                @Override
                public int getNewListSize() {
                    return packageDataList.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return PackagesAdapter.this.packageDataList.get(oldItemPosition).getId() ==
                            packageDataList.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PackageData newProduct = packageDataList.get(newItemPosition);
                    PackageData oldProduct = packageDataList.get(oldItemPosition);

                    return newProduct.getId() == oldProduct.getId()
                            && Objects.equals(newProduct.getId(), oldProduct.getId());
                }
            });
            this.packageDataList = packageDataList;
            result.dispatchUpdatesTo(this);
        }
    }

    @NonNull
    @Override
    public PackageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SharedTripListItemBinding binding= DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.shared_trip_list_item, parent, false);
        return new PackageViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageViewHolder holder, int position) {
        holder.bindItem(packageDataList.get(position));
    }

    @Override
    public int getItemCount() {
        if(packageDataList!=null){
            System.out.println("requests size is : "+packageDataList.size());
        }
        return packageDataList==null?0:packageDataList.size();
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
