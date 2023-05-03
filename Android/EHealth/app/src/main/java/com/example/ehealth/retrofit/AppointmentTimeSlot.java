package com.example.ehealth.retrofit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class AppointmentTimeSlot {
    private String Item1;
    private boolean Item2;

    public Date getItem1() {
        try {
            SimpleDateFormat input = new SimpleDateFormat("hh:mm:ss");
            return input.parse(Item1);
        } catch (Exception ex) {
            return null;
        }
    }

    public void setItem1(String item1) {
        Item1 = item1;
    }

    public boolean isItem2() {
        return Item2;
    }

    public void setItem2(boolean item2) {
        Item2 = item2;
    }
}
