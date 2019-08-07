package com.dp.meshini.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dp.meshini.R;
import com.dp.meshini.servise.model.pojo.CountryCityPojo;

import java.util.List;

public class StringSpinnerAdapter extends ArrayAdapter<String> {


    public StringSpinnerAdapter(@NonNull Context context, List<String> roomsType) {
        super(context, 0,roomsType);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.spinner_list_item, parent, false);
        }
        TextView textViewName = convertView.findViewById(R.id.tv_country_name);

        String currentItem = getItem(position);

        if (currentItem != null) {
            textViewName.setText(currentItem);
        }

        return convertView;
    }
}
