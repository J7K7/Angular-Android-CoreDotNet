package com.example.ehealth.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.viewpager.widget.PagerAdapter;

import com.example.ehealth.R;
import com.example.ehealth.retrofit.AppointmentSlotResponse;

import java.util.ArrayList;
import java.util.Date;


public class AppointmentPageAdapter extends PagerAdapter {

    Context context;
    ArrayList<AppointmentSlotResponse> arrayList;
    AlertDialog.Builder builder;

    public AppointmentPageAdapter(Context context, ArrayList<AppointmentSlotResponse> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.builder = new AlertDialog.Builder(context);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider_appointment, container, false);

        LinearLayoutCompat linearLayout = view.findViewById(R.id.llSliderAppointment);

        TextView textView = view.findViewById(R.id.tvSliderAppointment);
        textView.setText(arrayList.get(position).getItem1().toString());

        Button button[] = new Button[arrayList.get(position).getItem2().size()];
        for (int i = 0; i < button.length; i++) {
            Date date = arrayList.get(position).getItem2().get(i).getItem1();

            button[i] = new Button(context);
            button[i].setText(date.getHours() + ":" + date.getMinutes());

            if (arrayList.get(position).getItem2().get(i).isItem2()) {
                button[i].setTextColor(context.getResources().getColor(R.color.customPrimary));
                button[i].setBackgroundColor(context.getResources().getColor(R.color.white));
            } else {
                button[i].setTextColor(context.getResources().getColor(R.color.white));
                button[i].setBackgroundColor(context.getResources().getColor(R.color.customSecond));
                button[i].setEnabled(false);
            }

            int value = i;

            button[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    builder.setMessage("Do you want to book appointment ?").setTitle("Book Appointment").setCancelable(false);

                    builder.setPositiveButton("Book Now", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Toast.makeText(context, "Booked...!!!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    builder.create().show();
                }
            });

            linearLayout.addView(button[i]);
        }

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
