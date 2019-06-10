package com.jkk.finances.Model;

import android.content.Intent;

public class MyDate {
    private Integer year;
    private Integer day;

    public Integer getYear() {
        return this.year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDay() {
        return this.day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public Integer getMonth() {
        return this.month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public MyDate(Integer year, Integer month, Integer day) {
        this.year = year;
        this.day = day;
        this.month = month;
    }

    private Integer month;
}
