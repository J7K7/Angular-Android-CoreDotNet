package com.example.ehealth.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehealth.DoctorActivity;
import com.example.ehealth.HospitalActivity;
import com.example.ehealth.LoginActivity;
import com.example.ehealth.MainActivity;
import com.example.ehealth.ProfileActivity;
import com.example.ehealth.R;

import java.util.ArrayList;
import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.Holder> {

    Context context;
    String[] title = {"Hospital", "Doctor", "Profile"};
    int[] images = {R.drawable.hospital, R.drawable.doctor, R.drawable.profile};

    AlertDialog.Builder builder;

    public DashboardAdapter(Context context) {
        this.context = context;
        this.builder = new AlertDialog.Builder(context);
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_dashboard, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.tv.setText(title[position]);
        holder.iv.setImageResource(images[position]);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();

                if (holder.tv.getText().toString().equalsIgnoreCase("Hospital")) {
                    intent = new Intent(context, HospitalActivity.class);
                    context.startActivity(intent);
                }

                else if (holder.tv.getText().toString().equalsIgnoreCase("Doctor")) {
                    intent = new Intent(context, DoctorActivity.class);
                    context.startActivity(intent);
                }

                else if (holder.tv.getText().toString().equalsIgnoreCase("Profile"))
                    Logout();
            }
        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    private void Logout() {
        builder.setMessage("Are you sure you want to Logout ?").setTitle("Logout").setCancelable(false);

        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences sharedPreferences = context.getSharedPreferences("Patient", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString("PatientId", null);
                editor.clear();
                editor.apply();

                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
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

    public static class Holder extends RecyclerView.ViewHolder {

        TextView tv;
        ImageView iv;

        public Holder(@NonNull View itemView) {
            super(itemView);

            tv = itemView.findViewById(R.id.tvCardDashboard);
            iv = itemView.findViewById(R.id.ivCardDashboard);
        }
    }
}