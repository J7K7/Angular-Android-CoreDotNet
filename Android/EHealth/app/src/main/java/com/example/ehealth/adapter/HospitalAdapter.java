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

import com.example.ehealth.HospitalDetailActivity;
import com.example.ehealth.R;
import com.example.ehealth.entity.Users;
import com.example.ehealth.entity.UsersAddress;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.Holder> {

    Context context;
    ArrayList<Users> usersArrayList;
    String imagePath;

    public HospitalAdapter(Context context, ArrayList<Users> users, String ImagePath) {
        this.context = context;
        this.usersArrayList = users;
        this.imagePath = ImagePath;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HospitalAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.card_hospital, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Users users = usersArrayList.get(position);
        UsersAddress usersAddress = users.getUsersAddresses().get(0);

        Picasso.with(context).load(imagePath + users.getUserImages().get(0).getName()).into(holder.iv);

        holder.tvName.setText(users.getFirstName() + users.getLastName());
        holder.tvAddress.setText(usersAddress.getBuilding() + ", " + usersAddress.getStreet() + ", " +
                usersAddress.getCity().getName() + ", " + usersAddress.getCity().getState().getName() + ", " +
                usersAddress.getCity().getState().getCountry().getName());

        holder.tvEmail.setText(users.getEmail());
        holder.tvMobile.setText(users.getMobileNo());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, HospitalDetailActivity.class);
                intent.putExtra("HospitalId", users.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return usersArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        ImageView iv;
        TextView tvName;
        TextView tvAddress;
        TextView tvEmail;
        TextView tvMobile;

        public Holder(@NonNull View itemView) {
            super(itemView);

            iv = itemView.findViewById(R.id.ivCardHospital);
            tvName = itemView.findViewById(R.id.tvCardHospitalName);
            tvAddress = itemView.findViewById(R.id.tvCardHospitalAddress);
            tvEmail = itemView.findViewById(R.id.tvCardHospitalEmail);
            tvMobile = itemView.findViewById(R.id.tvCardHospitalMobile);
        }
    }
}