package com.example.ehealth.entity;

import java.util.List;

public class Country {
    private int Id;
    private String Name;

    private List<State> States;

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

    public List<State> getStates() {
        return States;
    }

    public void setStates(List<State> states) {
        States = states;
    }

    //endregion Getter & Setter
}