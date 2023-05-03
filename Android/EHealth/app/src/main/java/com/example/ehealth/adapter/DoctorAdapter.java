package com.example.ehealth.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehealth.DoctorDetailActivity;
import com.example.ehealth.HospitalDetailActivity;
import com.example.ehealth.R;
import com.example.ehealth.entity.Users;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.Holder> {

    Context context;
    ArrayList<Users> usersArrayList;
    String imagePath;
    int HospitalId;

    public DoctorAdapter(Context context, ArrayList<Users> usersArrayList, String imagePath, int HospitalId) {
        this.context = context;
        this.usersArrayList = usersArrayList;
        this.imagePath = imagePath;
        this.HospitalId = HospitalId;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.card_doctor, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Users users = usersArrayList.get(position);

        Picasso.with(context).load(imagePath + users.getUserImages().get(0).getName()).into(holder.imageView);
        holder.textView.setText(users.getFirstName() + " " + users.getLastName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DoctorDetailActivity.class);
                intent.putExtra("HospitalId", HospitalId);
                intent.putExtra("DoctorId", users.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView textView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.ivCardDoctor);
            textView = itemView.findViewById(R.id.tvCardDoctor);
        }
    }
}
