package com.dp.meshini.view.holder;

import com.dp.meshini.databinding.PendingRequestListItemBinding;
import com.dp.meshini.servise.model.pojo.PendingRequest;
import com.dp.meshini.view.callback.DeletePendingRequest;
import com.dp.meshini.viewmodel.PendingRequestListItemViewModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PendingRequestViewHolder extends RecyclerView.ViewHolder {

    PendingRequestListItemBinding binding;
    DeletePendingRequest deletePendingRequest;

    public PendingRequestViewHolder(@NonNull PendingRequestListItemBinding binding,DeletePendingRequest deletePendingRequest) {
        super(binding.getRoot());
        this.binding = binding;
        this.deletePendingRequest=deletePendingRequest;
    }

    public void bindItem(PendingRequest pendingRequest) {
        if (binding.getViewModel() == null) {
            binding.setViewModel(new PendingRequestListItemViewModel(itemView.getContext(), pendingRequest,deletePendingRequest));
        } else {
            binding.getViewModel().setPendingRequest(pendingRequest);
        }
    }
}
