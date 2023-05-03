package com.example.ehealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ehealth.adapter.AppointmentPageAdapter;
import com.example.ehealth.entity.Users;
import com.example.ehealth.retrofit.AppointmentSlotResponse;
import com.example.ehealth.retrofit.EndPoint;
import com.example.ehealth.retrofit.RetrofitEndPoint;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorDetailActivity extends AppCompatActivity {

    private String api_url;
    private String imagepath;

    private int HospitalId;
    private int DoctorId;
    private int SpecializationId;

    private TextView tvName;
    private TextView tvEmail;
    private TextView tvMobile;
    private TextView tvAppbar;

    private ImageView ivImage;

    private EndPoint endPoint;

    private ViewPager viewPager;
    private AppointmentPageAdapter appointmentPageAdapter;

    private ArrayList<AppointmentSlotResponse> arrayList = new ArrayList<AppointmentSlotResponse>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);

        View appbar = findViewById(R.id.appbar);
        appbar.findViewById(R.id.ibAppBarFilter).setVisibility(View.GONE);
        tvAppbar = appbar.findViewById(R.id.tvAppBar);

        appbar.findViewById(R.id.ibAppBarBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        HospitalId = intent.getIntExtra("HospitalId", 0);
        DoctorId = intent.getIntExtra("DoctorId", 0);

        SharedPreferences sharedPreferences = getSharedPreferences("Patient", MODE_PRIVATE);
        SpecializationId = sharedPreferences.getInt("SpecializationId", 0);

        api_url = getString(R.string.api_url);
        imagepath = api_url + "/Content/Images/";

        tvName = findViewById(R.id.tvDoctorDetailActivityName);
        tvEmail = findViewById(R.id.tvDoctorDetailActivityEmail);
        tvMobile = findViewById(R.id.tvDoctorDetailActivityMobile);
        ivImage = findViewById(R.id.ivDoctorDetailActivityImage);

        viewPager = findViewById(R.id.vpDoctorDetailActivity);
        appointmentPageAdapter = new AppointmentPageAdapter(this, arrayList);
        viewPager.setAdapter(appointmentPageAdapter);

        endPoint = RetrofitEndPoint.RetrofitEndPoint(api_url);

        DoctorDetail();

        AppointmentSlots();
    }

    private void DoctorDetail() {
        Call<ArrayList<Users>> getHospitals = endPoint.GetDoctors(HospitalId, DoctorId, SpecializationId, true);
        getHospitals.enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                if (response.isSuccessful() && response.code() == 200) {

                    Users users = response.body().get(0);

                    tvAppbar.setText(users.getFirstName() + " " + users.getLastName());

                    tvName.setText(users.getFirstName() + " " + users.getLastName());
                    tvEmail.setText("Email : " + users.getEmail());
                    tvMobile.setText("Ph no : +91 " + users.getMobileNo());

                    Picasso.with(DoctorDetailActivity.this).load(imagepath + users.getUserImages().get(0).getName()).into(ivImage);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Users>> call, Throwable t) {

            }
        });
    }

    private void AppointmentSlots() {
        Call<ArrayList<AppointmentSlotResponse>> getAppointmentSlots = endPoint.GetAppointmentSlots(HospitalId, DoctorId, SpecializationId);
        getAppointmentSlots.enqueue(new Callback<ArrayList<AppointmentSlotResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<AppointmentSlotResponse>> call, Response<ArrayList<AppointmentSlotResponse>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    arrayList.clear();

                    for (AppointmentSlotResponse slotResponse : response.body()) {
                        arrayList.add(slotResponse);
                    }

                    appointmentPageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<AppointmentSlotResponse>> call, Throwable t) {

            }
        });
    }
}