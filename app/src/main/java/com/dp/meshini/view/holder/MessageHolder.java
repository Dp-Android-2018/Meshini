package com.dp.meshini.view.holder;

import android.view.View;
import android.widget.TextView;

import com.dp.meshini.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MessageHolder extends RecyclerView.ViewHolder {
    TextView messageContent;
    TextView sentTime;


    public MessageHolder(@NonNull View itemView) {
        super(itemView);
        //messageContent = itemView.findViewById(R.id.tv_message);
        sentTime = itemView.findViewById(R.id.tv_time);
    }
}
