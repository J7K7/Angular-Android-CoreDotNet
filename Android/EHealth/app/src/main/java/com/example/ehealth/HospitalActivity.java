package com.example.ehealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ehealth.adapter.HospitalAdapter;
import com.example.ehealth.entity.Users;
import com.example.ehealth.retrofit.EndPoint;
import com.example.ehealth.retrofit.LoginResponse;
import com.example.ehealth.retrofit.RetrofitEndPoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalActivity extends AppCompatActivity {

    private ArrayList<Users> hospitals = new ArrayList<Users>();

    private HospitalAdapter hospitalAdapter;

    private EndPoint endPoint;

    private String api_url;
    private String imagepath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);

        api_url = getString(R.string.api_url);
        imagepath = api_url + "/Content/Images/";

        View appbar = findViewById(R.id.appbar);
        appbar.findViewById(R.id.ibAppBarFilter).setVisibility(View.GONE);
        TextView tvAppbar =  appbar.findViewById(R.id.tvAppBar);
        tvAppbar.setText(getString(R.string.app_hospital));
        appbar.findViewById(R.id.ibAppBarBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        RecyclerView rvHospital = findViewById(R.id.rvHospitalActivity);
        rvHospital.setLayoutManager(new LinearLayoutManager(this));
        hospitalAdapter = new HospitalAdapter(HospitalActivity.this, hospitals, imagepath);
        rvHospital.setAdapter(hospitalAdapter);

        endPoint = RetrofitEndPoint.RetrofitEndPoint(api_url);

        Hospitals();
    }

    private void Hospitals() {
        Call<ArrayList<Users>> getHospitals = endPoint.GetHospitals();
        getHospitals.enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    hospitals.clear();
                    for (Users hospital : response.body()) {
                        hospitals.add(hospital);
                    }
                    hospitalAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Users>> call, Throwable t) {

            }
        });
    }
}