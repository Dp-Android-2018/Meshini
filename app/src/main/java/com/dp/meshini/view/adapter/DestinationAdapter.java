package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dp.meshini.R;
import com.dp.meshini.utils.DateTimePicker;
import com.dp.meshini.view.callback.OnDateTimeSelected;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableInt;
import androidx.recyclerview.widget.RecyclerView;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestantionHolder> implements OnDateTimeSelected {

    private ObservableInt recyclerViewSize;

    public DestinationAdapter() {
        recyclerViewSize = new ObservableInt(1);
    }

    @NonNull
    @Override
    public DestantionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.destination_list_item_layout, viewGroup, false);
        return new DestantionHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DestantionHolder destantionHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return recyclerViewSize.get();
    }

    public void incrementRecyclerViewSize() {
        int temp = this.recyclerViewSize.get();
        this.recyclerViewSize.set(temp + 1);
    }

    public void decrementRecyclerViewSize() {
        int temp = this.recyclerViewSize.get();
        this.recyclerViewSize.set(temp - 1);
    }

    public void deleteItem(int position) {
        decrementRecyclerViewSize();
        this.notifyDataSetChanged();
      /*  mRecentlyDeletedItem = mListItems.get(position);
        mRecentlyDeletedItemPosition = position;
        mListItems.remove(position);
        notifyItemRemoved(position);
        showUndoSnackbar();*/
    }

    @Override
    public void onDateTimeReady(String date, String time) {
        System.out.println("date is : " + date);
        System.out.println("time is : " + time);
    }


    class DestantionHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public DestantionHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_pick_date_time);
            imageView.setOnClickListener(v -> {
                DateTimePicker.getInstance().showDatePickerDialog(itemView.getContext(), DestinationAdapter.this::onDateTimeReady);
            });
        }

    }
}