package com.dp.meshini.view.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.dp.meshini.R;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class ContainerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawer;
    Toolbar toolbar;
    View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        view = findViewById(R.id.layout_root);
        setNavigationDrawer();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent;
        int id = item.getItemId();
        switch (id) {
//            case R.id.account: {
//                intent = new Intent(ContainerActivity.this, ProfileActivity.class);
//                startActivity(intent);
//                break;
//            }
            case R.id.your_trip: {
                intent = new Intent(ContainerActivity.this, TripsActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.payment: {
                intent = new Intent(ContainerActivity.this, PaymentActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.help: {
                break;
            }
            case R.id.language: {
                break;
            }
            case R.id.rate: {
                break;
            }
            case R.id.logout: {
                break;
            }
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setNavigationDrawer() {

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            private float scaleFactor = 6f;

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                view.setTranslationX(slideX);
                view.setScaleX(1 - (slideOffset / scaleFactor));
                view.setScaleY(1 - (slideOffset / scaleFactor));
            }
        };

        toggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.setDrawerElevation(0f);
        toggle.setDrawerSlideAnimationEnabled(true);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setHomeAsUpIndicator(R.drawable.menu);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View itemView = navigationView.getHeaderView(0);
        View account=itemView.findViewById(R.id.v_account);
        account.setOnClickListener(v -> {
            Intent intent = new Intent(ContainerActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }

    public void showDialog(View view) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this,R.style.CustomDialog);
        View v = View.inflate(this, R.layout.request_guide_dialog, null);
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
}
