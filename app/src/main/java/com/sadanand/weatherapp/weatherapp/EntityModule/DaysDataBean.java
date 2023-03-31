package com.sadanand.weatherapp.weatherapp.EntityModule;

import java.io.Serializable;
import java.util.List;

public class DaysDataBean implements Serializable {
    private String dateAndtimeEp,temperatureMax,temperatureMin,prepciprob;
    private String desc,uvIndexes,image;
    private List<HourlyDataBean> hourlyList;
    private String eveningTime,nightTime,afternoonTime,morningtime;

    public DaysDataBean(String dateAndtimeEp, String temperatureMax, String desc, String precipprob, String uvIndexes, String image, String morningtime, String afternoonTime, String eveningTime, String nightTime) {

        this.prepciprob = precipprob;
        this.uvIndexes = uvIndexes;
        this.dateAndtimeEp = dateAndtimeEp;
        this.temperatureMax = temperatureMax;
        this.morningtime = morningtime;
        this.afternoonTime = afternoonTime;
        this.desc = desc;
        this.image = image;
        this.eveningTime = eveningTime;
        this.nightTime = nightTime;
    }

    public DaysDataBean(String dateAndtimeEp, String temperatureMax, String temperatureMin, String precipprob, String uvIndexes, String desc, String image, List<HourlyDataBean> hourlyList) {

        this.temperatureMin = temperatureMin;
        this.prepciprob = precipprob;
        this.dateAndtimeEp = dateAndtimeEp;
        this.temperatureMax = temperatureMax;
        this.image = image;
        this.hourlyList = hourlyList;
        this.uvIndexes = uvIndexes;
        this.desc = desc;
    }



    public String getTemperatureMin() {
        return temperatureMin;
    }

    public String getPrecipprob() {
        return prepciprob;
    }
    public String getDateAndtimeEp() {
        return dateAndtimeEp;
    }


    public String getTemperatureMax() {
        return temperatureMax;
    }



    public String getImage() {
        return image;
    }

    public List<HourlyDataBean> getHourlyList() {
        return hourlyList;
    }
    public String getUvIndexes() {
        return uvIndexes;
    }

    public String getDesc() {
        return desc;
    }



    public String getEveningTime() {
        return eveningTime;
    }

    public String getNightTime() {
        return nightTime;
    }
    public String getMorningtime() {
        return morningtime;
    }

    public String getAfternoonTime() {
        return afternoonTime;
    }

    @Override
    public String toString() {
        return
                dateAndtimeEp + '\'' + temperatureMax + '\'' +
                 temperatureMin + '\'' + prepciprob + '\'' +
                 uvIndexes + '\'' + desc + '\'' +
                 image + '\'' + hourlyList;
    }
}
