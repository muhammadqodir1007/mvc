package org.example.dao.config;


import java.sql.Date;

public class ForDate {



    static public Date getDate(){

        Date date=new Date(System.currentTimeMillis());
        return date;
    }
}
