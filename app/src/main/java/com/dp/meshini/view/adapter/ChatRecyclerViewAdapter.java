package com.dp.meshini.view.adapter;


import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ItemListChatServisProviderBinding;
import com.dp.meshini.databinding.ItemListChatUserBinding;
import com.dp.meshini.servise.model.pojo.Message;
import com.dp.meshini.view.holder.ChatViewHolder;

import java.util.ArrayList;
import java.util.List;

import static com.dp.meshini.utils.ConstantsFile.Constants.VIEW_TYPE_MESSAGE_RECEIVED;
import static com.dp.meshini.utils.ConstantsFile.Constants.VIEW_TYPE_MESSAGE_SENT;


public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatViewHolder> {
    private List<Message> data = new ArrayList<>();
    private int serviceProviderId;
    private int userId;

    public ChatRecyclerViewAdapter(int serviceProviderId, int userId) {
        this.serviceProviderId = serviceProviderId;
        this.userId = userId;
    }

    public void setData(List<Message> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
            if (data.get(position).getFromID().equals("serviceProvider-" + serviceProviderId)) {
                return VIEW_TYPE_MESSAGE_SENT;

            } else {
                return VIEW_TYPE_MESSAGE_RECEIVED;
            }
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        System.out.println("view type val : "+viewType);
        if(viewType==VIEW_TYPE_MESSAGE_SENT){
            ItemListChatServisProviderBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_list_chat_servis_provider, parent, false);
            return new ChatViewHolder(binding);
        }else if(viewType==VIEW_TYPE_MESSAGE_RECEIVED) {
            ItemListChatUserBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_list_chat_user, parent, false);
            return new ChatViewHolder(binding);
        }
            return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        System.out.println("data.get(position) : "+data.get(position).getFromID());
        if(data.get(position).getFromID().equals("serviceProvider-" + serviceProviderId)){
            holder.bindServiceProvider(data.get(position), serviceProviderId, userId);
        }else {
            holder.bindUser(data.get(position), serviceProviderId, userId);
        }

    }

    @Override
    public int getItemCount() {
        if (data != null) {
            return data.size();
        } else {
            return 0;
        }
    }

    public void addItem(Message message) {
        data.add(message);
        // notify the item removed by position
        // to perform recycler view delete animations
        // NOTE: don't call notifyDataSetChanged()
        notifyDataSetChanged();
    }

    public int getLastPosition() {
        if (data != null) {
            return data.size() - 1;
        } else {
            return 0;
        }
    }
}
