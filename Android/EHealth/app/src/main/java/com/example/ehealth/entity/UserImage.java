package com.example.ehealth.entity;

public class UserImage {
    private int Id;
    private int UserId;
    private String Name;

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

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Users getUser() {
        return User;
    }

    public void setUser(Users user) {
        User = user;
    }

    //endregion Getter & Setter
}