package com.example.ehealth.entity;

import java.util.List;

public class Users {
    private int Id;
    private String UserName;
    private String Password;
    private byte UserType;
    private String FirstName;
    private String LastName;
    private String Email;
    private String MobileNo;
    private byte Gender;
    private boolean IsActive;
    private boolean IsGoogle;

    private List<Appointment> Appointments;
    private List<DoctorSpecialization> DoctorSpecializations;
    private List<HospitalDoctor> HospitalDoctors;
    private List<UserImage> UserImages;
    private List<UsersAddress> UsersAddresses;

    //region Getter & Setter

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public byte getUserType() {
        return UserType;
    }

    public void setUserType(byte userType) {
        UserType = userType;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public byte getGender() {
        return Gender;
    }

    public void setGender(byte gender) {
        Gender = gender;
    }

    public boolean isActive() {
        return IsActive;
    }

    public void setActive(boolean active) {
        IsActive = active;
    }

    public boolean isGoogle() {
        return IsGoogle;
    }

    public void setGoogle(boolean google) {
        IsGoogle = google;
    }

    public List<Appointment> getAppointments() {
        return Appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        Appointments = appointments;
    }

    public List<DoctorSpecialization> getDoctorSpecializations() {
        return DoctorSpecializations;
    }

    public void setDoctorSpecializations(List<DoctorSpecialization> doctorSpecializations) {
        DoctorSpecializations = doctorSpecializations;
    }

    public List<HospitalDoctor> getHospitalDoctors() {
        return HospitalDoctors;
    }

    public void setHospitalDoctors(List<HospitalDoctor> hospitalDoctors) {
        HospitalDoctors = hospitalDoctors;
    }

    public List<UserImage> getUserImages() {
        return UserImages;
    }

    public void setUserImages(List<UserImage> userImages) {
        UserImages = userImages;
    }

    public List<UsersAddress> getUsersAddresses() {
        return UsersAddresses;
    }

    public void setUsersAddresses(List<UsersAddress> usersAddresses) {
        UsersAddresses = usersAddresses;
    }

    //endregion Getter & Setter
}