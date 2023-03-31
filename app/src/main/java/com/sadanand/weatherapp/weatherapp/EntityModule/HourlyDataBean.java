package com.sadanand.weatherapp.weatherapp.EntityModule;

import java.io.Serializable;

public class HourlyDataBean implements Serializable {
    private String DateAndtime,cond;
    private String temperature,image,time;

    public HourlyDataBean(String DateAndtime, String time, String image, String temperature, String cond) {

        this.DateAndtime = DateAndtime;
        this.time = time;
        this.temperature = temperature;
        this.cond = cond;
        this.image = image;

    }

    public HourlyDataBean(String DateAndtime, String temperature, String cond, String image) {
        this.DateAndtime = DateAndtime;
        this.image = image;
        this.cond = cond;
        this.temperature = temperature;
    }

    public String getCond() {
        return cond;
    }

    public String getImage() {
        return image;
    }
    public String getDateAndtime() {
        return DateAndtime;
    }

    public String getTemperature() {
        return temperature;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return DateAndtime + '\'' + time + '\'' +
                image + '\'' + temperature + '\'' + cond + '\'' ;
    }
}
