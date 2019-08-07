package com.dp.meshini.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhotosAdabter extends RecyclerView.Adapter<PhotosViewHolder> {

    List<String>photos;

    public PhotosAdabter(List<String> photos) {
        this.photos = photos;
    }

    @NonNull
    @Override
    public PhotosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item,parent,false);
        return new PhotosViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotosViewHolder holder, int position) {
        Picasso.get().load(photos.get(position)).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }
}
class PhotosViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    public PhotosViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView=itemView.findViewById(R.id.iv_image_item);
    }
}
