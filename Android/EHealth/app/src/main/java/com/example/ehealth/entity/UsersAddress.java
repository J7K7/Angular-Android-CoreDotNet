package com.example.ehealth.entity;

public class UsersAddress {
    private int Id;
    private int UserId;
    private String Building;
    private String Street;
    private int CityId;

    private City City;
    private Users User;

    //region Getter & Setter

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getBuilding() {
        return Building;
    }

    public void setBuilding(String building) {
        Building = building;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public int getCityId() {
        return CityId;
    }

    public void setCityId(int cityId) {
        CityId = cityId;
    }

    public com.example.ehealth.entity.City getCity() {
        return City;
    }

    public void setCity(com.example.ehealth.entity.City city) {
        City = city;
    }

    public Users getUser() {
        return User;
    }

    public void setUser(Users user) {
        User = user;
    }

    //endregion Getter & Setter
}