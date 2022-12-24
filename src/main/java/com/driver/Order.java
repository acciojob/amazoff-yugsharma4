package com.driver;


import java.util.StringTokenizer;

public class Order {

    private String id;
    private int deliveryTime;

    public Order(String id, String deliveryTime) {
        this.id = id;

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //Conversion of String time to Int
        String[] timeArr = deliveryTime.split(":"); //"10:10"

        int hour = Integer.parseInt(timeArr[0]);
        int min = Integer.parseInt(timeArr[1]);
        int time = hour*60 + min;

//        StringTokenizer st = new StringTokenizer(deliveryTime,":");
//        String hr = st.nextToken();
//        String min = st.nextToken();
//        int time = Integer.parseInt(hr)*60 + Integer.parseInt(min);
        //deliveryTime  = HH*60 + MM
        this.deliveryTime = time;

    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}
}
