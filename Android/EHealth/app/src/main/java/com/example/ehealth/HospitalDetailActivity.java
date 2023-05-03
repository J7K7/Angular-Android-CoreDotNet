package com.example.ehealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ehealth.adapter.DoctorAdapter;
import com.example.ehealth.adapter.DoctorSpecializationAdapter;
import com.example.ehealth.adapter.ImagePageAdapter;
import com.example.ehealth.entity.Specialization;
import com.example.ehealth.entity.UserImage;
import com.example.ehealth.entity.Users;
import com.example.ehealth.entity.UsersAddress;
import com.example.ehealth.retrofit.EndPoint;
import com.example.ehealth.retrofit.RetrofitEndPoint;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HospitalDetailActivity extends AppCompatActivity {

    private String api_url;
    private String imagepath;

    private int CurrentPage = 0;
    private int HospitalId;
    private int DoctorId;

    private ArrayList<UserImage> userImages = new ArrayList<UserImage>();
    private ArrayList<Specialization> specializations = new ArrayList<Specialization>();

    private EndPoint endPoint;

    private TextView tvName;
    private TextView tvAddress;
    private TextView tvEmail;
    private TextView tvMobile;
    private TextView tvAppbar;

    private Button btnBook;

    private RecyclerView recyclerView;
    private DoctorSpecializationAdapter doctorSpecializationAdapter;

    private ViewPager viewPager;
    private ImagePageAdapter imagePageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital_detail);

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

        api_url = getString(R.string.api_url);
        imagepath = api_url + "Content/Images/";

        endPoint = RetrofitEndPoint.RetrofitEndPoint(api_url);

        tvName = findViewById(R.id.tvHospitalDetailActivityName);
        tvAddress = findViewById(R.id.tvHospitalDetailActivityAddress);
        tvEmail = findViewById(R.id.tvHospitalDetailActivityEmail);
        tvMobile = findViewById(R.id.tvHospitalDetailActivityMobile);
        btnBook = findViewById(R.id.btnHospitalDetailActivity);

        recyclerView = findViewById(R.id.rvHospitalDetailActivityDoctors);
        viewPager = findViewById(R.id.vpHospitalDetailActivityHospitals);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        doctorSpecializationAdapter = new DoctorSpecializationAdapter(this, specializations, imagepath, HospitalId, endPoint);
        recyclerView.setAdapter(doctorSpecializationAdapter);

        imagePageAdapter = new ImagePageAdapter(this, userImages, imagepath);
        viewPager.setAdapter(imagePageAdapter);

        if (DoctorId > 0) {
            recyclerView.setVisibility(View.INVISIBLE);

            btnBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(HospitalDetailActivity.this, "Doctor", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            btnBook.setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.llHospitalDetailActivityCall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + tvMobile.getText().toString()));
                startActivity(callIntent);
            }
        });

        HospitalDetail();

        ImagePageAdapter();

        ImageTimerThread();

        Indicator(0);

        DoctorAdapter();
    }

    private void HospitalDetail() {
        Call<Users> getHospitals = endPoint.GetHospitalById(HospitalId);
        getHospitals.enqueue(new Callback<Users>() {
            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                if (response.isSuccessful() && response.code() == 200) {

                    UsersAddress usersAddress = response.body().getUsersAddresses().get(0);

                    tvAppbar.setText(response.body().getFirstName() + " " + response.body().getLastName());

                    tvName.setText(response.body().getFirstName() + " " + response.body().getLastName());
                    tvAddress.setText(usersAddress.getBuilding() + ", " + usersAddress.getStreet() + ", " +
                            usersAddress.getCity().getName() + ", " + usersAddress.getCity().getState().getName() + ", " +
                            usersAddress.getCity().getState().getCountry().getName());

                    tvEmail.setText(response.body().getEmail());
                    tvMobile.setText("+91 " + response.body().getMobileNo());

                    userImages.clear();
                    for (UserImage image : response.body().getUserImages()) {
                        userImages.add(image);
                    }
                    imagePageAdapter.notifyDataSetChanged();
                    Indicator(0);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }

    private void ImagePageAdapter() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                CurrentPage = position;
                Indicator(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void DoctorAdapter() {
        Call<ArrayList<Specialization>> getDoctorSpecializations = endPoint.GetDoctorSpecializations(HospitalId, 0, true);
        getDoctorSpecializations.enqueue(new Callback<ArrayList<Specialization>>() {
            @Override
            public void onResponse(Call<ArrayList<Specialization>> call, Response<ArrayList<Specialization>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    specializations.clear();
                    for (Specialization specialization : response.body()) {
                        Log.v("===========>", specialization.getName());
                        specializations.add(specialization);
                    }
                    doctorSpecializationAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Specialization>> call, Throwable t) {

            }
        });
    }

    private void Indicator(int position) {
        TextView textViews[] = new TextView[userImages.size()];
        LinearLayout BottomNavigation = findViewById(R.id.llHospitalDetailActivityBottomNavigation);

        BottomNavigation.removeAllViews();

        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = new TextView(this);

            textViews[i].setText(Html.fromHtml("&#8226"));
            textViews[i].setTextSize(35);
            textViews[i].setTextColor(getResources().getColor(R.color.lightSilver, getApplicationContext().getTheme()));

            BottomNavigation.addView(textViews[i]);
        }

        if (userImages.size() > 0)
            textViews[position].setTextColor(getResources().getColor(R.color.customPrimary, getApplicationContext().getTheme()));
    }

    private void ImageTimerThread() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (CurrentPage == imagePageAdapter.getCount())
                    CurrentPage = 0;

                viewPager.setCurrentItem(CurrentPage++, true);
            }
        };

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(runnable);
            }
        }, 3000, 1500);
    }
}