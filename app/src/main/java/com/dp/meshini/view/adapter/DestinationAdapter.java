package com.dp.meshini.view.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ObservableInt;
import androidx.databinding.ObservableList;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.dp.meshini.R;
import com.dp.meshini.repositories.PlacesRepository;
import com.dp.meshini.servise.model.pojo.Place;
import com.dp.meshini.servise.model.response.ErrorResponse;
import com.dp.meshini.servise.model.response.PlacesResponse;
import com.dp.meshini.utils.ProgressDialogUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import kotlin.Lazy;
import retrofit2.Response;

import static org.koin.java.standalone.KoinJavaComponent.inject;

public class DestinationAdapter extends RecyclerView.Adapter<DestinationAdapter.DestantionHolder> {

    private ObservableInt recyclerViewSize;
    Lazy<PlacesRepository> repositoryLazy = inject(PlacesRepository.class);
    List<Integer> selectedPlaces = new ArrayList();
    int selectedPlace;
    List<Place> allPlaces = new ArrayList<>();
    ObservableList<Place> placesToShow = new ObservableArrayList<>();
    Context context;
    PlacesSpinnerAdapter spinnerAdapter;

    public DestinationAdapter(Context context, int countryId) {
        recyclerViewSize = new ObservableInt(1);
        this.context = context;
        setAllPlacesPlaces(countryId);
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
        selectedPlaces.add(selectedPlace);
        for (int i = 0; i < placesToShow.size(); i++) {
            if (placesToShow.get(i).getId() == selectedPlace) {
                placesToShow.remove(i);
                break;
            }
        }
        //spinnerAdapter.notifyDataSetChanged();
        if (placesToShow.size() <= 0) {
            Toast.makeText(context, "You have selected all places", Snackbar.LENGTH_LONG).show();
        } else {
            this.recyclerViewSize.set(temp + 1);
        }
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
        int deletedId = selectedPlaces.get(position);
        selectedPlaces.remove(position);
        for (int i = 0; i < allPlaces.size(); i++) {
            if (allPlaces.get(i).getId() == deletedId) {
                placesToShow.add(allPlaces.get(i));
                break;
            }
        }
        spinnerAdapter.notifyDataSetChanged();
    }


    class DestantionHolder extends RecyclerView.ViewHolder {
        Spinner spinner;

        public DestantionHolder(@NonNull View itemView) {
            super(itemView);
            spinner = itemView.findViewById(R.id.sp_Places);
            spinnerAdapter = new PlacesSpinnerAdapter(itemView.getContext(), placesToShow);
            spinner.setAdapter(spinnerAdapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedPlace = placesToShow.get(position).getId();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                }
            });
        }

    }

    public void setAllPlacesPlaces(int id) {
        ProgressDialogUtils.getInstance().showProgressDialog((Activity) context);
        repositoryLazy.getValue().getPlaces(id).observeForever(placesResponseResponse -> {
            if (placesResponseResponse.isSuccessful()) {
                allPlaces.addAll(placesResponseResponse.body().getPlaces());
                placesToShow.addAll(allPlaces);
                spinnerAdapter.notifyDataSetChanged();
            }else {
                Gson gson = new GsonBuilder().create();
                ErrorResponse errorResponse = new ErrorResponse();
                try {
                    errorResponse = gson.fromJson(placesResponseResponse.errorBody().string(), ErrorResponse.class);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String error = "";
                for (String string : errorResponse.getErrors()) {
                    error += string;
                    error += "\n";
                }
                Toast.makeText(context,error,Toast.LENGTH_LONG).show();
            }
            ProgressDialogUtils.getInstance().cancelDialog();

        });
    }

            public List<Integer> getSelectedPlaces() {
                if (!selectedPlaces.contains(selectedPlace)) {
                    selectedPlaces.add(selectedPlace);
                }
                return selectedPlaces;
            }
        }