package com.example.ehealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ehealth.adapter.DashboardAdapter;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        View appbar = findViewById(R.id.appbar);
        appbar.findViewById(R.id.ibAppBarFilter).setVisibility(View.GONE);
        appbar.findViewById(R.id.ibAppBarBack).setVisibility(View.GONE);
        TextView tvAppbar =  appbar.findViewById(R.id.tvAppBar);
        tvAppbar.setText(getString(R.string.app_dashboard));

        RecyclerView rvDashboard = findViewById(R.id.rvDashboardActivity);
        rvDashboard.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        DashboardAdapter dashboardAdapter = new DashboardAdapter(this);
        rvDashboard.setAdapter(dashboardAdapter);
    }
}