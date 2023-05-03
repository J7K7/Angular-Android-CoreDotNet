package com.example.ehealth.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ehealth.R;
import com.example.ehealth.entity.Specialization;
import com.example.ehealth.entity.Users;
import com.example.ehealth.retrofit.EndPoint;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoctorSpecializationAdapter extends RecyclerView.Adapter<DoctorSpecializationAdapter.Holder> {

    Context context;
    ArrayList<Specialization> specializations;
    ArrayList<Users> doctors = new ArrayList<Users>();
    DoctorAdapter doctorAdapter;

    String imagePath;
    int HospitalId;

    private EndPoint endPoint;

    public DoctorSpecializationAdapter(Context context, ArrayList<Specialization> specializations, String imagePath, int hospitalId, EndPoint endPoint) {
        this.context = context;
        this.specializations = specializations;
        this.imagePath = imagePath;
        HospitalId = hospitalId;
        this.endPoint = endPoint;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DoctorSpecializationAdapter.Holder(LayoutInflater.from(context).inflate(R.layout.card_doctor_specialization, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        Specialization specialization = specializations.get(position);

        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        doctorAdapter = new DoctorAdapter(context, doctors, imagePath, HospitalId);
        holder.recyclerView.setAdapter(doctorAdapter);

        SharedPreferences sharedPreferences = context.getSharedPreferences("Patient", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("SpecializationId", specialization.getId());
        editor.apply();

        Call<ArrayList<Users>> getDoctors = endPoint.GetDoctors(HospitalId, 0, specialization.getId(), true);
        getDoctors.enqueue(new Callback<ArrayList<Users>>() {
            @Override
            public void onResponse(Call<ArrayList<Users>> call, Response<ArrayList<Users>> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    doctors.clear();
                    for (Users doctor : response.body()) {
                        Log.v("===========>", doctor.getFirstName() + "=>" + doctor.getUserImages().get(0).getName() + "=>" + specialization.getName());
                        doctors.add(doctor);
                    }
                    doctorAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Users>> call, Throwable t) {

            }
        });

        holder.textView.setText(specialization.getName());
    }

    @Override
    public int getItemCount() {
        return specializations.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        TextView textView;
        RecyclerView recyclerView;

        public Holder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.tvCardDoctorSpecialization);
            recyclerView = itemView.findViewById(R.id.rvCardDoctorSpecialization);
        }
    }
}