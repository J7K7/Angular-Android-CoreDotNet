package com.example.ehealth.entity;

import java.util.List;

public class City {
    private int Id;
    private String Name;
    private int StateId;

    private State State;
    private List<UsersAddress> UsersAddresses;

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

    public int getStateId() {
        return StateId;
    }

    public void setStateId(int stateId) {
        StateId = stateId;
    }

    public com.example.ehealth.entity.State getState() {
        return State;
    }

    public void setState(com.example.ehealth.entity.State state) {
        State = state;
    }

    public List<UsersAddress> getUsersAddresses() {
        return UsersAddresses;
    }

    public void setUsersAddresses(List<UsersAddress> usersAddresses) {
        UsersAddresses = usersAddresses;
    }

    //endregion Getter & Setter
}