package com.dp.meshini.view.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;

import java.util.List;

public class DestinationSharedAdapter extends RecyclerView.Adapter<ViewHolder> {
    List<String> destinations;

    public DestinationSharedAdapter(List<String> destinations) {
        this.destinations = destinations;
        System.out.println("city size is : "+destinations.size());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.destination_list_item,parent,false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.textView.setText(destinations.get(position));
    //    System.out.println("postion : "+position+" of "+destinations.size());
//        if(position==(destinations.size()-1)&&!flag) {
//            holder.view.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return destinations.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder{

    TextView textView;
    View view;
    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        textView=itemView.findViewById(R.id.tv_destination);
        view=itemView.findViewById(R.id.view12);
    }
}
