package com.dp.meshini.view.holder;

import android.text.format.DateFormat;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ItemListChatServisProviderBinding;
import com.dp.meshini.databinding.ItemListChatUserBinding;
import com.dp.meshini.servise.model.pojo.Message;
import com.dp.meshini.utils.SharedPreferenceHelpers;

import java.util.Calendar;
import java.util.Locale;

import kotlin.Lazy;

import static org.koin.java.standalone.KoinJavaComponent.inject;


public class ChatViewHolder extends RecyclerView.ViewHolder {
    private  ItemListChatServisProviderBinding servisProviderBinding;
    private ItemListChatUserBinding userBinding;
    Lazy<SharedPreferenceHelpers> sharedPreferenceHelpersLazy = inject(SharedPreferenceHelpers.class);
    private Message message;
    private int serviceProviderId;
    private int userId;
    private Calendar cal;

    public ChatViewHolder(ItemListChatServisProviderBinding binding) {
        super(binding.getRoot());
        this.servisProviderBinding = binding;
    }
    public ChatViewHolder(ItemListChatUserBinding binding) {
        super(binding.getRoot());
        this.userBinding = binding;
    }

    public void bindServiceProvider(Message message, int serviceProviderId, int userId) {
        this.message = message;
        this.serviceProviderId = serviceProviderId;
        this.userId = userId;
        if (sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")) {
            Locale locale = new Locale("ar", "EG");
            cal = Calendar.getInstance(locale);
            System.out.println("language : arabic");
            cal.setTimeInMillis(message.getTimestamp() * 1000L);
//        "dd-MM-yyyy hh:mm:ss"
            String date = DateFormat.format("EEE, MMM d, hh:mm:ss", cal).toString();
            servisProviderBinding.tvMessageTime.setText(date);
        } else {
            cal = Calendar.getInstance(Locale.ENGLISH);
            System.out.println("language : english");
            cal.setTimeInMillis(message.getTimestamp() * 1000L);
//        "dd-MM-yyyy hh:mm:ss"
            String date = DateFormat.format("EEE, MMM d, hh:mm:ss", cal).toString();
            servisProviderBinding.tvMessageTime.setText(date);
        }
        servisProviderBinding.tvMessageContent.setText(message.getContent());

    }

    public void bindUser(Message message, int serviceProviderId, int userId) {
        this.message = message;
        this.serviceProviderId = serviceProviderId;
        this.userId = userId;
        if (sharedPreferenceHelpersLazy.getValue().getAppLanguage().equals("ar")) {
            Locale locale = new Locale("ar", "EG");
            cal = Calendar.getInstance(locale);
            System.out.println("language : arabic");
            cal.setTimeInMillis(message.getTimestamp() * 1000L);
//        "dd-MM-yyyy hh:mm:ss"
            String date = DateFormat.format("EEE, MMM d, hh:mm:ss", cal).toString();
            userBinding.tvMessageTime.setText(date);
        } else {
            cal = Calendar.getInstance(Locale.ENGLISH);
            System.out.println("language : english");
            cal.setTimeInMillis(message.getTimestamp() * 1000L);
//        "dd-MM-yyyy hh:mm:ss"
            String date = DateFormat.format("EEE, MMM d, hh:mm:ss", cal).toString();
            userBinding.tvMessageTime.setText(date);
        }
        userBinding.tvMessageContent.setText(message.getContent());

    }

}
