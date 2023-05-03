package com.example.ehealth.entity;

import java.util.List;

public class State {
    private int Id;
    private String Name;
    private int CountryId;

    private List<City> Cities;
    private Country Country;

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

    public int getCountryId() {
        return CountryId;
    }

    public void setCountryId(int countryId) {
        CountryId = countryId;
    }

    public List<City> getCities() {
        return Cities;
    }

    public void setCities(List<City> cities) {
        Cities = cities;
    }

    public com.example.ehealth.entity.Country getCountry() {
        return Country;
    }

    public void setCountry(com.example.ehealth.entity.Country country) {
        Country = country;
    }

    //endregion Getter & Setter
}