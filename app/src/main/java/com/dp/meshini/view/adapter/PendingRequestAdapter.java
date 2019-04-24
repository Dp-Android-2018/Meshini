package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.dp.meshini.R;
import com.dp.meshini.databinding.PendingRequestListItemBinding;
import com.dp.meshini.servise.model.pojo.PendingRequest;
import com.dp.meshini.view.callback.DeletePendingRequest;
import com.dp.meshini.view.holder.PendingRequestViewHolder;

import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class PendingRequestAdapter extends RecyclerView.Adapter<PendingRequestViewHolder> {

    List<PendingRequest> pendingRequests;
    DeletePendingRequest deletePendingRequest;

    public PendingRequestAdapter(DeletePendingRequest deletePendingRequest) {
        this.deletePendingRequest = deletePendingRequest;
    }

    @NonNull
    public void setPendingRequests(@NonNull List<PendingRequest> pendingRequests) {
        if (this.pendingRequests == null) {
            this.pendingRequests = pendingRequests;
            notifyItemRangeInserted(0, pendingRequests.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return PendingRequestAdapter.this.pendingRequests.size();
                }

                @Override
                public int getNewListSize() {
                    return pendingRequests.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return PendingRequestAdapter.this.pendingRequests.get(oldItemPosition).getId() ==
                            pendingRequests.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    PendingRequest newProduct = pendingRequests.get(newItemPosition);
                    PendingRequest oldProduct = pendingRequests.get(oldItemPosition);

                    return newProduct.getId() == oldProduct.getId()
                            && Objects.equals(newProduct.getId(), oldProduct.getId());
                }
            });
            this.pendingRequests = pendingRequests;
            result.dispatchUpdatesTo(this);
        }
    }

    public PendingRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PendingRequestListItemBinding binding= DataBindingUtil.
                inflate(LayoutInflater.from(parent.getContext()), R.layout.pending_request_list_item, parent, false);
        return new PendingRequestViewHolder(binding,deletePendingRequest);
    }

    @Override
    public void onBindViewHolder(@NonNull PendingRequestViewHolder holder, int position) {
        holder.bindItem(pendingRequests.get(position));
    }

    @Override
    public int getItemCount() {
        if(pendingRequests!=null){
            System.out.println("requests size is : "+pendingRequests.size());
        }
        return pendingRequests==null?0:pendingRequests.size();
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
