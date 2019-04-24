package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dp.meshini.R;
import com.dp.meshini.databinding.OfferListItemBinding;
import com.dp.meshini.servise.model.pojo.Offer;
import com.dp.meshini.viewmodel.OfferListItemViewModel;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OfferHolder> {

    List<Offer>offers;
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    public void setOffers(List<Offer> offers) {
        if (this.offers == null) {
            this.offers = offers;
            notifyItemRangeInserted(0, offers.size());
        } else {
            DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
                @Override
                public int getOldListSize() {
                    return OffersAdapter.this.offers.size();
                }

                @Override
                public int getNewListSize() {
                    return offers.size();
                }

                @Override
                public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                    return OffersAdapter.this.offers.get(oldItemPosition).getId() ==
                            offers.get(newItemPosition).getId();
                }

                @Override
                public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                    Offer newProduct = offers.get(newItemPosition);
                    Offer oldProduct = offers.get(oldItemPosition);

                    return newProduct.getId() == oldProduct.getId()
                            && Objects.equals(newProduct.getName(), oldProduct.getName());
                }
            });
            this.offers = offers;
            result.dispatchUpdatesTo(this);
        }
    }
    int tripId;

    public OffersAdapter(int tripId) {
        this.tripId = tripId;
    }

    @NonNull
    @Override
    public OfferHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        OfferListItemBinding binding= DataBindingUtil.
                inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.offer_list_item, viewGroup, false);
        return new OfferHolder(binding,tripId);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferHolder offerHolder, int i) {
        offerHolder.bindOfferItem(offers.get(i),i);
    }

    @Override
    public int getItemCount() {
        return offers!=null?offers.size():0;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }

    public void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    public void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }


    class OfferHolder extends RecyclerView.ViewHolder {
        OfferListItemBinding offerListItemBinding;
        OfferListItemViewModel offerListItemViewModel;
        int requestId;
        public OfferHolder(@NonNull OfferListItemBinding offerListItemBinding,int requestId) {
            super(offerListItemBinding.getRoot());
            this.offerListItemBinding=offerListItemBinding;
            this.requestId=requestId;
        }

        public void bindOfferItem(Offer offer,int i) {
            if (offerListItemBinding.getViewModel()== null) {
                offerListItemViewModel= new OfferListItemViewModel(offer, itemView.getContext(),offerListItemBinding,requestId);
                offerListItemBinding.setViewModel(offerListItemViewModel);
            } else {
                offerListItemBinding.getViewModel().setOffer(offer);
            }
        }
    }

}

