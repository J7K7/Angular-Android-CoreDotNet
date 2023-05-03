package com.example.ehealth.entity;

import java.time.LocalDateTime;

public class Appointment {
    private int Id;
    private int HospitalDoctorId;
    private int PatientId;
    private LocalDateTime AppointmentTime;
    private byte Status;

    private HospitalDoctor HospitalDoctor;
    private Users User;

    //region Getter & Setter

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getHospitalDoctorId() {
        return HospitalDoctorId;
    }

    public void setHospitalDoctorId(int hospitalDoctorId) {
        HospitalDoctorId = hospitalDoctorId;
    }

    public int getPatientId() {
        return PatientId;
    }

    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    public LocalDateTime getAppointmentTime() {
        return AppointmentTime;
    }

    public void setAppointmentTime(LocalDateTime appointmentTime) {
        AppointmentTime = appointmentTime;
    }

    public byte getStatus() {
        return Status;
    }

    public void setStatus(byte status) {
        Status = status;
    }

    public com.example.ehealth.entity.HospitalDoctor getHospitalDoctor() {
        return HospitalDoctor;
    }

    public void setHospitalDoctor(com.example.ehealth.entity.HospitalDoctor hospitalDoctor) {
        HospitalDoctor = hospitalDoctor;
    }

    public Users getUser() {
        return User;
    }

    public void setUser(Users user) {
        User = user;
    }

    //endregion Getter & Setter
}