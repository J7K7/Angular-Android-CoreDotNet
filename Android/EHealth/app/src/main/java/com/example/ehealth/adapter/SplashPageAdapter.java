package com.example.ehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ehealth.R;

public class SplashPageAdapter extends PagerAdapter {

    Context context;

    int[] images = {R.drawable.slider1, R.drawable.slider2, R.drawable.slider3};
    String[] title = {"Title 1", "Title 2", "Title 3"};
    String[] message = {"Sample Message 1", "Sample Message 2", "Sample Message 3"};


    public SplashPageAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_splash, container, false);

        ImageView imageView = view.findViewById(R.id.ivSplashSlider);
        TextView tvBold = view.findViewById(R.id.tvSplashSliderBold);
        TextView tvNormal = view.findViewById(R.id.tvSplashSliderNormal);

        imageView.setImageResource(images[position]);
        tvBold.setText(title[position]);
        tvNormal.setText(message[position]);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
