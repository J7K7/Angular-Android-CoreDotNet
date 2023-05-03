package com.example.ehealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.ehealth.adapter.DoctorAdapter;
import com.example.ehealth.entity.Users;
import com.example.ehealth.retrofit.EndPoint;
import com.example.ehealth.retrofit.RetrofitEndPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorActivity extends AppCompatActivity {

    private String api_url;
    private String imagepath;

    private ArrayList<Users> doctors = new ArrayList<Users>();

    private EndPoint endPoint;

    private RecyclerView recyclerView;
    private DoctorAdapter doctorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        View appbar = findViewById(R.id.appbar);
        appbar.findViewById(R.id.ibAppBarFilter).setVisibility(View.GONE);
        TextView tvAppbar =  appbar.findViewById(R.id.tvAppBar);
        tvAppbar.setText(getString(R.string.app_doctor));
        appbar.findViewById(R.id.ibAppBarBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        api_url = getString(R.string.api_url);
        imagepath = api_url + "/Content/Images/";

        recyclerView = findViewById(R.id.rvDoctorActivity);

        endPoint = RetrofitEndPoint.RetrofitEndPoint(api_url);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        doctorAdapter = new DoctorAdapter(this, doctors, imagepath, 0);
        recyclerView.setAdapter(doctorAdapter);

        DoctorAdapter();
    }

    private void DoctorAdapter() {
        Call<ArrayList<Users>> getDoctorsByHospital = endPoint.GetDoctors(0, 0, 0, true);
        getDoctorsByHospital.enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    doctors.clear();
                    for (Users doctor : response.body()) {
                        doctors.add(doctor);
                    }
                    doctorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Users>> call, Throwable t) {

            }
        });
    }

}