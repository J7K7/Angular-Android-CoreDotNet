package com.example.ehealth.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class DoctorSchedule {
    private int Id;
    private int HospitalDoctorId;
    private LocalDateTime ScheduleDate;
    private LocalTime MorningStart;
    private LocalTime MorningEnd;
    private LocalTime EveningStart;
    private LocalTime EveningEnd;
    private boolean IsEmergency;

    private HospitalDoctor HospitalDoctor;

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

    public LocalDateTime getScheduleDate() {
        return ScheduleDate;
    }

    public void setScheduleDate(LocalDateTime scheduleDate) {
        ScheduleDate = scheduleDate;
    }

    public LocalTime getMorningStart() {
        return MorningStart;
    }

    public void setMorningStart(LocalTime morningStart) {
        MorningStart = morningStart;
    }

    public LocalTime getMorningEnd() {
        return MorningEnd;
    }

    public void setMorningEnd(LocalTime morningEnd) {
        MorningEnd = morningEnd;
    }

    public LocalTime getEveningStart() {
        return EveningStart;
    }

    public void setEveningStart(LocalTime eveningStart) {
        EveningStart = eveningStart;
    }

    public LocalTime getEveningEnd() {
        return EveningEnd;
    }

    public void setEveningEnd(LocalTime eveningEnd) {
        EveningEnd = eveningEnd;
    }

    public boolean isEmergency() {
        return IsEmergency;
    }

    public void setEmergency(boolean emergency) {
        IsEmergency = emergency;
    }

    public com.example.ehealth.entity.HospitalDoctor getHospitalDoctor() {
        return HospitalDoctor;
    }

    public void setHospitalDoctor(com.example.ehealth.entity.HospitalDoctor hospitalDoctor) {
        HospitalDoctor = hospitalDoctor;
    }

    //endregion Getter & Setter
}