package com.example.ehealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ehealth.R;
import com.example.ehealth.entity.UserImage;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImagePageAdapter extends PagerAdapter {

    Context context;
    ArrayList<UserImage> userImages;
    String imagePath;

    public ImagePageAdapter(Context context, ArrayList<UserImage> userImages, String ImagePath) {
        this.context = context;
        this.userImages = userImages;
        this.imagePath = ImagePath;
    }

    @Override
    public int getCount() {
        return userImages.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_image, container, false);

        ImageView imageView = view.findViewById(R.id.ivImageSlider);
        Picasso.with(context).load(imagePath + userImages.get(position).getName()).into(imageView);

        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
