package com.example.ehealth.entity;

import java.util.List;

public class Specialization {
    private int Id;
    private String Name;
    private byte Time;

    private List<DoctorSpecialization> DoctorSpecializations;

    //region Getter & Setter

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public byte getTime() {
        return Time;
    }

    public void setTime(byte time) {
        Time = time;
    }

    public List<DoctorSpecialization> getDoctorSpecializations() {
        return DoctorSpecializations;
    }

    public void setDoctorSpecializations(List<DoctorSpecialization> doctorSpecializations) {
        DoctorSpecializations = doctorSpecializations;
    }

    //endregion Getter & Setter
}