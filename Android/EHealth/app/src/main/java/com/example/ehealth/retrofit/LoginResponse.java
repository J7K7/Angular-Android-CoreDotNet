package com.example.ehealth.retrofit;

import com.example.ehealth.entity.Users;

public class LoginResponse {

    private String Item1;
    private Users Item2;

    //region Getter & Setter

    public String getItem1() {
        return Item1;
    }

    public void setItem1(String item1) {
        Item1 = item1;
    }

    public Users getItem2() {
        return Item2;
    }

    public void setItem2(Users item2) {
        Item2 = item2;
    }

    //endregion Getter & Setter
}