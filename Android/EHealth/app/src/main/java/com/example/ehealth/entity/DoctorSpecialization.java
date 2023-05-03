package com.example.ehealth.entity;

import java.util.List;

public class DoctorSpecialization {
    private int Id;
    private int DoctorId;
    private int SpecializationId;

    private List<HospitalDoctor> HospitalDoctors;
    private Specialization Specialization;
    private Users User;
}
