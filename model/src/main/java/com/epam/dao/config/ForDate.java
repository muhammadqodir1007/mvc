package com.epam.dao.config;


import java.sql.Date;
import java.text.ParseException;

public class ForDate {


    static public Date getDate() {

        Date date = new Date(System.currentTimeMillis());
        return date;

    }

    public static void main(String[] args) throws ParseException {
        long time =getDate().getTime();


        System.out.println(time);


    }
}
