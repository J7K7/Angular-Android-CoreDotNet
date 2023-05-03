package com.example.ehealth.retrofit;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentSlotResponse {
    private String Item1;
    private ArrayList<AppointmentTimeSlot> Item2;

    public Date getItem1() {
        try {
            SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
            return input.parse(Item1);
        } catch (Exception ex) {
            return null;
        }
    }

    public void setItem1(String item1) {
        Item1 = item1;
    }

    public ArrayList<AppointmentTimeSlot> getItem2() {
        return Item2;
    }

    public void setItem2(ArrayList<AppointmentTimeSlot> item2) {
        Item2 = item2;
    }
}
