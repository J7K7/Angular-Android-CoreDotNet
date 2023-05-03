package com.example.ehealth.retrofit;

import com.example.ehealth.entity.Specialization;
import com.example.ehealth.entity.Users;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface EndPoint {

    @POST("api/Users/Login")
    @Headers({"Content-Type: application/json"})
    Call<LoginResponse> PatientLogin(@Body Users users);

    @POST("api/Users/RegisterPatient")
    @Headers({"Content-Type: application/json"})
    Call<String> RegisterPatient(@Body Users users);

    @GET("api/Users/GetHospitals")
    @Headers({"Content-Type: application/json"})
    Call<ArrayList<Users>> GetHospitals();

    @GET("api/Users/GetHospitalById")
    @Headers({"Content-Type: application/json"})
    Call<Users> GetHospitalById(@Query("HospitalId") int HospitalId);

    @GET("api/Users/GetDoctorsByHospital")
    @Headers({"Content-Type: application/json"})
    Call<ArrayList<Users>> GetDoctors(@Query("HospitalId") int HospitalId, @Query("DoctorId") int DoctorId, @Query("SpecializationId") int SpecializationId, @Query("Status") boolean Status);

    @GET("api/DoctorSpecializations/Get")
    @Headers({"Content-Type: application/json"})
    Call<ArrayList<Specialization>> GetDoctorSpecializations(@Query("HospitalId") int HospitalId, @Query("DoctorId") int DoctorId, @Query("Status") boolean Statuss);

    @GET("api/Appointments/GetAppointmentSlots")
    @Headers({"Content-Type: application/json"})
    Call<ArrayList<AppointmentSlotResponse>> GetAppointmentSlots(@Query("HospitalId") int HospitalId, @Query("DoctorId") int DoctorId, @Query("SpecializationId")int SpecializationId);
}