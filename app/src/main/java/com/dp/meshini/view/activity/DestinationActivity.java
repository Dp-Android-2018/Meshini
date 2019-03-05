package com.dp.meshini.view.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dp.meshini.R;
import com.dp.meshini.databinding.ActivityDestantionBinding;
import com.dp.meshini.utils.SwipeToDeleteCallback;
import com.dp.meshini.view.adapter.DestinationAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;

public class DestinationActivity extends AppCompatActivity {
    ActivityDestantionBinding binding;
    DestinationAdapter destantionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_destantion);
        initRecyclerViewAdapter();

    }

    public void initRecyclerViewAdapter() {
        destantionAdapter = new DestinationAdapter();
        binding.rvDestination.setNestedScrollingEnabled(false);
        binding.rvDestination.setLayoutManager(new LinearLayoutManager(this));
        binding.rvDestination.setAdapter(destantionAdapter);
        ItemTouchHelper itemTouchHelper = new
                ItemTouchHelper(new SwipeToDeleteCallback(destantionAdapter));
        itemTouchHelper.attachToRecyclerView(binding.rvDestination);
    }

    public void updateRecyclerViewSize(View view) {
        destantionAdapter.incrementRecyclerViewSize();
        destantionAdapter.notifyDataSetChanged();
    }

    public void showDialog(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialog);
        View v = View.inflate(this, R.layout.write_note_dialog, null);
        builder.setView(v);
        builder.setCancelable(true);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams layoutParams =  window.getAttributes();
        layoutParams .gravity = Gravity.BOTTOM;
        window.setAttributes(layoutParams);
        dialog.show();
    }

    public void showRateDialog(View view){
        final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialog);
        View v = View.inflate(this, R.layout.rate_dialog, null);
        builder.setView(v);
        builder.setCancelable(true);
        Dialog dialog = builder.create();
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        //WindowManager.LayoutParams layoutParams =  window.getAttributes();
        //layoutParams .gravity = Gravity.BOTTOM;
        //window.setAttributes(layoutParams);
        dialog.show();
    }

}
