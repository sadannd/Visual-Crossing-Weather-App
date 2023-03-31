package com.sadanand.weatherapp.weatherapp.EntityModule;

import java.io.Serializable;
import java.util.List;

public class DataBean implements Serializable {
    private String addr, tz, timezzoneOffset;

    private List<DaysDataBean> daysList;
    private CurrConditionBean currConditionBeans;

    public DataBean(String addr, String tz, String timezzoneOffset, List<DaysDataBean> daysList, CurrConditionBean currentConditionList) {

        this.timezzoneOffset = timezzoneOffset;
        this.daysList = daysList;
        this.currConditionBeans = currentConditionList;
        this.addr = addr;
        this.tz = tz;
    }

    public String getAddr() {
        return addr;
    }

    public String getTz() {
        return tz;
    }

    public String getTimezzoneOffset() {
        return timezzoneOffset;
    }

    public List<DaysDataBean> getDaysList() {
        return daysList;
    }

    public CurrConditionBean getCurrentConditionDataBean() {
        return currConditionBeans;
    }

    @Override
    public String toString() {
        return addr + '\'' + tz + '\'' + timezzoneOffset + '\'' + daysList + currConditionBeans;
    }
}
