package com.google.se;

public class SleepModel {
    public String getSleep() {
        return sleep;
    }
    public String getSleepM() {
        return sleepM;
    }

    public void setSleep(String sleep) {
        this.sleep = sleep;
    }
    public void setSleepM(String sleepM) {
        this.sleepM = sleepM;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String sleep;
    String sleepM;
    String date;
}
