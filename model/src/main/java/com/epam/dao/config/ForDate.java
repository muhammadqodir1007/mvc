package com.epam.dao.config;
import java.sql.Date;

public class ForDate {
    public static Date getDate() {
        return new Date(System.currentTimeMillis());
    }
}
