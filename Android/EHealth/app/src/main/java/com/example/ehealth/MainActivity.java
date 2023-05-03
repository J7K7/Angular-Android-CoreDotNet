package com.example.ehealth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ehealth.adapter.SplashPageAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private int CurrentPage = 0;

    private Button btnStart;

    private ViewPager viewPager;
    private SplashPageAdapter viewPageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPreferences = this.getSharedPreferences("Patient", MODE_PRIVATE);
        if(sharedPreferences.contains("PatientId")){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            finish();
        }


        btnStart = findViewById(R.id.btnMainActivity);

        viewPager = findViewById(R.id.vpMainActivity);
        viewPageAdapter = new SplashPageAdapter(this);
        viewPager.setAdapter(viewPageAdapter);

        ImagePageAdapter();

        Indicator(0);

//        ImageTimerThread();

        btnStart.setVisibility(View.INVISIBLE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void ImagePageAdapter() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                CurrentPage = position;
                Indicator(position);

                if (position < 2)
                    btnStart.setVisibility(View.INVISIBLE);

                else
                    btnStart.setVisibility(View.VISIBLE);
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    private void ImageTimerThread() {
        Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (CurrentPage == viewPageAdapter.getCount())
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

    private void Indicator(int position) {
        TextView textViews[] = new TextView[3];
        LinearLayout BottomNavigation = findViewById(R.id.llMainActivityBottomNavigation);

        BottomNavigation.removeAllViews();

        for (int i = 0; i < textViews.length; i++) {
            textViews[i] = new TextView(this);

            textViews[i].setText(Html.fromHtml("&#8226"));
            textViews[i].setTextSize(35);
            textViews[i].setTextColor(getResources().getColor(R.color.gray, getApplicationContext().getTheme()));

            BottomNavigation.addView(textViews[i]);
        }

        textViews[position].setTextColor(getResources().getColor(R.color.customPrimary, getApplicationContext().getTheme()));
    }
}