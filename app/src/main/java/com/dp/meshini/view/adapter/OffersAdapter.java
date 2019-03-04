package com.dp.meshini.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OffersAdapter extends RecyclerView.Adapter<OfferHolder> {

    @NonNull
    @Override
    public OfferHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull OfferHolder offerHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}

class OfferHolder extends RecyclerView.ViewHolder {

    public OfferHolder(@NonNull View itemView) {
        super(itemView);
    }
}
