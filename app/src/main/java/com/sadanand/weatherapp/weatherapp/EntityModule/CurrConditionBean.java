package com.sadanand.weatherapp.weatherapp.EntityModule;

import java.io.Serializable;

public class CurrConditionBean implements Serializable {
    private String datetime,temp,feelsLike,humid;
    private String windG,wSpeed,windDirection,visibility,cloudCoverage;
    private String cond,uvIndexes,sunrise;
    private String sunset;
    private String images;

    public CurrConditionBean(String datetime, String temp, String feelsLike, String humid, String windG, String wSpeed, String windDirection, String visibility, String cloudCoverage, String uvIndexes, String cond, String images, String sunrise, String sunset) {

        this.feelsLike = feelsLike;
        this.humid = humid;
        this.datetime = datetime;
        this.temp = temp;
        this.windDirection = windDirection;
        this.visibility = visibility;
        this.windG = windG;
        this.wSpeed = wSpeed;
        this.cond = cond;
        this.images = images;
        this.cloudCoverage = cloudCoverage;
        this.uvIndexes = uvIndexes;
        this.sunrise = sunrise;
        this.sunset = sunset;
    }



    public String getFeelsLike() {
        return feelsLike;
    }

    public String getHumid() {
        return humid;
    }
    public String getDatetime() {
        return datetime;
    }

    public String getTemp() {
        return temp;
    }



    public String getWindDirection() {
        return windDirection;
    }

    public String getVisibility() {
        return visibility;
    }
    public String getWindG() {
        return windG;
    }

    public String getwSpeed() {
        return wSpeed;
    }



    public String getCond() {
        return cond;
    }

    public String getImages() {
        return images;
    }
    public String getCloudCoverage() {
        return cloudCoverage;
    }

    public String getUvIndexes() {
        return uvIndexes;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    @Override
    public String toString() {
        return  datetime + '\'' + temp + '\'' + feelsLike + '\'' + humid + '\'' + windG + '\'' +
                 wSpeed + '\'' + windDirection + '\'' + visibility + '\'' + cloudCoverage + '\'' + uvIndexes + '\'' +
                cond + '\'' + images + '\'' + sunrise + '\'' + sunset + '\'' +
                '}';
    }
}
