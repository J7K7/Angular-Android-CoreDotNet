package com.example.ehealth.entity;

import java.util.List;

public class HospitalDoctor {
    private int Id;
    private int DoctorSpecializationId;
    private int HospitalId;
    private double Fees;
    private boolean IsActive;

    private List<Appointment> Appointments;
    private List<DoctorSchedule> DoctorSchedules;
    private DoctorSpecialization DoctorSpecialization;
    private Users User;

    //region Getter & Setter

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getDoctorSpecializationId() {
        return DoctorSpecializationId;
    }

    public void setDoctorSpecializationId(int doctorSpecializationId) {
        DoctorSpecializationId = doctorSpecializationId;
    }

    public int getHospitalId() {
        return HospitalId;
    }

    public void setHospitalId(int hospitalId) {
        HospitalId = hospitalId;
    }

    public double getFees() {
        return Fees;
    }

    public void setFees(double fees) {
        Fees = fees;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public List<Appointment> getAppointments() {
        return Appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        Appointments = appointments;
    }

    public List<DoctorSchedule> getDoctorSchedules() {
        return DoctorSchedules;
    }

    public void setDoctorSchedules(List<DoctorSchedule> doctorSchedules) {
        DoctorSchedules = doctorSchedules;
    }

    public com.example.ehealth.entity.DoctorSpecialization getDoctorSpecialization() {
        return DoctorSpecialization;
    }

    public void setDoctorSpecialization(com.example.ehealth.entity.DoctorSpecialization doctorSpecialization) {
        DoctorSpecialization = doctorSpecialization;
    }

    public Users getUser() {
        return User;
    }

    public void setUser(Users user) {
        User = user;
    }

    //endregion Getter & Setter
}